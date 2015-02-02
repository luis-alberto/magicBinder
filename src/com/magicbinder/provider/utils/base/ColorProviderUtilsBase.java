/**************************************************************************
 * ColorProviderUtilsBase.java, MagicBinder Android
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
import com.magicbinder.data.CardSQLiteAdapter;
import com.magicbinder.entity.Color;
import com.magicbinder.entity.Card;

import com.magicbinder.provider.ColorProviderAdapter;
import com.magicbinder.provider.ColortoCardProviderAdapter;
import com.magicbinder.provider.CardProviderAdapter;
import com.magicbinder.provider.MagicBinderProvider;
import com.magicbinder.provider.contract.ColorContract;
import com.magicbinder.provider.contract.ColortoCardContract;
import com.magicbinder.provider.contract.CardContract;

/**
 * Color Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class ColorProviderUtilsBase
            extends ProviderUtils<Color> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "ColorProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public ColorProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Color item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = ColorContract.itemToContentValues(item);
        itemValues.remove(ColorContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                ColorProviderAdapter.COLOR_URI)
                        .withValues(itemValues)
                        .build());

        if (item.getCards() != null && item.getCards().size() > 0) {
            for (Card card : item.getCards()) {
                ContentValues cardValues = new ContentValues();
                cardValues.put(
                        ColortoCardContract.COL_CARDS_ID,
                        card.getId());

                operations.add(ContentProviderOperation.newInsert(
                    ColortoCardProviderAdapter.COLORTOCARD_URI)
                        .withValues(cardValues)
                        .withValueBackReference(
                                ColortoCardContract.COL_COLORS_ID,
                                0)
                        .build());

            }
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
     * @param item Color
     * @return number of row affected
     */
    public int delete(final Color item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = ColorProviderAdapter.COLOR_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Color
     */
    public Color query(final Color item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Color
     */
    public Color query(final int id) {
        Color result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(ColorContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            ColorProviderAdapter.COLOR_URI,
            ColorContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = ColorContract.cursorToItem(cursor);
            cursor.close();

            result.setCards(
                this.getAssociateCards(result));
        }

        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Color>
     */
    public ArrayList<Color> queryAll() {
        ArrayList<Color> result =
                    new ArrayList<Color>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                ColorProviderAdapter.COLOR_URI,
                ColorContract.ALIASED_COLS,
                null,
                null,
                null);

        result = ColorContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Color>
     */
    public ArrayList<Color> query(CriteriaExpression expression) {
        ArrayList<Color> result =
                    new ArrayList<Color>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                ColorProviderAdapter.COLOR_URI,
                ColorContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = ColorContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Color
     
     * @return number of rows updated
     */
    public int update(final Color item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = ColorContract.itemToContentValues(
                item);

        Uri uri = ColorProviderAdapter.COLOR_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        operations.add(ContentProviderOperation.newDelete(ColortoCardProviderAdapter.COLORTOCARD_URI)
                .withSelection(ColortoCardContract.COL_COLORS_ID + "= ?",
                                new String[]{String.valueOf(item.getId())})
                .build());

        for (Card card : item.getCards()) {
            ContentValues cardValues = new ContentValues();
            cardValues.put(ColortoCardContract.COL_CARDS_ID,
                    card.getId());
            cardValues.put(ColortoCardContract.COL_COLORS_ID,
                    item.getId());

            operations.add(ContentProviderOperation.newInsert(ColortoCardProviderAdapter.COLORTOCARD_URI)
                    .withValues(cardValues)
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
     * Get associate Cards.
     * @param item Color
     * @return Card
     */
    public ArrayList<Card> getAssociateCards(
            final Color item) {
        ArrayList<Card> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor colortoCardCursor = prov.query(
                ColortoCardProviderAdapter.COLORTOCARD_URI,
                ColortoCardContract.ALIASED_COLS,
                ColortoCardContract.ALIASED_COL_COLORS_ID 
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        if (colortoCardCursor.getCount() > 0) {
            CriteriaExpression cardCrits =
                    new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            ArrayValue arrayValue = new ArrayValue();
            inCrit.setKey(CardContract.ALIASED_COL_ID);
            inCrit.setType(Type.IN);
            inCrit.addValue(arrayValue);
            cardCrits.add(inCrit);

            while (colortoCardCursor.moveToNext()) {
                int index = colortoCardCursor.getColumnIndex(
                        ColortoCardContract.COL_CARDS_ID);
                String cardId = colortoCardCursor.getString(index);

                arrayValue.addValue(cardId);
            }
            colortoCardCursor.close();
            android.database.Cursor cardCursor = prov.query(
                    CardProviderAdapter.CARD_URI,
                    CardContract.ALIASED_COLS,
                    cardCrits.toSQLiteSelection(),
                    cardCrits.toSQLiteSelectionArgs(),
                    null);

            result = CardContract.cursorToItems(cardCursor);
            cardCursor.close();
        } else {
            result = new ArrayList<Card>();
        }

        return result;
    }

}
