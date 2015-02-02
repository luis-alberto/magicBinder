/**************************************************************************
 * QualityContractBase.java, MagicBinder Android
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

import com.magicbinder.entity.Quality;
import com.magicbinder.entity.Binder_Card;



import com.magicbinder.provider.contract.QualityContract;

/** MagicBinder contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class QualityContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            QualityContract.TABLE_NAME + "." + COL_ID;

    /** label. */
    public static final String COL_LABEL =
            "label";
    /** Alias. */
    public static final String ALIASED_COL_LABEL =
            QualityContract.TABLE_NAME + "." + COL_LABEL;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Quality";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Quality";
    /** Global Fields. */
    public static final String[] COLS = new String[] {
            QualityContract.COL_ID,
            QualityContract.COL_LABEL,
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
            QualityContract.ALIASED_COL_ID,
            QualityContract.ALIASED_COL_LABEL,
    };


    /**
     * Converts a Quality into a content values.
     *
     * @param item The Quality to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Quality item) {
        final ContentValues result = new ContentValues();

            result.put(QualityContract.COL_ID,
                String.valueOf(item.getId()));

            if (item.getLabel() != null) {
                result.put(QualityContract.COL_LABEL,
                    item.getLabel());
            }


        return result;
    }

    /**
     * Converts a Cursor into a Quality.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Quality 
     */
    public static Quality cursorToItem(final android.database.Cursor cursor) {
        Quality result = new Quality();
        QualityContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Quality entity.
     * @param cursor Cursor object
     * @param result Quality entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Quality result) {
        if (cursor.getCount() != 0) {
            int index;

                index = cursor.getColumnIndexOrThrow(QualityContract.COL_ID);
                result.setId(
                        cursor.getInt(index));

                index = cursor.getColumnIndexOrThrow(QualityContract.COL_LABEL);
                result.setLabel(
                        cursor.getString(index));


        }
    }

    /**
     * Convert Cursor of database to Array of Quality entity.
     * @param cursor Cursor object
     * @return Array of Quality entity
     */
    public static ArrayList<Quality> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Quality> result = new ArrayList<Quality>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Quality item;
            do {
                item = QualityContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
