package com.magicbinder.view.binder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.magicbinder.R;
import com.magicbinder.adapter.BinderGridViewAdapter;
import com.magicbinder.entity.Binder;
import com.magicbinder.harmony.view.HarmonyFragment;

/**
 * BinderFragment
 * @author Luis
 *
 */
public class BinderFragment extends HarmonyFragment{

    /**
     * Binder.
     */
    private Binder binder;
    
    /**
     * Constructorof BinderFragment.
     * @param binder to show
     */
    public BinderFragment(Binder binder){
        this.binder = binder;
    }
    
    /**
     * Create BinderFragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(
        		R.layout.fragment_binder_gridview, container, false);
        setRetainInstance(true);
        //create and binder gridview.
        GridView gridview = (GridView) view.findViewById(R.id.binder_gridview);
        gridview.setAdapter(new BinderGridViewAdapter(getActivity(),this.binder));
        return view;
    }

}

