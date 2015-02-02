/**************************************************************************
 * CardProviderUtilsBase.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.provider.utils.base;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;

import android.content.ContentResolver;
import android.content.ContentValues;

import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;


import com.magicbinder.provider.utils.ProviderUtils;
import com.magicbinder.criterias.base.Criterion;
import com.magicbinder.criterias.base.Criterion.Type;
import com.magicbinder.criterias.base.value.ArrayValue;
import com.magicbinder.criterias.base.CriteriaExpression;
import com.magicbinder.criterias.base.CriteriaExpression.GroupType;
import com.magicbinder.data.ColorSQLiteAdapter;
import com.magicbinder.entity.Card;
import com.magicbinder.entity.Color;
import com.magicbinder.entity.Binder_Card;

import com.magicbinder.provider.CardProviderAdapter;
import com.magicbinder.provider.ColortoCardProviderAdapter;
import com.magicbinder.provider.ColorProviderAdapter;
import com.magicbinder.provider.Binder_CardProviderAdapter;
import com.magicbinder.provider.MagicBinderProvider;
import com.magicbinder.provider.contract.CardContract;
import com.magicbinder.provider.contract.ColortoCardContract;
import com.magicbinder.provider.contract.ColorContract;
import com.magicbinder.provider.contract.Binder_CardContract;

/**
 * Card Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class CardProviderUtilsBase
            extends ProviderUtils<Card> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "CardProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public CardProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Card item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = CardContract.itemToContentValues(item);

        operations.add(ContentProviderOperation.newInsert(
                CardProviderAdapter.CARD_URI)
                        .withValues(itemValues)
                        .build());

        if (item.getColors() != null && item.getColors().size() > 0) {
            for (Color color : item.getColors()) {
                ContentValues colorValues = new ContentValues();
                colorValues.put(
                        ColortoCardContract.COL_COLORS_ID,
                        color.getId());

                operations.add(ContentProviderOperation.newInsert(
                    ColortoCardProviderAdapter.COLORTOCARD_URI)
                        .withValues(colorValues)
                        .withValue(
                                ColortoCardContract.COL_CARDS_ID,
                                item.getId())    
                        .build());

            }
        }
        if (item.getBinders_cards_card() != null && item.getBinders_cards_card().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(Binder_CardContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getBinders_cards_card().size(); i++) {
                inValue.addValue(String.valueOf(item.getBinders_cards_card().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(Binder_CardProviderAdapter.BINDER_CARD_URI)
                    .withValue(
                            Binder_CardContract
                                    .COL_CARD_ID,
                            item.getId())
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }

        try {
            ContentProviderResult[] results =
                    prov.applyBatch(MagicBinderProvider.authority, operations);
            if (results[0] != null) {
                result = results[0].uri;
                item.setId(Integer.parseInt(result.getPathSegments().get(1)));
            }
        } catch (RemoteException e) {
            android.util.Log.e(TAG, e.getMessage());
        } catch (OperationApplicationException e) {
            android.util.Log.e(TAG, e.getMessage());
        }

        return result;
    }


    /**
     * Delete from DB.
     * @param item Card
     * @return number of row affected
     */
    public int delete(final Card item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = CardProviderAdapter.CARD_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Card
     */
    public Card query(final Card item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Card
     */
    public Card query(final int id) {
        Card result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(CardContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            CardProviderAdapter.CARD_URI,
            CardContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = CardContract.cursorToItem(cursor);
            cursor.close();

            result.setColors(
                this.getAssociateColors(result));
            result.setBinders_cards_card(
                this.getAssociateBinders_cards_card(result));
        }

        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Card>
     */
    public ArrayList<Card> queryAll() {
        ArrayList<Card> result =
                    new ArrayList<Card>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                CardProviderAdapter.CARD_URI,
                CardContract.ALIASED_COLS,
                null,
                null,
                null);

        result = CardContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Card>
     */
    public ArrayList<Card> query(CriteriaExpression expression) {
        ArrayList<Card> result =
                    new ArrayList<Card>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                CardProviderAdapter.CARD_URI,
                CardContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = CardContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Card
     
     * @return number of rows updated
     */
    public int update(final Card item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = CardContract.itemToContentValues(
                item);

        Uri uri = CardProviderAdapter.CARD_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        operations.add(ContentProviderOperation.newDelete(ColortoCardProviderAdapter.COLORTOCARD_URI)
                .withSelection(ColortoCardContract.COL_CARDS_ID + "= ?",
                                new String[]{String.valueOf(item.getId())})
                .build());

        for (Color color : item.getColors()) {
            ContentValues colorValues = new ContentValues();
            colorValues.put(ColortoCardContract.COL_COLORS_ID,
                    color.getId());
            colorValues.put(ColortoCardContract.COL_CARDS_ID,
                    item.getId());

            operations.add(ContentProviderOperation.newInsert(ColortoCardProviderAdapter.COLORTOCARD_URI)
                    .withValues(colorValues)
                    .build());
        }
        if (item.getBinders_cards_card() != null && item.getBinders_cards_card().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new binders_cards_card for Card
            CriteriaExpression binders_cards_cardCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(Binder_CardContract.COL_ID);
            crit.addValue(values);
            binders_cards_cardCrit.add(crit);


            for (Binder_Card binders_cards_card : item.getBinders_cards_card()) {
                values.addValue(
                    String.valueOf(binders_cards_card.getId()));
            }
            selection = binders_cards_cardCrit.toSQLiteSelection();
            selectionArgs = binders_cards_cardCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    Binder_CardProviderAdapter.BINDER_CARD_URI)
                    .withValue(
                            Binder_CardContract.COL_CARD_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated binders_cards_card
            crit.setType(Type.NOT_IN);
            binders_cards_cardCrit.add(Binder_CardContract.COL_CARD_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    Binder_CardProviderAdapter.BINDER_CARD_URI)
                    .withValue(
                            Binder_CardContract.COL_CARD_ID,
                            null)
                    .withSelection(
                            binders_cards_cardCrit.toSQLiteSelection(),
                            binders_cards_cardCrit.toSQLiteSelectionArgs())
                    .build());
        }


        try {
            ContentProviderResult[] results = prov.applyBatch(MagicBinderProvider.authority, operations);
            result = results[0].count;
        } catch (RemoteException e) {
            android.util.Log.e(TAG, e.getMessage());
        } catch (OperationApplicationException e) {
            android.util.Log.e(TAG, e.getMessage());
        }

        return result;
    }

    /** Relations operations. */
    /**
     * Get associate Colors.
     * @param item Card
     * @return Color
     */
    public ArrayList<Color> getAssociateColors(
            final Card item) {
        ArrayList<Color> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor colortoCardCursor = prov.query(
                ColortoCardProviderAdapter.COLORTOCARD_URI,
                ColortoCardContract.ALIASED_COLS,
                ColortoCardContract.ALIASED_COL_CARDS_ID 
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        if (colortoCardCursor.getCount() > 0) {
            CriteriaExpression colorCrits =
                    new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            ArrayValue arrayValue = new ArrayValue();
            inCrit.setKey(ColorContract.ALIASED_COL_ID);
            inCrit.setType(Type.IN);
            inCrit.addValue(arrayValue);
            colorCrits.add(inCrit);

            while (colortoCardCursor.moveToNext()) {
                int index = colortoCardCursor.getColumnIndex(
                        ColortoCardContract.COL_COLORS_ID);
                String colorId = colortoCardCursor.getString(index);

                arrayValue.addValue(colorId);
            }
            colortoCardCursor.close();
            android.database.Cursor colorCursor = prov.query(
                    ColorProviderAdapter.COLOR_URI,
                    ColorContract.ALIASED_COLS,
                    colorCrits.toSQLiteSelection(),
                    colorCrits.toSQLiteSelectionArgs(),
                    null);

            result = ColorContract.cursorToItems(colorCursor);
            colorCursor.close();
        } else {
            result = new ArrayList<Color>();
        }

        return result;
    }

    /**
     * Get associate Binders_cards_card.
     * @param item Card
     * @return Binder_Card
     */
    public ArrayList<Binder_Card> getAssociateBinders_cards_card(
            final Card item) {
        ArrayList<Binder_Card> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor binder_CardCursor = prov.query(
                Binder_CardProviderAdapter.BINDER_CARD_URI,
                Binder_CardContract.ALIASED_COLS,
                Binder_CardContract.ALIASED_COL_CARD_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = Binder_CardContract.cursorToItems(
                        binder_CardCursor);
        binder_CardCursor.close();

        return result;
    }

}
