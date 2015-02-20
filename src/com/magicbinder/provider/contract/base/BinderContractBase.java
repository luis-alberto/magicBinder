/**************************************************************************
 * BinderContractBase.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.provider.contract.base;

import android.content.ContentValues;


import java.util.ArrayList;

import com.magicbinder.entity.Binder;
import com.magicbinder.entity.Binder_Card;



import com.magicbinder.provider.contract.BinderContract;

/** MagicBinder contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class BinderContractBase {


    /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            BinderContract.TABLE_NAME + "." + COL_ID;

    /** name. */
    public static final String COL_NAME =
            "name";
    /** Alias. */
    public static final String ALIASED_COL_NAME =
            BinderContract.TABLE_NAME + "." + COL_NAME;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Binder";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Binder";
    /** Global Fields. */
    public static final String[] COLS = new String[] {
        BinderContract.COL_ID,
        BinderContract.COL_NAME,
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        BinderContract.ALIASED_COL_ID,
        BinderContract.ALIASED_COL_NAME,
    };


    /**
     * Converts a Binder into a content values.
     *
     * @param item The Binder to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Binder item) {
        final ContentValues result = new ContentValues();

        result.put(BinderContract.COL_ID,
                String.valueOf(item.getId()));

        if (item.getName() != null) {
            result.put(BinderContract.COL_NAME,
                    item.getName());
        }


        return result;
    }

    /**
     * Converts a Cursor into a Binder.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Binder 
     */
    public static Binder cursorToItem(final android.database.Cursor cursor) {
        Binder result = new Binder();
        BinderContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Binder entity.
     * @param cursor Cursor object
     * @param result Binder entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Binder result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndexOrThrow(BinderContract.COL_ID);
            result.setId(
                    cursor.getInt(index));

            index = cursor.getColumnIndexOrThrow(BinderContract.COL_NAME);
            result.setName(
                    cursor.getString(index));


        }
    }

    /**
     * Convert Cursor of database to Array of Binder entity.
     * @param cursor Cursor object
     * @return Array of Binder entity
     */
    public static ArrayList<Binder> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Binder> result = new ArrayList<Binder>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Binder item;
            do {
                item = BinderContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
