/**************************************************************************
 * BinderProviderUtilsBase.java, MagicBinder Android
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

import com.magicbinder.entity.Binder;
import com.magicbinder.entity.Binder_Card;

import com.magicbinder.provider.BinderProviderAdapter;
import com.magicbinder.provider.Binder_CardProviderAdapter;
import com.magicbinder.provider.MagicBinderProvider;
import com.magicbinder.provider.contract.BinderContract;
import com.magicbinder.provider.contract.Binder_CardContract;

/**
 * Binder Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class BinderProviderUtilsBase
            extends ProviderUtils<Binder> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "BinderProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public BinderProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Binder item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = BinderContract.itemToContentValues(item);
        itemValues.remove(BinderContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                BinderProviderAdapter.BINDER_URI)
                        .withValues(itemValues)
                        .build());

        if (item.getBinders_cards_binder() != null && item.getBinders_cards_binder().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(Binder_CardContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getBinders_cards_binder().size(); i++) {
                inValue.addValue(String.valueOf(item.getBinders_cards_binder().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(Binder_CardProviderAdapter.BINDER_CARD_URI)
                    .withValueBackReference(
                            Binder_CardContract
                                    .COL_BINDER_ID,
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
     * @param item Binder
     * @return number of row affected
     */
    public int delete(final Binder item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = BinderProviderAdapter.BINDER_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Binder
     */
    public Binder query(final Binder item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Binder
     */
    public Binder query(final int id) {
        Binder result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(BinderContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            BinderProviderAdapter.BINDER_URI,
            BinderContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = BinderContract.cursorToItem(cursor);
            cursor.close();

            result.setBinders_cards_binder(
                this.getAssociateBinders_cards_binder(result));
        }

        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Binder>
     */
    public ArrayList<Binder> queryAll() {
        ArrayList<Binder> result =
                    new ArrayList<Binder>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                BinderProviderAdapter.BINDER_URI,
                BinderContract.ALIASED_COLS,
                null,
                null,
                null);

        result = BinderContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Binder>
     */
    public ArrayList<Binder> query(CriteriaExpression expression) {
        ArrayList<Binder> result =
                    new ArrayList<Binder>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                BinderProviderAdapter.BINDER_URI,
                BinderContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = BinderContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Binder
     
     * @return number of rows updated
     */
    public int update(final Binder item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = BinderContract.itemToContentValues(
                item);

        Uri uri = BinderProviderAdapter.BINDER_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        if (item.getBinders_cards_binder() != null && item.getBinders_cards_binder().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new binders_cards_binder for Binder
            CriteriaExpression binders_cards_binderCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(Binder_CardContract.COL_ID);
            crit.addValue(values);
            binders_cards_binderCrit.add(crit);


            for (Binder_Card binders_cards_binder : item.getBinders_cards_binder()) {
                values.addValue(
                    String.valueOf(binders_cards_binder.getId()));
            }
            selection = binders_cards_binderCrit.toSQLiteSelection();
            selectionArgs = binders_cards_binderCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    Binder_CardProviderAdapter.BINDER_CARD_URI)
                    .withValue(
                            Binder_CardContract.COL_BINDER_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated binders_cards_binder
            crit.setType(Type.NOT_IN);
            binders_cards_binderCrit.add(Binder_CardContract.COL_BINDER_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    Binder_CardProviderAdapter.BINDER_CARD_URI)
                    .withValue(
                            Binder_CardContract.COL_BINDER_ID,
                            null)
                    .withSelection(
                            binders_cards_binderCrit.toSQLiteSelection(),
                            binders_cards_binderCrit.toSQLiteSelectionArgs())
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
     * Get associate Binders_cards_binder.
     * @param item Binder
     * @return Binder_Card
     */
    public ArrayList<Binder_Card> getAssociateBinders_cards_binder(
            final Binder item) {
        ArrayList<Binder_Card> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor binder_CardCursor = prov.query(
                Binder_CardProviderAdapter.BINDER_CARD_URI,
                Binder_CardContract.ALIASED_COLS,
                Binder_CardContract.ALIASED_COL_BINDER_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = Binder_CardContract.cursorToItems(
                        binder_CardCursor);
        binder_CardCursor.close();

        return result;
    }

}
