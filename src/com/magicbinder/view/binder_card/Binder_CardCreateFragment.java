/**************************************************************************
 * Binder_CardCreateFragment.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.view.binder_card;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.magicbinder.R;
import com.magicbinder.entity.Binder_Card;
import com.magicbinder.entity.Card;
import com.magicbinder.entity.Binder;
import com.magicbinder.entity.Quality;

import com.magicbinder.harmony.view.HarmonyFragmentActivity;
import com.magicbinder.harmony.view.HarmonyFragment;

import com.magicbinder.harmony.widget.SingleEntityWidget;
import com.magicbinder.menu.SaveMenuWrapper.SaveMenuInterface;
import com.magicbinder.provider.utils.Binder_CardProviderUtils;
import com.magicbinder.provider.utils.CardProviderUtils;
import com.magicbinder.provider.utils.BinderProviderUtils;
import com.magicbinder.provider.utils.QualityProviderUtils;

/**
 * Binder_Card create fragment.
 *
 * This fragment gives you an interface to create a Binder_Card.
 */
public class Binder_CardCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Binder_Card model = new Binder_Card();

    /** Fields View. */
    /** quantity View. */
    protected EditText quantityView;
    /** The card chooser component. */
    protected SingleEntityWidget cardWidget;
    /** The card Adapter. */
    protected SingleEntityWidget.EntityAdapter<Card> 
                cardAdapter;
    /** The binder chooser component. */
    protected SingleEntityWidget binderWidget;
    /** The binder Adapter. */
    protected SingleEntityWidget.EntityAdapter<Binder> 
                binderAdapter;
    /** The quality chooser component. */
    protected SingleEntityWidget qualityWidget;
    /** The quality Adapter. */
    protected SingleEntityWidget.EntityAdapter<Quality> 
                qualityAdapter;

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.quantityView =
            (EditText) view.findViewById(R.id.binder_card_quantity);
        this.cardAdapter = 
                new SingleEntityWidget.EntityAdapter<Card>() {
            @Override
            public String entityToString(Card item) {
                return String.valueOf(item.getId());
            }
        };
        this.cardWidget =
            (SingleEntityWidget) view.findViewById(R.id.binder_card_card_button);
        this.cardWidget.setAdapter(this.cardAdapter);
        this.cardWidget.setTitle(R.string.binder_card_card_dialog_title);
        this.binderAdapter = 
                new SingleEntityWidget.EntityAdapter<Binder>() {
            @Override
            public String entityToString(Binder item) {
                return String.valueOf(item.getId());
            }
        };
        this.binderWidget =
            (SingleEntityWidget) view.findViewById(R.id.binder_card_binder_button);
        this.binderWidget.setAdapter(this.binderAdapter);
        this.binderWidget.setTitle(R.string.binder_card_binder_dialog_title);
        this.qualityAdapter = 
                new SingleEntityWidget.EntityAdapter<Quality>() {
            @Override
            public String entityToString(Quality item) {
                return String.valueOf(item.getId());
            }
        };
        this.qualityWidget =
            (SingleEntityWidget) view.findViewById(R.id.binder_card_quality_button);
        this.qualityWidget.setAdapter(this.qualityAdapter);
        this.qualityWidget.setTitle(R.string.binder_card_quality_dialog_title);
    }

    /** Load data from model to fields view. */
    public void loadData() {

        this.quantityView.setText(String.valueOf(this.model.getQuantity()));

        new LoadTask(this).execute();
    }

    /** Save data from fields view to model. */
    public void saveData() {

        this.model.setQuantity(Integer.parseInt(
                    this.quantityView.getEditableText().toString()));

        this.model.setCard(this.cardAdapter.getSelectedItem());

        this.model.setBinder(this.binderAdapter.getSelectedItem());

        this.model.setQuality(this.qualityAdapter.getSelectedItem());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.quantityView.getText().toString().trim())) {
            error = R.string.binder_card_quantity_invalid_field_error;
        }
        if (this.cardAdapter.getSelectedItem() == null) {
            error = R.string.binder_card_card_invalid_field_error;
        }
        if (this.binderAdapter.getSelectedItem() == null) {
            error = R.string.binder_card_binder_invalid_field_error;
        }
        if (this.qualityAdapter.getSelectedItem() == null) {
            error = R.string.binder_card_quality_invalid_field_error;
        }
    
        if (error > 0) {
            Toast.makeText(this.getActivity(),
                this.getActivity().getString(error),
                Toast.LENGTH_SHORT).show();
        }
        return error == 0;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(
                R.layout.fragment_binder_card_create,
                container,
                false);

        this.initializeComponent(view);
        this.loadData();
        return view;
    }

    /**
     * This class will save the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class CreateTask extends AsyncTask<Void, Void, Uri> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Entity to persist. */
        private final Binder_Card entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final Binder_CardCreateFragment fragment,
                final Binder_Card entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.binder_card_progress_save_title),
                    this.ctx.getString(
                            R.string.binder_card_progress_save_message));
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new Binder_CardProviderUtils(this.ctx).insert(
                        this.entity);

            return result;
        }

        @Override
        protected void onPostExecute(Uri result) {
            super.onPostExecute(result);
            if (result != null) {
                final HarmonyFragmentActivity activity =
                                         (HarmonyFragmentActivity) this.ctx;
                activity.finish();
            } else {
                final AlertDialog.Builder builder =
                        new AlertDialog.Builder(this.ctx);
                builder.setIcon(0);
                builder.setMessage(
                        this.ctx.getString(
                                R.string.binder_card_error_create));
                builder.setPositiveButton(
                        this.ctx.getString(android.R.string.yes),
                        new Dialog.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int which) {

                            }
                        });
                builder.show();
            }

            this.progress.dismiss();
        }
    }

    /**
     * This class will save the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class LoadTask extends AsyncTask<Void, Void, Void> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Progress Dialog. */
        private ProgressDialog progress;
        /** Fragment. */
        private Binder_CardCreateFragment fragment;
        /** card list. */
        private ArrayList<Card> cardList;
        /** binder list. */
        private ArrayList<Binder> binderList;
        /** quality list. */
        private ArrayList<Quality> qualityList;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final Binder_CardCreateFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.binder_card_progress_load_relations_title),
                    this.ctx.getString(
                            R.string.binder_card_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.cardList = 
                new CardProviderUtils(this.ctx).queryAll();
            this.binderList = 
                new BinderProviderUtils(this.ctx).queryAll();
            this.qualityList = 
                new QualityProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.cardAdapter.loadData(this.cardList);
            this.fragment.binderAdapter.loadData(this.binderList);
            this.fragment.qualityAdapter.loadData(this.qualityList);
            this.progress.dismiss();
        }
    }

    @Override
    public void onClickSave() {
        if (this.validateData()) {
            this.saveData();
            new CreateTask(this, this.model).execute();
        }
    }
}
