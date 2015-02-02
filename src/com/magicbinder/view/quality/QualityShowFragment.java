/**************************************************************************
 * QualityShowFragment.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.view.quality;


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
import com.magicbinder.entity.Quality;
import com.magicbinder.entity.Binder_Card;
import com.magicbinder.harmony.view.DeleteDialog;
import com.magicbinder.harmony.view.HarmonyFragment;
import com.magicbinder.harmony.view.MultiLoader;
import com.magicbinder.harmony.view.MultiLoader.UriLoadedCallback;
import com.magicbinder.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.magicbinder.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.magicbinder.provider.utils.QualityProviderUtils;
import com.magicbinder.provider.QualityProviderAdapter;
import com.magicbinder.provider.contract.QualityContract;
import com.magicbinder.provider.contract.Binder_CardContract;

/** Quality show fragment.
 *
 * This fragment gives you an interface to show a Quality.
 * 
 * @see android.app.Fragment
 */
public class QualityShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected Quality model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** label View. */
    protected TextView labelView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no Quality. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.labelView =
            (TextView) view.findViewById(
                    R.id.quality_label);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.quality_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.quality_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getLabel() != null) {
            this.labelView.setText(this.model.getLabel());
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
                        R.layout.fragment_quality_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Quality) intent.getParcelableExtra(QualityContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The Quality to get the data from.
     */
    public void update(Quality item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    QualityProviderAdapter.QUALITY_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    QualityShowFragment.this.onQualityLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/binders_cards_quality"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    QualityShowFragment.this.onBinders_cards_qualityLoaded(c);
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
    public void onQualityLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            QualityContract.cursorToItem(
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
    public void onBinders_cards_qualityLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setBinders_cards_quality(Binder_CardContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setBinders_cards_quality(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the QualityEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    QualityEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(QualityContract.PARCEL, this.model);
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
        private Quality item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build QualitySQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final Quality item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new QualityProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                QualityShowFragment.this.onPostDelete();
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

