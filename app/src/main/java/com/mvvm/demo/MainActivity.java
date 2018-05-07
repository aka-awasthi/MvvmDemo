package com.mvvm.demo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toolbar;

import com.mvvm.demo.api.sampleapi.SampleApi;
import com.mvvm.demo.db.AppDatabase;
import com.mvvm.demo.db.ranking.RankingViewModel;
import com.mvvm.demo.fragment.BrowseFragment;
import com.mvvm.demo.fragment.CategoryFragment;
import com.mvvm.demo.fragment.ProductFragment;
import com.mvvm.demo.fragment.RankingFragment;
import com.mvvm.demo.interfaces.CalltoParent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CalltoParent {

    final String TAG = getClass().getName();
    @BindView(R.id.am_drawerlayout)
    DrawerLayout drawerlayout;

    @BindView(R.id.am_drawer)
    NavigationView navigationView;

    @BindView(R.id.am_toolbar)
    android.support.v7.widget.Toolbar toolbar;
    RankingViewModel rankingViewModel;
    List<String> rankings;

    ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        drawerToggle=setupDrawerToggle();

        //drawerToggle.setDrawerIndicatorEnabled(false);
        //drawerToggle.setHomeAsUpIndicator(R.mipmap.appicon);
        drawerlayout.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(false);
        drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                drawerlayout.openDrawer(GravityCompat.END);
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.am_nvfl,new CategoryFragment()).addToBackStack(null).commit();
        new SampleApi(AppDatabase.getDatabase(this.getApplication()));

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.drawer_open,  R.string.drawer_closed){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //bus.post(ToolbarEventEnum.drawer_open);
            }
        };
    }

    AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {
                BrowseFragment f = new BrowseFragment();
                Bundle b = new Bundle();
                b.putInt("id",0);
                f.setArguments(b);
                getSupportFragmentManager().beginTransaction().replace(R.id.am_mainfl,f).addToBackStack(null).commit();
            }else {
                RankingFragment r = new RankingFragment();
                Bundle b = new Bundle();
                b.putString("id",rankings.get(position));
                r.setArguments(b);
                getSupportFragmentManager().beginTransaction().replace(R.id.am_mainfl,r).addToBackStack(null).commit();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        rankings = new ArrayList<>();
       final  Spinner spinner = (Spinner) MenuItemCompat.getActionView(item); // get the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,rankings);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(itemSelectedListener);
        rankingViewModel = ViewModelProviders.of(this).get(RankingViewModel.class);

        rankingViewModel.getList().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                if (strings != null && strings.size() != 0){
                    rankings.clear();
                    rankings.addAll(strings);
                    rankings.add(0,"All");
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                            android.R.layout.simple_spinner_item,rankings);
                    spinner.setAdapter(adapter);
                }
            }
        });
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                if (drawerlayout.isDrawerOpen(GravityCompat.END)) {
                    drawerlayout.closeDrawer(GravityCompat.END);
                } else {
                    drawerlayout.openDrawer(GravityCompat.END);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onpreviouscallback(int id) {
        ProductFragment f = new ProductFragment();
        Bundle b = new Bundle();
        b.putInt("id",id);
        f.setArguments(b);
        getSupportFragmentManager().beginTransaction().add(R.id.am_mainfl,f).addToBackStack(null).commit();
        Log.d(TAG,"activity called");
        drawerlayout.closeDrawers();
    }
}
