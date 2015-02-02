/**************************************************************************
 * CardListAdapter.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.view.card;

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
import com.magicbinder.entity.Card;

/**
 * List adapter for Card entity.
 */
public class CardListAdapter
        extends HeaderAdapter<Card>
        implements PinnedHeaderAdapter {
    /**
     * Constructor.
     * @param ctx context
     */
    public CardListAdapter(android.content.Context ctx) {
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
    public CardListAdapter(android.content.Context context,
            int resource,
            int textViewResourceId,
            List<Card> objects) {
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
    public CardListAdapter(android.content.Context context,
            int resource,
            int textViewResourceId,
            Card[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    /**
     * Constructor.
     * 
     * @param context The context
     * @param resource The resource
     * @param textViewResourceId The resource id of the text view
     */
    public CardListAdapter(android.content.Context context,
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
    public CardListAdapter(android.content.Context context,
            int textViewResourceId,
            List<Card> objects) {
        super(context, textViewResourceId, objects);
    }

    /**
     * Constructor.
     * 
     * @param context The context
     * @param textViewResourceId The resource id of the text view
     * @param objects The list of objects of this adapter
     */
    public CardListAdapter(android.content.Context context,
            int textViewResourceId,
            Card[] objects) {
        super(context, textViewResourceId, objects);
    }

    /**
     * Constructor.
     * 
     * @param context The context
     * @param textViewResourceId The resource id of the text view
     */
    public CardListAdapter(android.content.Context context,
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
            super(context, attrs, R.layout.row_card);
        }

        /** Populate row with a Card.
         *
         * @param model Card data
         */
        public void populate(final Card model) {
            View convertView = this.getInnerLayout();
            TextView idView =
                (TextView) convertView.findViewById(
                        R.id.row_card_id);
            TextView nameView =
                (TextView) convertView.findViewById(
                        R.id.row_card_name);
            TextView imageView =
                (TextView) convertView.findViewById(
                        R.id.row_card_image);
            TextView convertedManaCostView =
                (TextView) convertView.findViewById(
                        R.id.row_card_convertedmanacost);
            TextView typeCardView =
                (TextView) convertView.findViewById(
                        R.id.row_card_typecard);
            TextView rarityView =
                (TextView) convertView.findViewById(
                        R.id.row_card_rarity);
            TextView cardSetIdView =
                (TextView) convertView.findViewById(
                        R.id.row_card_cardsetid);


            idView.setText(String.valueOf(model.getId()));
            if (model.getName() != null) {
                nameView.setText(model.getName());
            }
            if (model.getImage() != null) {
                imageView.setText(model.getImage());
            }
            convertedManaCostView.setText(String.valueOf(model.getConvertedManaCost()));
            if (model.getTypeCard() != null) {
                typeCardView.setText(model.getTypeCard());
            }
            if (model.getRarity() != null) {
                rarityView.setText(model.getRarity());
            }
            if (model.getCardSetId() != null) {
                cardSetIdView.setText(model.getCardSetId());
            }
        }
    }

    /** Section indexer for this entity's list. */
    public static class CardSectionIndexer
                    extends HeaderSectionIndexer<Card>
                    implements SectionIndexer {

        /**
         * Constructor.
         * @param items The items of the indexer
         */
        public CardSectionIndexer(List<Card> items) {
            super(items);
        }
        
        @Override
        protected String getHeaderText(Card item) {
            return "Your entity's header name here";
        }
    }

    @Override
    protected View bindView(View itemView,
                int partition,
                Card item,
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
    public int getPosition(Card item) {
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
