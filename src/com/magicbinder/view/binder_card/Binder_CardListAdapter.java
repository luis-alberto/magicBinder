/**************************************************************************
 * Binder_CardListAdapter.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.view.binder_card;

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
import com.magicbinder.entity.Binder_Card;

/**
 * List adapter for Binder_Card entity.
 */
public class Binder_CardListAdapter
        extends HeaderAdapter<Binder_Card>
        implements PinnedHeaderAdapter {
    /**
     * Constructor.
     * @param ctx context
     */
    public Binder_CardListAdapter(android.content.Context ctx) {
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
    public Binder_CardListAdapter(android.content.Context context,
            int resource,
            int textViewResourceId,
            List<Binder_Card> objects) {
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
    public Binder_CardListAdapter(android.content.Context context,
            int resource,
            int textViewResourceId,
            Binder_Card[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    /**
     * Constructor.
     * 
     * @param context The context
     * @param resource The resource
     * @param textViewResourceId The resource id of the text view
     */
    public Binder_CardListAdapter(android.content.Context context,
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
    public Binder_CardListAdapter(android.content.Context context,
            int textViewResourceId,
            List<Binder_Card> objects) {
        super(context, textViewResourceId, objects);
    }

    /**
     * Constructor.
     * 
     * @param context The context
     * @param textViewResourceId The resource id of the text view
     * @param objects The list of objects of this adapter
     */
    public Binder_CardListAdapter(android.content.Context context,
            int textViewResourceId,
            Binder_Card[] objects) {
        super(context, textViewResourceId, objects);
    }

    /**
     * Constructor.
     * 
     * @param context The context
     * @param textViewResourceId The resource id of the text view
     */
    public Binder_CardListAdapter(android.content.Context context,
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
            super(context, attrs, R.layout.row_binder_card);
        }

        /** Populate row with a Binder_Card.
         *
         * @param model Binder_Card data
         */
        public void populate(final Binder_Card model) {
            View convertView = this.getInnerLayout();
            TextView quantityView =
                (TextView) convertView.findViewById(
                        R.id.row_binder_card_quantity);
            TextView cardView =
                (TextView) convertView.findViewById(
                        R.id.row_binder_card_card);
            TextView binderView =
                (TextView) convertView.findViewById(
                        R.id.row_binder_card_binder);
            TextView qualityView =
                (TextView) convertView.findViewById(
                        R.id.row_binder_card_quality);


            quantityView.setText(String.valueOf(model.getQuantity()));
            if (model.getCard() != null) {
                cardView.setText(
                        String.valueOf(model.getCard().getId()));
            }
            if (model.getBinder() != null) {
                binderView.setText(
                        String.valueOf(model.getBinder().getId()));
            }
            if (model.getQuality() != null) {
                qualityView.setText(
                        String.valueOf(model.getQuality().getId()));
            }
        }
    }

    /** Section indexer for this entity's list. */
    public static class Binder_CardSectionIndexer
                    extends HeaderSectionIndexer<Binder_Card>
                    implements SectionIndexer {

        /**
         * Constructor.
         * @param items The items of the indexer
         */
        public Binder_CardSectionIndexer(List<Binder_Card> items) {
            super(items);
        }
        
        @Override
        protected String getHeaderText(Binder_Card item) {
            return "Your entity's header name here";
        }
    }

    @Override
    protected View bindView(View itemView,
                int partition,
                Binder_Card item,
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
    public int getPosition(Binder_Card item) {
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
