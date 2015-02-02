/**************************************************************************
 * BinderListAdapter.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.view.binder;

import java.util.List;

import com.magicbinder.R;


import android.util.AttributeSet;
import android.view.View;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.magicbinder.harmony.view.HarmonyFragmentActivity;
import com.google.android.pinnedheader.SelectionItemView;
import com.google.android.pinnedheader.headerlist.HeaderAdapter;
import com.google.android.pinnedheader.headerlist.HeaderSectionIndexer;
import com.google.android.pinnedheader.headerlist.PinnedHeaderListView.PinnedHeaderAdapter;
import com.magicbinder.entity.Binder;

/**
 * List adapter for Binder entity.
 */
public class BinderListAdapter
        extends HeaderAdapter<Binder>
        implements PinnedHeaderAdapter {
    /**
     * Constructor.
     * @param ctx context
     */
    public BinderListAdapter(android.content.Context ctx) {
        super(ctx);
    }

    /**
     * Constructor.
     * 
     * @param context The context
     * @param resource The resource
     * @param textViewResourceId The resource id of the text view
     * @param objects The list of objects of this adapter
     */
    public BinderListAdapter(android.content.Context context,
            int resource,
            int textViewResourceId,
            List<Binder> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    /**
     * Constructor.
     *
     * @param context The context
     * @param resource The resource
     * @param textViewResourceId The resource id of the text view
     * @param objects The list of objects of this adapter
     */
    public BinderListAdapter(android.content.Context context,
            int resource,
            int textViewResourceId,
            Binder[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    /**
     * Constructor.
     * 
     * @param context The context
     * @param resource The resource
     * @param textViewResourceId The resource id of the text view
     */
    public BinderListAdapter(android.content.Context context,
            int resource,
            int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    /**
     * Constructor.
     * 
     * @param context The context
     * @param textViewResourceId The resource id of the text view
     * @param objects The list of objects of this adapter
     */
    public BinderListAdapter(android.content.Context context,
            int textViewResourceId,
            List<Binder> objects) {
        super(context, textViewResourceId, objects);
    }

    /**
     * Constructor.
     * 
     * @param context The context
     * @param textViewResourceId The resource id of the text view
     * @param objects The list of objects of this adapter
     */
    public BinderListAdapter(android.content.Context context,
            int textViewResourceId,
            Binder[] objects) {
        super(context, textViewResourceId, objects);
    }

    /**
     * Constructor.
     * 
     * @param context The context
     * @param textViewResourceId The resource id of the text view
     */
    public BinderListAdapter(android.content.Context context,
            int textViewResourceId) {
        super(context, textViewResourceId);
    }

    /** Holder row. */
    private static class ViewHolder extends SelectionItemView {

        /**
         * Constructor.
         *
         * @param context The context
         */
        public ViewHolder(android.content.Context context) {
            this(context, null);
        }
        
        /**
         * Constructor.
         *
         * @param context The context
         * @param attrs The attribute set
         */
        public ViewHolder(android.content.Context context, AttributeSet attrs) {
            super(context, attrs, R.layout.row_binder);
        }

        /** Populate row with a Binder.
         *
         * @param model Binder data
         */
        public void populate(final Binder model) {
            View convertView = this.getInnerLayout();
            TextView nameView =
                (TextView) convertView.findViewById(
                        R.id.row_binder_name);


            if (model.getName() != null) {
                nameView.setText(model.getName());
            }
        }
    }

    /** Section indexer for this entity's list. */
    public static class BinderSectionIndexer
                    extends HeaderSectionIndexer<Binder>
                    implements SectionIndexer {

        /**
         * Constructor.
         * @param items The items of the indexer
         */
        public BinderSectionIndexer(List<Binder> items) {
            super(items);
        }
        
        @Override
        protected String getHeaderText(Binder item) {
            return "Your entity's header name here";
        }
    }

    @Override
    protected View bindView(View itemView,
                int partition,
                Binder item,
                int position) {
        final ViewHolder view;
        
        if (itemView != null) {
            view = (ViewHolder) itemView;
        } else {
            view = new ViewHolder(this.getContext());
        }

        if (!((HarmonyFragmentActivity) this.getContext()).isDualMode()) {
            view.setActivatedStateSupported(false);
        }
        
        view.populate(item);
        this.bindSectionHeaderAndDivider(view, position);
        
        return view;
    }

    @Override
    public int getPosition(Binder item) {
        int result = -1;
        if (item != null) {
            for (int i = 0; i < this.getCount(); i++) {
                if (item.getId() == this.getItem(i).getId()) {
                    result = i;
                }
            }
        }
        return result;
    }
}
