package com.mvvm.demo.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mvvm.demo.R;
import com.mvvm.demo.db.category.CategoryEntity;
import com.mvvm.demo.db.category.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryFragment extends Fragment {

    final String TAG = getClass().getName();
    @BindView(R.id.cf_rv) RecyclerView rv;
    CategoryViewModel categoryViewModel;
    List<CategoryEntity> list;
    CategoryAdapter categoryAdapter;

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
        list = new ArrayList<>();
        rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        categoryAdapter=new CategoryAdapter();
        rv.setAdapter(categoryAdapter);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.getList().observe(this, new Observer<List<CategoryEntity>>() {
            @Override
            public void onChanged(@Nullable List<CategoryEntity> categoryEntities) {
                if (categoryEntities != null) {
                    list.clear();
                    list.addAll(categoryEntities);
                    categoryAdapter.notifyDataSetChanged();
                    Log.d(TAG,"size is "+categoryEntities.size());

                }
            }
        });
    }


    class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            //if(viewType==1){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categoryrow,parent,false);
            return new RegularRow(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            CategoryEntity cdb = list.get(position);
            RegularRow
                    row = ((RegularRow)holder);
            row.tv.setText(cdb.getName());

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

            }

            @Override
            public void onClick(View view) {

            }
        }

    }



}
