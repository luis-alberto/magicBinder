package com.magicbinder.view.binder;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.magicbinder.R;
import com.magicbinder.Test1;
import com.magicbinder.adapter.BinderGridViewAdapter;
import com.magicbinder.entity.Binder;
import com.magicbinder.harmony.view.HarmonyFragment;


public class BinderFragment extends HarmonyFragment{

    private int index;
    private Binder binder;
    
    public BinderFragment(int index, Binder binder){
        // TODO Auto-generated constructor stub
        this.index = index;
        this.binder = binder;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate
                (R.layout.fragment_binder_gridview, container, false);
        GridView gridview = (GridView) view.findViewById(R.id.binder_gridview);
        gridview.setAdapter(new BinderGridViewAdapter(getActivity(),this.binder));
        ((TextView)view.findViewById(R.id.textView))
                .setText(String.valueOf(this.index));
        Button button = (Button)view.findViewById(R.id.test);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity(),Test1.class);
                startActivity(intent);
            }
        });
        return view;

    }

}

