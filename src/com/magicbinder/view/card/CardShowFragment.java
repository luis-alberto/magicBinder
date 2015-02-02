/**************************************************************************
 * CardShowFragment.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.view.card;


import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.magicbinder.R;
import com.magicbinder.entity.Card;
import com.magicbinder.entity.Color;
import com.magicbinder.entity.Binder_Card;
import com.magicbinder.harmony.view.DeleteDialog;
import com.magicbinder.harmony.view.HarmonyFragment;
import com.magicbinder.harmony.view.MultiLoader;
import com.magicbinder.harmony.view.MultiLoader.UriLoadedCallback;
import com.magicbinder.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.magicbinder.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.magicbinder.provider.utils.CardProviderUtils;
import com.magicbinder.provider.CardProviderAdapter;
import com.magicbinder.provider.contract.CardContract;
import com.magicbinder.provider.contract.ColorContract;
import com.magicbinder.provider.contract.Binder_CardContract;

/** Card show fragment.
 *
 * This fragment gives you an interface to show a Card.
 * 
 * @see android.app.Fragment
 */
public class CardShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected Card model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** id View. */
    protected TextView idView;
    /** name View. */
    protected TextView nameView;
    /** image View. */
    protected TextView imageView;
    /** convertedManaCost View. */
    protected TextView convertedManaCostView;
    /** typeCard View. */
    protected TextView typeCardView;
    /** rarity View. */
    protected TextView rarityView;
    /** cardSetId View. */
    protected TextView cardSetIdView;
    /** binders_cards_card View. */
    protected TextView binders_cards_cardView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no Card. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.idView =
            (TextView) view.findViewById(
                    R.id.card_id);
        this.nameView =
            (TextView) view.findViewById(
                    R.id.card_name);
        this.imageView =
            (TextView) view.findViewById(
                    R.id.card_image);
        this.convertedManaCostView =
            (TextView) view.findViewById(
                    R.id.card_convertedmanacost);
        this.typeCardView =
            (TextView) view.findViewById(
                    R.id.card_typecard);
        this.rarityView =
            (TextView) view.findViewById(
                    R.id.card_rarity);
        this.cardSetIdView =
            (TextView) view.findViewById(
                    R.id.card_cardsetid);
        this.binders_cards_cardView =
            (TextView) view.findViewById(
                    R.id.card_binders_cards_card);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.card_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.card_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        this.idView.setText(String.valueOf(this.model.getId()));
        if (this.model.getName() != null) {
            this.nameView.setText(this.model.getName());
        }
        if (this.model.getImage() != null) {
            this.imageView.setText(this.model.getImage());
        }
        this.convertedManaCostView.setText(String.valueOf(this.model.getConvertedManaCost()));
        if (this.model.getTypeCard() != null) {
            this.typeCardView.setText(this.model.getTypeCard());
        }
        if (this.model.getRarity() != null) {
            this.rarityView.setText(this.model.getRarity());
        }
        if (this.model.getCardSetId() != null) {
            this.cardSetIdView.setText(this.model.getCardSetId());
        }
        if (this.model.getBinders_cards_card() != null) {
            String binders_cards_cardValue = "";
            for (Binder_Card item : this.model.getBinders_cards_card()) {
                binders_cards_cardValue += item.getId() + ",";
            }
            this.binders_cards_cardView.setText(binders_cards_cardValue);
        }
        } else {
            this.dataLayout.setVisibility(View.GONE);
            this.emptyText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view =
                inflater.inflate(
                        R.layout.fragment_card_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Card) intent.getParcelableExtra(CardContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The Card to get the data from.
     */
    public void update(Card item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    CardProviderAdapter.CARD_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    CardShowFragment.this.onCardLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/colors"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    CardShowFragment.this.onColorsLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/binders_cards_card"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    CardShowFragment.this.onBinders_cards_cardLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.init();
        }
    }

    /**
     * Called when the entity has been loaded.
     * 
     * @param c The cursor of this entity
     */
    public void onCardLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            CardContract.cursorToItem(
                        c,
                        this.model);
            this.loadData();
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onColorsLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setColors(ColorContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setColors(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onBinders_cards_cardLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setBinders_cards_card(Binder_CardContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setBinders_cards_card(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the CardEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    CardEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(CardContract.PARCEL, this.model);
        intent.putExtras(extras);

        this.getActivity().startActivity(intent);
    }
    /**
     * Shows a confirmation dialog.
     */
    @Override
    public void onClickDelete() {
        new DeleteDialog(this.getActivity(), this).show();
    }

    @Override
    public void onDeleteDialogClose(boolean ok) {
        if (ok) {
            new DeleteTask(this.getActivity(), this.model).execute();
        }
    }
    
    /** 
     * Called when delete task is done.
     */    
    public void onPostDelete() {
        if (this.deleteCallback != null) {
            this.deleteCallback.onItemDeleted();
        }
    }

    /**
     * This class will remove the entity into the DB.
     * It runs asynchronously.
     */
    private class DeleteTask extends AsyncTask<Void, Void, Integer> {
        /** AsyncTask's context. */
        private android.content.Context ctx;
        /** Entity to delete. */
        private Card item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build CardSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final Card item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new CardProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                CardShowFragment.this.onPostDelete();
            }
            super.onPostExecute(result);
        }
        
        

    }

    /**
     * Callback for item deletion.
     */ 
    public interface DeleteCallback {
        /** Called when current item has been deleted. */
        void onItemDeleted();
    }
}

