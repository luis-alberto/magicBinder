package com.magicbinder.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.magicbinder.data.BinderSQLiteAdapter;
import com.magicbinder.entity.Binder;
import com.magicbinder.view.binder.BinderFragment;

/**
 * BinderPagerAdapter FragmentPagerAdapter.
 * @author Luis
 *
 */
public class BinderPagerAdapter extends FragmentPagerAdapter{
	/**
	 * Binders
	 */
    private ArrayList<Binder> binders;
    
    /**
     * Constructor of BinderPagerAdapter.
     * @param fragmnetManager
     * @param context
     */
    public BinderPagerAdapter(FragmentManager fragmnetManager,Context context) {
        super(fragmnetManager);
        BinderSQLiteAdapter binderAdapter = new BinderSQLiteAdapter(context);
        binderAdapter.open();
        this.binders = binderAdapter.getAll();
        binderAdapter.close();
    }

    /**
     * GetItem of BinderPager.
     * @param index from BinderPager.
     * @return BinderFragment in index position. Null if no binders.
     */
    @Override
    public Fragment getItem(int index) {
        if (!this.binders.isEmpty()){
            return new BinderFragment(this.binders.get(index));
        }
        return null;
    }

    /**
     * Count binders.
     * @return Numbers of binders.
     */
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return this.binders.size();
    }
}
