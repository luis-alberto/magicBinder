/**************************************************************************
 * ColorContractBase.java, MagicBinder Android
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

import com.magicbinder.entity.Color;
import com.magicbinder.entity.Card;



import com.magicbinder.provider.contract.ColorContract;

/** MagicBinder contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class ColorContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            ColorContract.TABLE_NAME + "." + COL_ID;

    /** label. */
    public static final String COL_LABEL =
            "label";
    /** Alias. */
    public static final String ALIASED_COL_LABEL =
            ColorContract.TABLE_NAME + "." + COL_LABEL;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Color";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Color";
    /** Global Fields. */
    public static final String[] COLS = new String[] {
            ColorContract.COL_ID,
            ColorContract.COL_LABEL,
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
            ColorContract.ALIASED_COL_ID,
            ColorContract.ALIASED_COL_LABEL,
    };


    /**
     * Converts a Color into a content values.
     *
     * @param item The Color to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Color item) {
        final ContentValues result = new ContentValues();

            result.put(ColorContract.COL_ID,
                String.valueOf(item.getId()));

            if (item.getLabel() != null) {
                result.put(ColorContract.COL_LABEL,
                    item.getLabel());
            }


        return result;
    }

    /**
     * Converts a Cursor into a Color.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Color 
     */
    public static Color cursorToItem(final android.database.Cursor cursor) {
        Color result = new Color();
        ColorContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Color entity.
     * @param cursor Cursor object
     * @param result Color entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Color result) {
        if (cursor.getCount() != 0) {
            int index;

                index = cursor.getColumnIndexOrThrow(ColorContract.COL_ID);
                result.setId(
                        cursor.getInt(index));

                index = cursor.getColumnIndexOrThrow(ColorContract.COL_LABEL);
                result.setLabel(
                        cursor.getString(index));


        }
    }

    /**
     * Convert Cursor of database to Array of Color entity.
     * @param cursor Cursor object
     * @return Array of Color entity
     */
    public static ArrayList<Color> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Color> result = new ArrayList<Color>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Color item;
            do {
                item = ColorContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
