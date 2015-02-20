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
import com.magicbinder.view.search.SearchMenuFragment;

/**
 * Main Activity
 * @author Luis
 *
 */
public class MainActivity extends HarmonyFragmentActivity
implements ActionBar.TabListener{

	/**
	 * Create binder name.
	 */
    public static final String BINDER_NB = "binder%d";
    /**
     * ViewPager.
     */
    private ViewPager viewPager;
    /**
     * PagerAdapter for Binder.
     */
    private BinderPagerAdapter binderPagerAdapter;
    /**
     * ActionBa.r
     */
    private ActionBar actionBar;
    /**
     * DrawerLayout from main_activity.
     */
    private DrawerLayout mainLoyoutDrawerLayout;
    /**
     * ActionBarDrawerToggle.
     */
    private ActionBarDrawerToggle actionBarDrawerToggle;
    /**
     * SearchMenuLayout.
     */    private FrameLayout searchMenuFrameLayout;

    /**
     * Creating activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Initilization
        ArrayList<Binder> binders= getBinders();

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
                R.drawable.ic_action_search, R.string.drawer_open,
                R.string.drawer_close) {
        	/**
        	 * Action when close drawer.
        	 */
            public void onDrawerClosed(View view) {
                // TODO Auto-generated method stub
                getSupportActionBar().setTitle(R.string.app_name);
                super.onDrawerClosed(view);
            }
 
            /**
             * Action when open drawer.
             */
            public void onDrawerOpened(View drawerView) {
                // TODO Auto-generated method stub
                // Set the title on the action when drawer open
                getSupportActionBar().setTitle(R.string.search_search_button);
                super.onDrawerOpened(drawerView);
            }
        };
 
        mainLoyoutDrawerLayout.setDrawerListener(actionBarDrawerToggle);
 
        //Contruction of viewpager
        actionBar = getActionBar();
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        binderPagerAdapter = new BinderPagerAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(binderPagerAdapter);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        

        // Adding Tabs
        for (Binder binder : binders) {
            actionBar.addTab(actionBar.newTab().setText(binder.getName())
                    .setTabListener(this));
        }

        /**
         * on swiping the viewpager make respective tab selected.
         */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        	/**.
        	 * Select Tab when selected pager.
        	 */
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

    /**
     * Update fragment when focus activity.
     */
    @Override
    protected void onPostResume() {
        super.onPostResume();
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        binderPagerAdapter = new BinderPagerAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(binderPagerAdapter);
        viewPager.setCurrentItem(actionBar.getSelectedTab().getPosition());
        if (mainLoyoutDrawerLayout.isDrawerOpen(searchMenuFrameLayout)) {
            mainLoyoutDrawerLayout.closeDrawer(searchMenuFrameLayout);
        }
    }
    
    /**
     * Update Fragment for tab selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
 
        if (item.getItemId() == android.R.id.home) {
 
            if (mainLoyoutDrawerLayout.isDrawerOpen(searchMenuFrameLayout)) {
                mainLoyoutDrawerLayout.closeDrawer(searchMenuFrameLayout);
            } else {
                mainLoyoutDrawerLayout.openDrawer(searchMenuFrameLayout);
            }
        }
 
        return super.onOptionsItemSelected(item);
    }
    /**
     * OnPostCreated actionBarDrawerToggle.
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    /**
     * OnConfigurationChanged to actionBarDrawerToggle.
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * onTabSelected action
     */
    @Override
    public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
        //close drawer if change tab
        viewPager.setCurrentItem(tab.getPosition());
        if (mainLoyoutDrawerLayout.isDrawerOpen(searchMenuFrameLayout)) {
            mainLoyoutDrawerLayout.closeDrawer(searchMenuFrameLayout);
        }
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
     * Get Binders from BDD.
     * @return ArrayList<Binder> Get all Binders from BDD.
     */
    public ArrayList<Binder> getBinders(){
        BinderSQLiteAdapter binderAdapter = new BinderSQLiteAdapter(this);
        binderAdapter.open();
        ArrayList<Binder> binders = binderAdapter.getAll();
        binderAdapter.close();
        return binders;
    }
    
}

