/**************************************************************************
 * CardContractBase.java, MagicBinder Android
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

import com.magicbinder.entity.Card;
import com.magicbinder.entity.Color;
import com.magicbinder.entity.Binder_Card;



import com.magicbinder.provider.contract.CardContract;

/** MagicBinder contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class CardContractBase {


    /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            CardContract.TABLE_NAME + "." + COL_ID;

    /** name. */
    public static final String COL_NAME =
            "name";
    /** Alias. */
    public static final String ALIASED_COL_NAME =
            CardContract.TABLE_NAME + "." + COL_NAME;

    /** image. */
    public static final String COL_IMAGE =
            "image";
    /** Alias. */
    public static final String ALIASED_COL_IMAGE =
            CardContract.TABLE_NAME + "." + COL_IMAGE;

    /** convertedManaCost. */
    public static final String COL_CONVERTEDMANACOST =
            "convertedManaCost";
    /** Alias. */
    public static final String ALIASED_COL_CONVERTEDMANACOST =
            CardContract.TABLE_NAME + "." + COL_CONVERTEDMANACOST;

    /** typeCard. */
    public static final String COL_TYPECARD =
            "typeCard";
    /** Alias. */
    public static final String ALIASED_COL_TYPECARD =
            CardContract.TABLE_NAME + "." + COL_TYPECARD;

    /** rarity. */
    public static final String COL_RARITY =
            "rarity";
    /** Alias. */
    public static final String ALIASED_COL_RARITY =
            CardContract.TABLE_NAME + "." + COL_RARITY;

    /** cardSetId. */
    public static final String COL_CARDSETID =
            "cardSetId";
    /** Alias. */
    public static final String ALIASED_COL_CARDSETID =
            CardContract.TABLE_NAME + "." + COL_CARDSETID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Card";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Card";
    /** Global Fields. */
    public static final String[] COLS = new String[] {
        CardContract.COL_ID,
        CardContract.COL_NAME,
        CardContract.COL_IMAGE,
        CardContract.COL_CONVERTEDMANACOST,
        CardContract.COL_TYPECARD,
        CardContract.COL_RARITY,
        CardContract.COL_CARDSETID,
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        CardContract.ALIASED_COL_ID,
        CardContract.ALIASED_COL_NAME,
        CardContract.ALIASED_COL_IMAGE,
        CardContract.ALIASED_COL_CONVERTEDMANACOST,
        CardContract.ALIASED_COL_TYPECARD,
        CardContract.ALIASED_COL_RARITY,
        CardContract.ALIASED_COL_CARDSETID,
    };


    /**
     * Converts a Card into a content values.
     *
     * @param item The Card to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Card item) {
        final ContentValues result = new ContentValues();

        result.put(CardContract.COL_ID,
                String.valueOf(item.getId()));

        if (item.getName() != null) {
            result.put(CardContract.COL_NAME,
                    item.getName());
        }

        if (item.getImage() != null) {
            result.put(CardContract.COL_IMAGE,
                    item.getImage());
        }

        result.put(CardContract.COL_CONVERTEDMANACOST,
                String.valueOf(item.getConvertedManaCost()));

        if (item.getTypeCard() != null) {
            result.put(CardContract.COL_TYPECARD,
                    item.getTypeCard());
        }

        if (item.getRarity() != null) {
            result.put(CardContract.COL_RARITY,
                    item.getRarity());
        }

        if (item.getCardSetId() != null) {
            result.put(CardContract.COL_CARDSETID,
                    item.getCardSetId());
        }


        return result;
    }

    /**
     * Converts a Cursor into a Card.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Card 
     */
    public static Card cursorToItem(final android.database.Cursor cursor) {
        Card result = new Card();
        CardContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Card entity.
     * @param cursor Cursor object
     * @param result Card entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Card result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndexOrThrow(CardContract.COL_ID);
            result.setId(
                    cursor.getInt(index));

            index = cursor.getColumnIndexOrThrow(CardContract.COL_NAME);
            result.setName(
                    cursor.getString(index));

            index = cursor.getColumnIndexOrThrow(CardContract.COL_IMAGE);
            result.setImage(
                    cursor.getString(index));

            index = cursor.getColumnIndexOrThrow(CardContract.COL_CONVERTEDMANACOST);
            result.setConvertedManaCost(
                    cursor.getInt(index));

            index = cursor.getColumnIndexOrThrow(CardContract.COL_TYPECARD);
            result.setTypeCard(
                    cursor.getString(index));

            index = cursor.getColumnIndexOrThrow(CardContract.COL_RARITY);
            result.setRarity(
                    cursor.getString(index));

            index = cursor.getColumnIndexOrThrow(CardContract.COL_CARDSETID);
            result.setCardSetId(
                    cursor.getString(index));


        }
    }

    /**
     * Convert Cursor of database to Array of Card entity.
     * @param cursor Cursor object
     * @return Array of Card entity
     */
    public static ArrayList<Card> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Card> result = new ArrayList<Card>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Card item;
            do {
                item = CardContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
