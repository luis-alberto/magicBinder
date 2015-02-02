/**************************************************************************
 * Binder_CardProviderUtilsBase.java, MagicBinder Android
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
import com.magicbinder.criterias.base.CriteriaExpression;
import com.magicbinder.criterias.base.CriteriaExpression.GroupType;

import com.magicbinder.entity.Binder_Card;
import com.magicbinder.entity.Card;
import com.magicbinder.entity.Binder;
import com.magicbinder.entity.Quality;

import com.magicbinder.provider.Binder_CardProviderAdapter;
import com.magicbinder.provider.CardProviderAdapter;
import com.magicbinder.provider.BinderProviderAdapter;
import com.magicbinder.provider.QualityProviderAdapter;
import com.magicbinder.provider.MagicBinderProvider;
import com.magicbinder.provider.contract.Binder_CardContract;
import com.magicbinder.provider.contract.CardContract;
import com.magicbinder.provider.contract.BinderContract;
import com.magicbinder.provider.contract.QualityContract;

/**
 * Binder_Card Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class Binder_CardProviderUtilsBase
            extends ProviderUtils<Binder_Card> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "Binder_CardProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public Binder_CardProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Binder_Card item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = Binder_CardContract.itemToContentValues(item);
        itemValues.remove(Binder_CardContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                Binder_CardProviderAdapter.BINDER_CARD_URI)
                        .withValues(itemValues)
                        .build());


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
     * @param item Binder_Card
     * @return number of row affected
     */
    public int delete(final Binder_Card item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = Binder_CardProviderAdapter.BINDER_CARD_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Binder_Card
     */
    public Binder_Card query(final Binder_Card item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Binder_Card
     */
    public Binder_Card query(final int id) {
        Binder_Card result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(Binder_CardContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            Binder_CardProviderAdapter.BINDER_CARD_URI,
            Binder_CardContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = Binder_CardContract.cursorToItem(cursor);
            cursor.close();

            if (result.getCard() != null) {
                result.setCard(
                    this.getAssociateCard(result));
            }
            if (result.getBinder() != null) {
                result.setBinder(
                    this.getAssociateBinder(result));
            }
            if (result.getQuality() != null) {
                result.setQuality(
                    this.getAssociateQuality(result));
            }
        }

        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Binder_Card>
     */
    public ArrayList<Binder_Card> queryAll() {
        ArrayList<Binder_Card> result =
                    new ArrayList<Binder_Card>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                Binder_CardProviderAdapter.BINDER_CARD_URI,
                Binder_CardContract.ALIASED_COLS,
                null,
                null,
                null);

        result = Binder_CardContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Binder_Card>
     */
    public ArrayList<Binder_Card> query(CriteriaExpression expression) {
        ArrayList<Binder_Card> result =
                    new ArrayList<Binder_Card>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                Binder_CardProviderAdapter.BINDER_CARD_URI,
                Binder_CardContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = Binder_CardContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Binder_Card
     
     * @return number of rows updated
     */
    public int update(final Binder_Card item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = Binder_CardContract.itemToContentValues(
                item);

        Uri uri = Binder_CardProviderAdapter.BINDER_CARD_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());



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
     * Get associate Card.
     * @param item Binder_Card
     * @return Card
     */
    public Card getAssociateCard(
            final Binder_Card item) {
        Card result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor cardCursor = prov.query(
                CardProviderAdapter.CARD_URI,
                CardContract.ALIASED_COLS,
                CardContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getCard().getId())},
                null);

        if (cardCursor.getCount() > 0) {
            cardCursor.moveToFirst();
            result = CardContract.cursorToItem(cardCursor);
        } else {
            result = null;
        }
        cardCursor.close();

        return result;
    }

    /**
     * Get associate Binder.
     * @param item Binder_Card
     * @return Binder
     */
    public Binder getAssociateBinder(
            final Binder_Card item) {
        Binder result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor binderCursor = prov.query(
                BinderProviderAdapter.BINDER_URI,
                BinderContract.ALIASED_COLS,
                BinderContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getBinder().getId())},
                null);

        if (binderCursor.getCount() > 0) {
            binderCursor.moveToFirst();
            result = BinderContract.cursorToItem(binderCursor);
        } else {
            result = null;
        }
        binderCursor.close();

        return result;
    }

    /**
     * Get associate Quality.
     * @param item Binder_Card
     * @return Quality
     */
    public Quality getAssociateQuality(
            final Binder_Card item) {
        Quality result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor qualityCursor = prov.query(
                QualityProviderAdapter.QUALITY_URI,
                QualityContract.ALIASED_COLS,
                QualityContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getQuality().getId())},
                null);

        if (qualityCursor.getCount() > 0) {
            qualityCursor.moveToFirst();
            result = QualityContract.cursorToItem(qualityCursor);
        } else {
            result = null;
        }
        qualityCursor.close();

        return result;
    }

}
