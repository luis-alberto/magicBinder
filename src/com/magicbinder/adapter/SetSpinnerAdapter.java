package com.magicbinder.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.magicbinder.R;
import com.magicbinder.entity.Set;

/**
 * ArrayAdapter for SetSpinner.
 * @author Luis
 *
 */
public class SetSpinnerAdapter extends ArrayAdapter<Set> {
	/**
	 * Context of activity.
	 */
    private Activity context;
    /**
     * Array list of sets.
     */
    private ArrayList<Set> data = null;
 
    /**
     * Constructor of SetSpinnerAdapter.
     * @param context .
     * @param resource .
     * @param data .
     */
    public SetSpinnerAdapter(Activity context, int resource,
            ArrayList<Set> data) {
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
            row = inflater.inflate(R.layout.row_double_item_spinner, parent, false);
        }
 
        Set item = data.get(position);
 
        if (item != null) { // Parse the data from each object and set it.
            TextView setId = (TextView) row.findViewById(R.id.item_id);
            TextView setName = (TextView) row.findViewById(R.id.item_value);
            if (setId != null) {
                setId.setText(item.getId());
            }
            if (setName != null) {
                setName.setText(item.getName());
            }
 
        }
 
        return row;
    }
}
