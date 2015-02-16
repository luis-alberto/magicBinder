package com.magicbinder.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.magicbinder.data.BinderSQLiteAdapter;
import com.magicbinder.entity.Binder;
import com.magicbinder.view.binder.BinderFragment;



public class BinderPagerAdapter extends FragmentPagerAdapter{
    private ArrayList<Binder> binders;
    
    public BinderPagerAdapter(FragmentManager fragmnetManager,Context context) {
        // TODO Auto-generated constructor stub
        super(fragmnetManager);
        BinderSQLiteAdapter binderAdapter = new BinderSQLiteAdapter(context);
        binderAdapter.open();
        this.binders = binderAdapter.getAll();
        binderAdapter.close();
    }
    @Override
    public Fragment getItem(int index) {
        if (!this.binders.isEmpty()){
            return new BinderFragment(index,this.binders.get(index));
        }
        return null;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return this.binders.size();
    }

}

