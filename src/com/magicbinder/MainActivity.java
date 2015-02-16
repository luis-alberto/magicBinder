package com.magicbinder;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.FrameLayout;

import com.actionbarsherlock.view.MenuItem;
import com.magicbinder.adapter.BinderPagerAdapter;
import com.magicbinder.data.BinderSQLiteAdapter;
import com.magicbinder.entity.Binder;
import com.magicbinder.harmony.view.HarmonyFragmentActivity;
import com.magicbinder.view.SearchMenuFragment;

public class MainActivity extends HarmonyFragmentActivity
implements ActionBar.TabListener{

    public static final String BINDER_NB = "binder%d";

    private ViewPager viewPager;
    private BinderPagerAdapter binderPagerAdapter;
    private ActionBar actionBar;
    private DrawerLayout mainLoyoutDrawerLayout; //Layout Principal
    private ActionBarDrawerToggle actionBarDrawerToggle; //Gère l'ouverture et la fermeture du menu
    private FrameLayout searchMenuFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //BinderSQLiteAdapter binderAdapter = new BinderSQLiteAdapter(HomeActivity.this);
        //BinderSQLiteAdapter binderAdapter = new BinderSQLiteAdapter(HomeActivity.this);
        ArrayList<Binder> binders= getBinders();


        // Initilization
        mainLoyoutDrawerLayout = (DrawerLayout) findViewById(R.id.main_layout);
        mainLoyoutDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        
        SearchMenuFragment searchMenuFragment = new SearchMenuFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.search_menu, searchMenuFragment);
        transaction.commit();
        
        searchMenuFrameLayout = (FrameLayout) findViewById(R.id.search_menu);
        
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mainLoyoutDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {
 
            public void onDrawerClosed(View view) {
                // TODO Auto-generated method stub
                super.onDrawerClosed(view);
            }
 
            public void onDrawerOpened(View drawerView) {
                // TODO Auto-generated method stub
                // Set the title on the action when drawer open
                getSupportActionBar().setTitle("OPEN");
                super.onDrawerOpened(drawerView);
            }
        };
 
        mainLoyoutDrawerLayout.setDrawerListener(actionBarDrawerToggle);
 
        
 /////////////////////////////////////////////////////////////////////////////////////////////////
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        actionBar = getActionBar();
        //mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        binderPagerAdapter = new BinderPagerAdapter(getSupportFragmentManager(),this);

        viewPager.setAdapter(binderPagerAdapter);
        //actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        

        // Adding Tabs
        for (Binder binder : binders) {
            actionBar.addTab(actionBar.newTab().setText(binder.getId()+binder.getName())
                    .setTabListener(this));
        }

        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
 
        if (item.getItemId() == android.R.id.home) {
 
            if (mainLoyoutDrawerLayout.isDrawerOpen(searchMenuFrameLayout)) {
                mainLoyoutDrawerLayout.closeDrawer(searchMenuFrameLayout);
//                actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            } else {
                mainLoyoutDrawerLayout.openDrawer(searchMenuFrameLayout);
//                actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            }
        }
 
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }
 
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
    ///////////////////////////////////////////////////////////////////////
    @Override
    public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
        // TODO Auto-generated method stub
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
        // TODO Auto-generated method stub

    }
    /**
     * Get Binders from BDD
     * @return ArrayList<Binder>
     */
    public ArrayList<Binder> getBinders(){
        BinderSQLiteAdapter binderAdapter = new BinderSQLiteAdapter(this);
        binderAdapter.open();
        ArrayList<Binder> binders = binderAdapter.getAll();
        binderAdapter.close();
        return binders;
    }
    
}

