package com.mvvm.demo.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mvvm.demo.R;
import com.mvvm.demo.db.product.ProductEntity;
import com.mvvm.demo.db.product.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrowseFragment extends Fragment {

    final String TAG = getClass().getName();
    @BindView(R.id.cf_rv)
    RecyclerView rv;
    List<ProductEntity> list;
    ProductAdapter adapter;
    ProductViewModel productViewModel;
    int id=0;
    boolean proceed = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.categoryfragment,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG,"product called");
        list = new ArrayList<>();
        id = getArguments().getInt("id");
        rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        adapter=new ProductAdapter();
        rv.setAdapter(adapter);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        new AsyncDbCall().execute();
    }

    class  AsyncDbCall extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            if (id == 0){
                productViewModel.getList().observe(BrowseFragment.this, new Observer<List<ProductEntity>>() {
                    @Override
                    public void onChanged(@Nullable List<ProductEntity> productEntities) {
                        if (productEntities != null && productEntities.size()!=0) {
                            list.clear();
                            list.addAll(productEntities);
//                        adapter.notifyDataSetChanged();
                            proceed = true;
                        }else {
                            proceed = false;
                        }
                        onpostexecute();
                    }
                });
            }else {
                productViewModel.getList(id).observe(BrowseFragment.this, new Observer<List<ProductEntity>>() {
                    @Override
                    public void onChanged(@Nullable List<ProductEntity> productEntities) {
                        if (productEntities != null && productEntities.size()!=0) {
                            list.clear();
                            list.addAll(productEntities);
//                        adapter.notifyDataSetChanged();
                            proceed = true;
                        }else {
                            proceed = false;
                        }
                        onpostexecute();
                    }
                });
            }
            return null;
        }

        @UiThread
        private void onpostexecute(){
            try {
                if (proceed) {
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d(TAG, "product not present now");
                    Toast.makeText(getActivity(), "Products not found", Toast.LENGTH_SHORT).show();
//                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            //if(viewType==1){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categoryrow,parent,false);
            return new ProductAdapter.RegularRow(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ProductEntity cdb = list.get(position);
            RegularRow row = ((RegularRow)holder);
            String text = cdb.getName()+" | "+cdb.getTax_value()+"%  and click to see variants";
            row.tv.setText(text);
        }

        @Override
        public int getItemCount() {
            if(list!=null && list.size()!=0){
                return list.size();
            }else{
                return 0;
            }
        }

        class RegularRow extends RecyclerView.ViewHolder implements View.OnClickListener{

            @BindView(R.id.cr_tv)
            TextView tv;


            public RegularRow(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                int id =  list.get(getAdapterPosition()).getId();
                BottomSheetDialogFragment lbottomSheetDialogFragment = new VariantBottomsheet();
                Bundle b = new Bundle();
                b.putInt("id",id);
                lbottomSheetDialogFragment.setArguments(b);
                //show it
                lbottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), lbottomSheetDialogFragment.getTag());
            }
        }
    }
}
