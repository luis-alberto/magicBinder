/**************************************************************************
 * BinderSQLiteAdapterBase.java, MagicBinder Android
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
import com.magicbinder.data.BinderSQLiteAdapter;
import com.magicbinder.data.Binder_CardSQLiteAdapter;
import com.magicbinder.provider.contract.BinderContract;
import com.magicbinder.provider.contract.Binder_CardContract;
import com.magicbinder.entity.Binder;
import com.magicbinder.entity.Binder_Card;


import com.magicbinder.MagicBinderApplication;


/** Binder adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit BinderAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class BinderSQLiteAdapterBase
                        extends SQLiteAdapter<Binder> {

    /** TAG for debug purpose. */
    protected static final String TAG = "BinderDBAdapter";


    /**
     * Get the table name used in DB for your Binder entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return BinderContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Binder entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = BinderContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Binder entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return BinderContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + BinderContract.TABLE_NAME    + " ("
        
         + BinderContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + BinderContract.COL_NAME    + " VARCHAR NOT NULL"

        
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public BinderSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Binder entity to Content Values for database.
     * @param item Binder entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Binder item) {
        return BinderContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Binder entity.
     * @param cursor android.database.Cursor object
     * @return Binder entity
     */
    public Binder cursorToItem(final android.database.Cursor cursor) {
        return BinderContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Binder entity.
     * @param cursor android.database.Cursor object
     * @param result Binder entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Binder result) {
        BinderContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Binder by id in database.
     *
     * @param id Identify of Binder
     * @return Binder entity
     */
    public Binder getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Binder result = this.cursorToItem(cursor);
        cursor.close();

        final Binder_CardSQLiteAdapter binders_cards_binderAdapter =
                new Binder_CardSQLiteAdapter(this.ctx);
        binders_cards_binderAdapter.open(this.mDatabase);
        android.database.Cursor binders_cards_binderCursor = binders_cards_binderAdapter
                    .getByBinder(
                            result.getId(),
                            Binder_CardContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setBinders_cards_binder(binders_cards_binderAdapter.cursorToItems(binders_cards_binderCursor));
        return result;
    }


    /**
     * Read All Binders entities.
     *
     * @return List of Binder entities
     */
    public ArrayList<Binder> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Binder> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Binder entity into database.
     *
     * @param item The Binder entity to persist
     * @return Id of the Binder entity
     */
    public long insert(final Binder item) {
        if (MagicBinderApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + BinderContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                BinderContract.itemToContentValues(item);
        values.remove(BinderContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    BinderContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        if (item.getBinders_cards_binder() != null) {
            Binder_CardSQLiteAdapterBase binders_cards_binderAdapter =
                    new Binder_CardSQLiteAdapter(this.ctx);
            binders_cards_binderAdapter.open(this.mDatabase);
            for (Binder_Card binder_card
                        : item.getBinders_cards_binder()) {
                binder_card.setBinder(item);
                binders_cards_binderAdapter.insertOrUpdate(binder_card);
            }
        }
        return insertResult;
    }

    /**
     * Either insert or update a Binder entity into database whether.
     * it already exists or not.
     *
     * @param item The Binder entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Binder item) {
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
     * Update a Binder entity into database.
     *
     * @param item The Binder entity to persist
     * @return count of updated entities
     */
    public int update(final Binder item) {
        if (MagicBinderApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + BinderContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                BinderContract.itemToContentValues(item);
        final String whereClause =
                 BinderContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Binder entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (MagicBinderApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB(" 
                    + BinderContract.TABLE_NAME 
                    + ")"
                    + " id : "+ id);
        }
        
        final String whereClause =
                BinderContract.COL_ID 
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param binder The entity to delete
     * @return count of updated entities
     */
    public int delete(final Binder binder) {
        return this.remove(binder.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Binder corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (MagicBinderApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                BinderContract.ALIASED_COL_ID 
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                BinderContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Binder entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = BinderContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                BinderContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }


}

