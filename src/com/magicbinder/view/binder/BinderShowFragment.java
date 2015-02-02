/**************************************************************************
 * BinderShowFragment.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.view.binder;


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
import com.magicbinder.entity.Binder;
import com.magicbinder.entity.Binder_Card;
import com.magicbinder.harmony.view.DeleteDialog;
import com.magicbinder.harmony.view.HarmonyFragment;
import com.magicbinder.harmony.view.MultiLoader;
import com.magicbinder.harmony.view.MultiLoader.UriLoadedCallback;
import com.magicbinder.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.magicbinder.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.magicbinder.provider.utils.BinderProviderUtils;
import com.magicbinder.provider.BinderProviderAdapter;
import com.magicbinder.provider.contract.BinderContract;
import com.magicbinder.provider.contract.Binder_CardContract;

/** Binder show fragment.
 *
 * This fragment gives you an interface to show a Binder.
 * 
 * @see android.app.Fragment
 */
public class BinderShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected Binder model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** name View. */
    protected TextView nameView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no Binder. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nameView =
            (TextView) view.findViewById(
                    R.id.binder_name);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.binder_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.binder_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getName() != null) {
            this.nameView.setText(this.model.getName());
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
                        R.layout.fragment_binder_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Binder) intent.getParcelableExtra(BinderContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The Binder to get the data from.
     */
    public void update(Binder item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    BinderProviderAdapter.BINDER_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    BinderShowFragment.this.onBinderLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/binders_cards_binder"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    BinderShowFragment.this.onBinders_cards_binderLoaded(c);
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
    public void onBinderLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            BinderContract.cursorToItem(
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
    public void onBinders_cards_binderLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setBinders_cards_binder(Binder_CardContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setBinders_cards_binder(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the BinderEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    BinderEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(BinderContract.PARCEL, this.model);
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
        private Binder item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build BinderSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final Binder item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new BinderProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                BinderShowFragment.this.onPostDelete();
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

