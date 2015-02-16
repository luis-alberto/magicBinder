package com.magicbinder.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.magicbinder.R;

public class SimpleSpinnerAdapter extends ArrayAdapter<String>{

    private Activity context;
    ArrayList<String> data = null;

    public SimpleSpinnerAdapter(Activity context, int resource,
            ArrayList<String> data) {
        super(context, resource, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { 
        return super.getView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) { 
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.row_simple_item_spinner, parent, false);
        }

        String item = data.get(position);

        if (item != null) { // Parse the data from each object and set it.
            TextView textView = (TextView) row.findViewById(R.id.item_value);
            if(textView != null) {
                textView.setText(item);
            }

        }

        return row;
    }
}
