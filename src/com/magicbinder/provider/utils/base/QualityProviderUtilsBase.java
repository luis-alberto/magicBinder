/**************************************************************************
 * QualityProviderUtilsBase.java, MagicBinder Android
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

import com.magicbinder.entity.Quality;
import com.magicbinder.entity.Binder_Card;

import com.magicbinder.provider.QualityProviderAdapter;
import com.magicbinder.provider.Binder_CardProviderAdapter;
import com.magicbinder.provider.MagicBinderProvider;
import com.magicbinder.provider.contract.QualityContract;
import com.magicbinder.provider.contract.Binder_CardContract;

/**
 * Quality Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class QualityProviderUtilsBase
            extends ProviderUtils<Quality> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "QualityProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public QualityProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Quality item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = QualityContract.itemToContentValues(item);
        itemValues.remove(QualityContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                QualityProviderAdapter.QUALITY_URI)
                        .withValues(itemValues)
                        .build());

        if (item.getBinders_cards_quality() != null && item.getBinders_cards_quality().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(Binder_CardContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getBinders_cards_quality().size(); i++) {
                inValue.addValue(String.valueOf(item.getBinders_cards_quality().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(Binder_CardProviderAdapter.BINDER_CARD_URI)
                    .withValueBackReference(
                            Binder_CardContract
                                    .COL_QUALITY_ID,
                            0)
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
     * @param item Quality
     * @return number of row affected
     */
    public int delete(final Quality item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = QualityProviderAdapter.QUALITY_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Quality
     */
    public Quality query(final Quality item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Quality
     */
    public Quality query(final int id) {
        Quality result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(QualityContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            QualityProviderAdapter.QUALITY_URI,
            QualityContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = QualityContract.cursorToItem(cursor);
            cursor.close();

            result.setBinders_cards_quality(
                this.getAssociateBinders_cards_quality(result));
        }

        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Quality>
     */
    public ArrayList<Quality> queryAll() {
        ArrayList<Quality> result =
                    new ArrayList<Quality>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                QualityProviderAdapter.QUALITY_URI,
                QualityContract.ALIASED_COLS,
                null,
                null,
                null);

        result = QualityContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Quality>
     */
    public ArrayList<Quality> query(CriteriaExpression expression) {
        ArrayList<Quality> result =
                    new ArrayList<Quality>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                QualityProviderAdapter.QUALITY_URI,
                QualityContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = QualityContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Quality
     
     * @return number of rows updated
     */
    public int update(final Quality item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = QualityContract.itemToContentValues(
                item);

        Uri uri = QualityProviderAdapter.QUALITY_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        if (item.getBinders_cards_quality() != null && item.getBinders_cards_quality().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new binders_cards_quality for Quality
            CriteriaExpression binders_cards_qualityCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(Binder_CardContract.COL_ID);
            crit.addValue(values);
            binders_cards_qualityCrit.add(crit);


            for (Binder_Card binders_cards_quality : item.getBinders_cards_quality()) {
                values.addValue(
                    String.valueOf(binders_cards_quality.getId()));
            }
            selection = binders_cards_qualityCrit.toSQLiteSelection();
            selectionArgs = binders_cards_qualityCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    Binder_CardProviderAdapter.BINDER_CARD_URI)
                    .withValue(
                            Binder_CardContract.COL_QUALITY_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated binders_cards_quality
            crit.setType(Type.NOT_IN);
            binders_cards_qualityCrit.add(Binder_CardContract.COL_QUALITY_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    Binder_CardProviderAdapter.BINDER_CARD_URI)
                    .withValue(
                            Binder_CardContract.COL_QUALITY_ID,
                            null)
                    .withSelection(
                            binders_cards_qualityCrit.toSQLiteSelection(),
                            binders_cards_qualityCrit.toSQLiteSelectionArgs())
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
     * Get associate Binders_cards_quality.
     * @param item Quality
     * @return Binder_Card
     */
    public ArrayList<Binder_Card> getAssociateBinders_cards_quality(
            final Quality item) {
        ArrayList<Binder_Card> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor binder_CardCursor = prov.query(
                Binder_CardProviderAdapter.BINDER_CARD_URI,
                Binder_CardContract.ALIASED_COLS,
                Binder_CardContract.ALIASED_COL_QUALITY_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = Binder_CardContract.cursorToItems(
                        binder_CardCursor);
        binder_CardCursor.close();

        return result;
    }

}
