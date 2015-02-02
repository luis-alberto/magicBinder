/**************************************************************************
 * Binder_CardShowFragment.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.view.binder_card;


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
import com.magicbinder.entity.Binder_Card;
import com.magicbinder.harmony.view.DeleteDialog;
import com.magicbinder.harmony.view.HarmonyFragment;
import com.magicbinder.harmony.view.MultiLoader;
import com.magicbinder.harmony.view.MultiLoader.UriLoadedCallback;
import com.magicbinder.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.magicbinder.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.magicbinder.provider.utils.Binder_CardProviderUtils;
import com.magicbinder.provider.Binder_CardProviderAdapter;
import com.magicbinder.provider.contract.Binder_CardContract;
import com.magicbinder.provider.contract.CardContract;
import com.magicbinder.provider.contract.BinderContract;
import com.magicbinder.provider.contract.QualityContract;

/** Binder_Card show fragment.
 *
 * This fragment gives you an interface to show a Binder_Card.
 * 
 * @see android.app.Fragment
 */
public class Binder_CardShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected Binder_Card model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** quantity View. */
    protected TextView quantityView;
    /** card View. */
    protected TextView cardView;
    /** binder View. */
    protected TextView binderView;
    /** quality View. */
    protected TextView qualityView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no Binder_Card. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.quantityView =
            (TextView) view.findViewById(
                    R.id.binder_card_quantity);
        this.cardView =
            (TextView) view.findViewById(
                    R.id.binder_card_card);
        this.binderView =
            (TextView) view.findViewById(
                    R.id.binder_card_binder);
        this.qualityView =
            (TextView) view.findViewById(
                    R.id.binder_card_quality);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.binder_card_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.binder_card_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        this.quantityView.setText(String.valueOf(this.model.getQuantity()));
        if (this.model.getCard() != null) {
            this.cardView.setText(
                    String.valueOf(this.model.getCard().getId()));
        }
        if (this.model.getBinder() != null) {
            this.binderView.setText(
                    String.valueOf(this.model.getBinder().getId()));
        }
        if (this.model.getQuality() != null) {
            this.qualityView.setText(
                    String.valueOf(this.model.getQuality().getId()));
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
                        R.layout.fragment_binder_card_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Binder_Card) intent.getParcelableExtra(Binder_CardContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The Binder_Card to get the data from.
     */
    public void update(Binder_Card item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    Binder_CardProviderAdapter.BINDER_CARD_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    Binder_CardShowFragment.this.onBinder_CardLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/card"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    Binder_CardShowFragment.this.onCardLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/binder"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    Binder_CardShowFragment.this.onBinderLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/quality"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    Binder_CardShowFragment.this.onQualityLoaded(c);
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
    public void onBinder_CardLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            Binder_CardContract.cursorToItem(
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
    public void onCardLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setCard(CardContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setCard(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onBinderLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setBinder(BinderContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setBinder(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onQualityLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setQuality(QualityContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setQuality(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the Binder_CardEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    Binder_CardEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(Binder_CardContract.PARCEL, this.model);
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
        private Binder_Card item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build Binder_CardSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final Binder_Card item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new Binder_CardProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                Binder_CardShowFragment.this.onPostDelete();
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

