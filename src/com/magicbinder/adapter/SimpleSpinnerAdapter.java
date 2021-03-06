package com.magicbinder.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.magicbinder.R;
/**
 * ArrayAdapter for a simple spinner.
 * @author Luis
 *
 */
public class SimpleSpinnerAdapter extends ArrayAdapter<String>{
	/**
	 * Context of activity.
	 */
    private Activity context;
    /**
     * Arraylist of strings.
     */
    ArrayList<String> data = null;

    /**
     * Constructor of SimpleSpinnerAdapter.
     * @param context .
     * @param resource .
     * @param data .
     */
    public SimpleSpinnerAdapter(Activity context, int resource,
            ArrayList<String> data) {
        super(context, resource, data);
        this.context = context;
        this.data = data;
    }

    /**
     * Get view of fragment.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) { 
        return super.getView(position, convertView, parent);
    }

    /**
     * Create and build dropdowns component.
     */
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
