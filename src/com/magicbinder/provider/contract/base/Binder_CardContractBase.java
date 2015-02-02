/**************************************************************************
 * Binder_CardContractBase.java, MagicBinder Android
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

import com.magicbinder.entity.Binder_Card;
import com.magicbinder.entity.Card;
import com.magicbinder.entity.Binder;
import com.magicbinder.entity.Quality;



import com.magicbinder.provider.contract.Binder_CardContract;

/** MagicBinder contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class Binder_CardContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            Binder_CardContract.TABLE_NAME + "." + COL_ID;

    /** quantity. */
    public static final String COL_QUANTITY =
            "quantity";
    /** Alias. */
    public static final String ALIASED_COL_QUANTITY =
            Binder_CardContract.TABLE_NAME + "." + COL_QUANTITY;

    /** card_id. */
    public static final String COL_CARD_ID =
            "card_id";
    /** Alias. */
    public static final String ALIASED_COL_CARD_ID =
            Binder_CardContract.TABLE_NAME + "." + COL_CARD_ID;

    /** binder_id. */
    public static final String COL_BINDER_ID =
            "binder_id";
    /** Alias. */
    public static final String ALIASED_COL_BINDER_ID =
            Binder_CardContract.TABLE_NAME + "." + COL_BINDER_ID;

    /** quality_id. */
    public static final String COL_QUALITY_ID =
            "quality_id";
    /** Alias. */
    public static final String ALIASED_COL_QUALITY_ID =
            Binder_CardContract.TABLE_NAME + "." + COL_QUALITY_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Binder_Card";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Binder_Card";
    /** Global Fields. */
    public static final String[] COLS = new String[] {
            Binder_CardContract.COL_ID,
            Binder_CardContract.COL_QUANTITY,
            Binder_CardContract.COL_CARD_ID,
            Binder_CardContract.COL_BINDER_ID,
            Binder_CardContract.COL_QUALITY_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
            Binder_CardContract.ALIASED_COL_ID,
            Binder_CardContract.ALIASED_COL_QUANTITY,
            Binder_CardContract.ALIASED_COL_CARD_ID,
            Binder_CardContract.ALIASED_COL_BINDER_ID,
            Binder_CardContract.ALIASED_COL_QUALITY_ID
    };


    /**
     * Converts a Binder_Card into a content values.
     *
     * @param item The Binder_Card to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Binder_Card item) {
        final ContentValues result = new ContentValues();

            result.put(Binder_CardContract.COL_ID,
                String.valueOf(item.getId()));

            result.put(Binder_CardContract.COL_QUANTITY,
                String.valueOf(item.getQuantity()));

            if (item.getCard() != null) {
                result.put(Binder_CardContract.COL_CARD_ID,
                    item.getCard().getId());
            }

            if (item.getBinder() != null) {
                result.put(Binder_CardContract.COL_BINDER_ID,
                    item.getBinder().getId());
            }

            if (item.getQuality() != null) {
                result.put(Binder_CardContract.COL_QUALITY_ID,
                    item.getQuality().getId());
            }


        return result;
    }

    /**
     * Converts a Cursor into a Binder_Card.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Binder_Card 
     */
    public static Binder_Card cursorToItem(final android.database.Cursor cursor) {
        Binder_Card result = new Binder_Card();
        Binder_CardContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Binder_Card entity.
     * @param cursor Cursor object
     * @param result Binder_Card entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Binder_Card result) {
        if (cursor.getCount() != 0) {
            int index;

                index = cursor.getColumnIndexOrThrow(Binder_CardContract.COL_ID);
                result.setId(
                        cursor.getInt(index));

                index = cursor.getColumnIndexOrThrow(Binder_CardContract.COL_QUANTITY);
                result.setQuantity(
                        cursor.getInt(index));

                if (result.getCard() == null) {
                    final Card card = new Card();
                    index = cursor.getColumnIndexOrThrow(Binder_CardContract.COL_CARD_ID);
                    card.setId(cursor.getInt(index));
                    result.setCard(card);
                }

                if (result.getBinder() == null) {
                    final Binder binder = new Binder();
                    index = cursor.getColumnIndexOrThrow(Binder_CardContract.COL_BINDER_ID);
                    binder.setId(cursor.getInt(index));
                    result.setBinder(binder);
                }

                if (result.getQuality() == null) {
                    final Quality quality = new Quality();
                    index = cursor.getColumnIndexOrThrow(Binder_CardContract.COL_QUALITY_ID);
                    quality.setId(cursor.getInt(index));
                    result.setQuality(quality);
                }


        }
    }

    /**
     * Convert Cursor of database to Array of Binder_Card entity.
     * @param cursor Cursor object
     * @return Array of Binder_Card entity
     */
    public static ArrayList<Binder_Card> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Binder_Card> result = new ArrayList<Binder_Card>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Binder_Card item;
            do {
                item = Binder_CardContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
