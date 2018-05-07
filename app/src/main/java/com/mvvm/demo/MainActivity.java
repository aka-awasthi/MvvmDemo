package com.mvvm.demo;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.mvvm.demo.api.sampleapi.SampleApi;
import com.mvvm.demo.db.AppDatabase;
import com.mvvm.demo.fragment.CategoryFragment;
import com.mvvm.demo.fragment.ProductFragment;
import com.mvvm.demo.interfaces.CalltoParent;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
