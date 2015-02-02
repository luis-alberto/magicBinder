/**************************************************************************
 * CardEditFragment.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.view.card;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
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
import com.magicbinder.entity.Card;
import com.magicbinder.entity.Color;
import com.magicbinder.entity.Binder_Card;

import com.magicbinder.harmony.view.HarmonyFragmentActivity;
import com.magicbinder.harmony.view.HarmonyFragment;
import com.magicbinder.harmony.widget.MultiEntityWidget;
import com.magicbinder.menu.SaveMenuWrapper.SaveMenuInterface;
import com.magicbinder.provider.CardProviderAdapter;
import com.magicbinder.provider.utils.CardProviderUtils;
import com.magicbinder.provider.utils.ColorProviderUtils;
import com.magicbinder.provider.utils.Binder_CardProviderUtils;
import com.magicbinder.provider.contract.CardContract;
import com.magicbinder.provider.contract.ColorContract;
import com.magicbinder.provider.contract.Binder_CardContract;

/** Card create fragment.
 *
 * This fragment gives you an interface to edit a Card.
 *
 * @see android.app.Fragment
 */
public class CardEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Card model = new Card();

    /** curr.fields View. */
    /** id View. */
    protected EditText idView;
    /** name View. */
    protected EditText nameView;
    /** image View. */
    protected EditText imageView;
    /** convertedManaCost View. */
    protected EditText convertedManaCostView;
    /** typeCard View. */
    protected EditText typeCardView;
    /** rarity View. */
    protected EditText rarityView;
    /** cardSetId View. */
    protected EditText cardSetIdView;
    /** The binders_cards_card chooser component. */
    protected MultiEntityWidget binders_cards_cardWidget;
    /** The binders_cards_card Adapter. */
    protected MultiEntityWidget.EntityAdapter<Binder_Card>
            binders_cards_cardAdapter;

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.idView = (EditText) view.findViewById(
                R.id.card_id);
        this.nameView = (EditText) view.findViewById(
                R.id.card_name);
        this.imageView = (EditText) view.findViewById(
                R.id.card_image);
        this.convertedManaCostView = (EditText) view.findViewById(
                R.id.card_convertedmanacost);
        this.typeCardView = (EditText) view.findViewById(
                R.id.card_typecard);
        this.rarityView = (EditText) view.findViewById(
                R.id.card_rarity);
        this.cardSetIdView = (EditText) view.findViewById(
                R.id.card_cardsetid);
        this.binders_cards_cardAdapter =
                new MultiEntityWidget.EntityAdapter<Binder_Card>() {
            @Override
            public String entityToString(Binder_Card item) {
                return String.valueOf(item.getId());
            }
        };
        this.binders_cards_cardWidget = (MultiEntityWidget) view.findViewById(
                        R.id.card_binders_cards_card_button);
        this.binders_cards_cardWidget.setAdapter(this.binders_cards_cardAdapter);
        this.binders_cards_cardWidget.setTitle(R.string.card_binders_cards_card_dialog_title);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {

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

        new LoadTask(this).execute();
    }

    /** Save data from curr.fields view to model. */
    public void saveData() {

        this.model.setId(Integer.parseInt(
                    this.idView.getEditableText().toString()));

        this.model.setName(this.nameView.getEditableText().toString());

        this.model.setImage(this.imageView.getEditableText().toString());

        this.model.setConvertedManaCost(Integer.parseInt(
                    this.convertedManaCostView.getEditableText().toString()));

        this.model.setTypeCard(this.typeCardView.getEditableText().toString());

        this.model.setRarity(this.rarityView.getEditableText().toString());

        this.model.setCardSetId(this.cardSetIdView.getEditableText().toString());

        this.model.setBinders_cards_card(this.binders_cards_cardAdapter.getCheckedItems());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.idView.getText().toString().trim())) {
            error = R.string.card_id_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.nameView.getText().toString().trim())) {
            error = R.string.card_name_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.imageView.getText().toString().trim())) {
            error = R.string.card_image_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.convertedManaCostView.getText().toString().trim())) {
            error = R.string.card_convertedmanacost_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.typeCardView.getText().toString().trim())) {
            error = R.string.card_typecard_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.rarityView.getText().toString().trim())) {
            error = R.string.card_rarity_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.cardSetIdView.getText().toString().trim())) {
            error = R.string.card_cardsetid_invalid_field_error;
        }
        if (this.binders_cards_cardAdapter.getCheckedItems().isEmpty()) {
            error = R.string.card_binders_cards_card_invalid_field_error;
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
        final View view =
                inflater.inflate(R.layout.fragment_card_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (Card) intent.getParcelableExtra(
                CardContract.PARCEL);

        this.initializeComponent(view);
        this.loadData();

        return view;
    }

    /**
     * This class will update the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class EditTask extends AsyncTask<Void, Void, Integer> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Entity to update. */
        private final Card entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final CardEditFragment fragment,
                    final Card entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.card_progress_save_title),
                    this.ctx.getString(
                            R.string.card_progress_save_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new CardProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("CardEditFragment", e.getMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            if (result > 0) {
                final HarmonyFragmentActivity activity =
                        (HarmonyFragmentActivity) this.ctx;
                activity.setResult(HarmonyFragmentActivity.RESULT_OK);
                activity.finish();
            } else {
                final AlertDialog.Builder builder =
                        new AlertDialog.Builder(this.ctx);
                builder.setIcon(0);
                builder.setMessage(this.ctx.getString(
                        R.string.card_error_edit));
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
        private CardEditFragment fragment;
        /** binders_cards_card list. */
        private ArrayList<Binder_Card> binders_cards_cardList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final CardEditFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                this.ctx.getString(
                    R.string.card_progress_load_relations_title),
                this.ctx.getString(
                    R.string.card_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.binders_cards_cardList = 
                new Binder_CardProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.onBinders_cards_cardLoaded(this.binders_cards_cardList);

            this.progress.dismiss();
        }
    }

    @Override
    public void onClickSave() {
        if (this.validateData()) {
            this.saveData();
            new EditTask(this, this.model).execute();
        }
    }

    /**
     * Called when binders_cards_card have been loaded.
     * @param items The loaded items
     */
    protected void onBinders_cards_cardLoaded(ArrayList<Binder_Card> items) {
        this.binders_cards_cardAdapter.loadData(items);
        ArrayList<Binder_Card> modelItems = new ArrayList<Binder_Card>();
        for (Binder_Card item : items) {
            if (item.getCard() != null && item.getCard().getId() == this.model.getId()) {
                modelItems.add(item);
                this.binders_cards_cardAdapter.checkItem(item, true);
            }
        }
        this.model.setBinders_cards_card(modelItems);
    }
}
