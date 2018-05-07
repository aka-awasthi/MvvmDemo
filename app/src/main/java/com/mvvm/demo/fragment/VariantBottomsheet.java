package com.mvvm.demo.fragment;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
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
import com.mvvm.demo.db.variants.VariantEntity;
import com.mvvm.demo.db.variants.VariantViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VariantBottomsheet extends BottomSheetDialogFragment {

    final String TAG = getClass().getName();
    VariantViewModel viewModel;
    @BindView(R.id.cf_rv)
    RecyclerView rv;

    int id=0;
    List<VariantEntity> list;
    boolean proceed = false;
    VariantAdapter adapter;



    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };

    CoordinatorLayout.Behavior behavior;

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        id = getArguments().getInt("id");
        list = new ArrayList<>();


        View contentView = View.inflate(getContext(), R.layout.categoryfragment, null);
        ButterKnife.bind(this,contentView);
        dialog.setContentView(contentView);
        viewModel = ViewModelProviders.of(this).get(VariantViewModel.class);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        behavior = params.getBehavior();
        ((View) contentView.getParent()).setBackgroundColor(Color.parseColor("#ffffff"));
        if( behavior != null && behavior instanceof BottomSheetBehavior ) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
            ((BottomSheetBehavior) behavior).setPeekHeight(Integer.MAX_VALUE);
            contentView.requestLayout();
        }
        rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        adapter=new VariantAdapter();
        rv.setAdapter(adapter);
        new AsyncDbCall().execute();
    }

    class  AsyncDbCall extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            viewModel.getList(id).observe(VariantBottomsheet.this, new Observer<List<VariantEntity>>() {
                @Override
                public void onChanged(@Nullable List<VariantEntity> variantEntities) {
                    if (variantEntities != null && variantEntities.size()!=0) {
                        list.clear();
                        list.addAll(variantEntities);

                        proceed = true;
                    }else {
                        proceed = false;
                    }
                    onpostexecute();
                }
            });

            return null;
        }

        @UiThread
        private void onpostexecute(){
            try {
                if (proceed) {
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d(TAG, "product not present now");
                    Toast.makeText(getActivity(), "Variants not found", Toast.LENGTH_SHORT).show();
//                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class VariantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            //if(viewType==1){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categoryrow,parent,false);
            return new RegularRow(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            VariantEntity cdb = list.get(position);
            RegularRow row = ((RegularRow)holder);
            String text = cdb.getColor()+" | "+cdb.getPrice()+" | "+cdb.getSize();
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
//                ChildCategoryFragment childCategoryFragment = new ChildCategoryFragment();
//                Bundle b = new Bundle();
//                b.putInt("id",id);
//                childCategoryFragment.setArguments(b);
//                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.am_nvfl,childCategoryFragment).addToBackStack(null).commit();
            }
        }
    }

}
