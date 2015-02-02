/**************************************************************************
 * CardSQLiteAdapterBase.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.data.base;

import java.util.ArrayList;
import android.content.ContentValues;


import android.database.sqlite.SQLiteDatabase;


import com.magicbinder.data.SQLiteAdapter;
import com.magicbinder.data.CardSQLiteAdapter;
import com.magicbinder.data.ColortoCardSQLiteAdapter;
import com.magicbinder.data.ColorSQLiteAdapter;
import com.magicbinder.data.Binder_CardSQLiteAdapter;
import com.magicbinder.provider.contract.CardContract;
import com.magicbinder.provider.contract.ColortoCardContract;
import com.magicbinder.provider.contract.ColorContract;
import com.magicbinder.provider.contract.Binder_CardContract;
import com.magicbinder.entity.Card;
import com.magicbinder.entity.Color;
import com.magicbinder.entity.Binder_Card;


import com.magicbinder.MagicBinderApplication;


/** Card adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit CardAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class CardSQLiteAdapterBase
                        extends SQLiteAdapter<Card> {

    /** TAG for debug purpose. */
    protected static final String TAG = "CardDBAdapter";


    /**
     * Get the table name used in DB for your Card entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return CardContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Card entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = CardContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Card entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return CardContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + CardContract.TABLE_NAME    + " ("
        
         + CardContract.COL_ID    + " INTEGER PRIMARY KEY,"
         + CardContract.COL_NAME    + " VARCHAR NOT NULL,"
         + CardContract.COL_IMAGE    + " VARCHAR NOT NULL,"
         + CardContract.COL_CONVERTEDMANACOST    + " INTEGER NOT NULL,"
         + CardContract.COL_TYPECARD    + " VARCHAR NOT NULL,"
         + CardContract.COL_RARITY    + " VARCHAR NOT NULL,"
         + CardContract.COL_CARDSETID    + " VARCHAR NOT NULL"

        
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public CardSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Card entity to Content Values for database.
     * @param item Card entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Card item) {
        return CardContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Card entity.
     * @param cursor android.database.Cursor object
     * @return Card entity
     */
    public Card cursorToItem(final android.database.Cursor cursor) {
        return CardContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Card entity.
     * @param cursor android.database.Cursor object
     * @param result Card entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Card result) {
        CardContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Card by id in database.
     *
     * @param id Identify of Card
     * @return Card entity
     */
    public Card getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Card result = this.cursorToItem(cursor);
        cursor.close();

        ColortoCardSQLiteAdapter colortocardAdapter =
                new ColortoCardSQLiteAdapter(this.ctx);
        colortocardAdapter.open(this.mDatabase);
        android.database.Cursor colorsCursor = colortocardAdapter.getByCards(
                            result.getId(),
                            ColorContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setColors(new ColorSQLiteAdapter(ctx).cursorToItems(colorsCursor));
        final Binder_CardSQLiteAdapter binders_cards_cardAdapter =
                new Binder_CardSQLiteAdapter(this.ctx);
        binders_cards_cardAdapter.open(this.mDatabase);
        android.database.Cursor binders_cards_cardCursor = binders_cards_cardAdapter
                    .getByCard(
                            result.getId(),
                            Binder_CardContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setBinders_cards_card(binders_cards_cardAdapter.cursorToItems(binders_cards_cardCursor));
        return result;
    }


    /**
     * Read All Cards entities.
     *
     * @return List of Card entities
     */
    public ArrayList<Card> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Card> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Card entity into database.
     *
     * @param item The Card entity to persist
     * @return Id of the Card entity
     */
    public long insert(final Card item) {
        if (MagicBinderApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + CardContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                CardContract.itemToContentValues(item);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    CardContract.COL_ID,
                    values);
        }
        if (item.getColors() != null) {
            ColortoCardSQLiteAdapterBase colorsAdapter =
                    new ColortoCardSQLiteAdapter(this.ctx);
            colorsAdapter.open(this.mDatabase);
            for (Color i : item.getColors()) {
                colorsAdapter.insert(item.getId(),
                        i.getId());
            }
        }
        if (item.getBinders_cards_card() != null) {
            Binder_CardSQLiteAdapterBase binders_cards_cardAdapter =
                    new Binder_CardSQLiteAdapter(this.ctx);
            binders_cards_cardAdapter.open(this.mDatabase);
            for (Binder_Card binder_card
                        : item.getBinders_cards_card()) {
                binder_card.setCard(item);
                binders_cards_cardAdapter.insertOrUpdate(binder_card);
            }
        }
        return insertResult;
    }

    /**
     * Either insert or update a Card entity into database whether.
     * it already exists or not.
     *
     * @param item The Card entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Card item) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.update(item);
        } else {
            // Item doesn't exist => create it
            final long id = this.insert(item);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }

    /**
     * Update a Card entity into database.
     *
     * @param item The Card entity to persist
     * @return count of updated entities
     */
    public int update(final Card item) {
        if (MagicBinderApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + CardContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                CardContract.itemToContentValues(item);
        final String whereClause =
                 CardContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Card entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (MagicBinderApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB(" 
                    + CardContract.TABLE_NAME 
                    + ")"
                    + " id : "+ id);
        }
        
        final String whereClause =
                CardContract.COL_ID 
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param card The entity to delete
     * @return count of updated entities
     */
    public int delete(final Card card) {
        return this.remove(card.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Card corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (MagicBinderApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                CardContract.ALIASED_COL_ID 
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                CardContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Card entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = CardContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                CardContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }


}

