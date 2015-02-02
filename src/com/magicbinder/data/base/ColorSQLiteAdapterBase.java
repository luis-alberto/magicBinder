/**************************************************************************
 * ColorSQLiteAdapterBase.java, MagicBinder Android
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
import com.magicbinder.data.ColorSQLiteAdapter;
import com.magicbinder.data.ColortoCardSQLiteAdapter;
import com.magicbinder.data.CardSQLiteAdapter;
import com.magicbinder.provider.contract.ColorContract;
import com.magicbinder.provider.contract.ColortoCardContract;
import com.magicbinder.provider.contract.CardContract;
import com.magicbinder.entity.Color;
import com.magicbinder.entity.Card;


import com.magicbinder.MagicBinderApplication;


/** Color adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit ColorAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class ColorSQLiteAdapterBase
                        extends SQLiteAdapter<Color> {

    /** TAG for debug purpose. */
    protected static final String TAG = "ColorDBAdapter";


    /**
     * Get the table name used in DB for your Color entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return ColorContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Color entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = ColorContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Color entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return ColorContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + ColorContract.TABLE_NAME    + " ("
        
         + ColorContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + ColorContract.COL_LABEL    + " VARCHAR NOT NULL"

        
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public ColorSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Color entity to Content Values for database.
     * @param item Color entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Color item) {
        return ColorContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Color entity.
     * @param cursor android.database.Cursor object
     * @return Color entity
     */
    public Color cursorToItem(final android.database.Cursor cursor) {
        return ColorContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Color entity.
     * @param cursor android.database.Cursor object
     * @param result Color entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Color result) {
        ColorContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Color by id in database.
     *
     * @param id Identify of Color
     * @return Color entity
     */
    public Color getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Color result = this.cursorToItem(cursor);
        cursor.close();

        ColortoCardSQLiteAdapter colortocardAdapter =
                new ColortoCardSQLiteAdapter(this.ctx);
        colortocardAdapter.open(this.mDatabase);
        android.database.Cursor cardsCursor = colortocardAdapter.getByColors(
                            result.getId(),
                            CardContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setCards(new CardSQLiteAdapter(ctx).cursorToItems(cardsCursor));
        return result;
    }


    /**
     * Read All Colors entities.
     *
     * @return List of Color entities
     */
    public ArrayList<Color> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Color> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Color entity into database.
     *
     * @param item The Color entity to persist
     * @return Id of the Color entity
     */
    public long insert(final Color item) {
        if (MagicBinderApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + ColorContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                ColorContract.itemToContentValues(item);
        values.remove(ColorContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    ColorContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        if (item.getCards() != null) {
            ColortoCardSQLiteAdapterBase cardsAdapter =
                    new ColortoCardSQLiteAdapter(this.ctx);
            cardsAdapter.open(this.mDatabase);
            for (Card i : item.getCards()) {
                cardsAdapter.insert(
                        i.getId(),
                        insertResult);
            }
        }
        return insertResult;
    }

    /**
     * Either insert or update a Color entity into database whether.
     * it already exists or not.
     *
     * @param item The Color entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Color item) {
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
     * Update a Color entity into database.
     *
     * @param item The Color entity to persist
     * @return count of updated entities
     */
    public int update(final Color item) {
        if (MagicBinderApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + ColorContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                ColorContract.itemToContentValues(item);
        final String whereClause =
                 ColorContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Color entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (MagicBinderApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB(" 
                    + ColorContract.TABLE_NAME 
                    + ")"
                    + " id : "+ id);
        }
        
        final String whereClause =
                ColorContract.COL_ID 
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param color The entity to delete
     * @return count of updated entities
     */
    public int delete(final Color color) {
        return this.remove(color.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Color corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (MagicBinderApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                ColorContract.ALIASED_COL_ID 
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                ColorContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Color entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = ColorContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                ColorContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }


}

