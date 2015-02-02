/**************************************************************************
 * Binder_CardSQLiteAdapterBase.java, MagicBinder Android
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


import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
import com.magicbinder.data.SQLiteAdapter;
import com.magicbinder.data.Binder_CardSQLiteAdapter;
import com.magicbinder.data.CardSQLiteAdapter;
import com.magicbinder.data.BinderSQLiteAdapter;
import com.magicbinder.data.QualitySQLiteAdapter;
import com.magicbinder.provider.contract.Binder_CardContract;
import com.magicbinder.provider.contract.CardContract;
import com.magicbinder.provider.contract.BinderContract;
import com.magicbinder.provider.contract.QualityContract;
import com.magicbinder.entity.Binder_Card;
import com.magicbinder.entity.Card;
import com.magicbinder.entity.Binder;
import com.magicbinder.entity.Quality;


import com.magicbinder.MagicBinderApplication;


/** Binder_Card adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit Binder_CardAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class Binder_CardSQLiteAdapterBase
                        extends SQLiteAdapter<Binder_Card> {

    /** TAG for debug purpose. */
    protected static final String TAG = "Binder_CardDBAdapter";


    /**
     * Get the table name used in DB for your Binder_Card entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return Binder_CardContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Binder_Card entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = Binder_CardContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Binder_Card entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return Binder_CardContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + Binder_CardContract.TABLE_NAME    + " ("
        
         + Binder_CardContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + Binder_CardContract.COL_QUANTITY    + " INTEGER NOT NULL,"
         + Binder_CardContract.COL_CARD_ID    + " INTEGER NOT NULL,"
         + Binder_CardContract.COL_BINDER_ID    + " INTEGER NOT NULL,"
         + Binder_CardContract.COL_QUALITY_ID    + " INTEGER NOT NULL,"

        
         + "FOREIGN KEY(" + Binder_CardContract.COL_CARD_ID + ") REFERENCES " 
             + CardContract.TABLE_NAME 
                + " (" + CardContract.COL_ID + "),"
         + "FOREIGN KEY(" + Binder_CardContract.COL_BINDER_ID + ") REFERENCES " 
             + BinderContract.TABLE_NAME 
                + " (" + BinderContract.COL_ID + "),"
         + "FOREIGN KEY(" + Binder_CardContract.COL_QUALITY_ID + ") REFERENCES " 
             + QualityContract.TABLE_NAME 
                + " (" + QualityContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public Binder_CardSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Binder_Card entity to Content Values for database.
     * @param item Binder_Card entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Binder_Card item) {
        return Binder_CardContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Binder_Card entity.
     * @param cursor android.database.Cursor object
     * @return Binder_Card entity
     */
    public Binder_Card cursorToItem(final android.database.Cursor cursor) {
        return Binder_CardContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Binder_Card entity.
     * @param cursor android.database.Cursor object
     * @param result Binder_Card entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Binder_Card result) {
        Binder_CardContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Binder_Card by id in database.
     *
     * @param id Identify of Binder_Card
     * @return Binder_Card entity
     */
    public Binder_Card getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Binder_Card result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getCard() != null) {
            final CardSQLiteAdapter cardAdapter =
                    new CardSQLiteAdapter(this.ctx);
            cardAdapter.open(this.mDatabase);
            
            result.setCard(cardAdapter.getByID(
                            result.getCard().getId()));
        }
        if (result.getBinder() != null) {
            final BinderSQLiteAdapter binderAdapter =
                    new BinderSQLiteAdapter(this.ctx);
            binderAdapter.open(this.mDatabase);
            
            result.setBinder(binderAdapter.getByID(
                            result.getBinder().getId()));
        }
        if (result.getQuality() != null) {
            final QualitySQLiteAdapter qualityAdapter =
                    new QualitySQLiteAdapter(this.ctx);
            qualityAdapter.open(this.mDatabase);
            
            result.setQuality(qualityAdapter.getByID(
                            result.getQuality().getId()));
        }
        return result;
    }

    /**
     * Find & read Binder_Card by card.
     * @param cardId cardId
     * @param orderBy Order by string (can be null)
     * @return List of Binder_Card entities
     */
     public android.database.Cursor getByCard(final int cardId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = Binder_CardContract.COL_CARD_ID + "= ?";
        String idSelectionArgs = String.valueOf(cardId);
        if (!Strings.isNullOrEmpty(selection)) {
            selection += " AND " + idSelection;
            selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
        } else {
            selection = idSelection;
            selectionArgs = new String[]{idSelectionArgs};
        }
        final android.database.Cursor cursor = this.query(
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);

        return cursor;
     }
    /**
     * Find & read Binder_Card by binder.
     * @param binderId binderId
     * @param orderBy Order by string (can be null)
     * @return List of Binder_Card entities
     */
     public android.database.Cursor getByBinder(final int binderId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = Binder_CardContract.COL_BINDER_ID + "= ?";
        String idSelectionArgs = String.valueOf(binderId);
        if (!Strings.isNullOrEmpty(selection)) {
            selection += " AND " + idSelection;
            selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
        } else {
            selection = idSelection;
            selectionArgs = new String[]{idSelectionArgs};
        }
        final android.database.Cursor cursor = this.query(
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);

        return cursor;
     }
    /**
     * Find & read Binder_Card by quality.
     * @param qualityId qualityId
     * @param orderBy Order by string (can be null)
     * @return List of Binder_Card entities
     */
     public android.database.Cursor getByQuality(final int qualityId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = Binder_CardContract.COL_QUALITY_ID + "= ?";
        String idSelectionArgs = String.valueOf(qualityId);
        if (!Strings.isNullOrEmpty(selection)) {
            selection += " AND " + idSelection;
            selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
        } else {
            selection = idSelection;
            selectionArgs = new String[]{idSelectionArgs};
        }
        final android.database.Cursor cursor = this.query(
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);

        return cursor;
     }

    /**
     * Read All Binder_Cards entities.
     *
     * @return List of Binder_Card entities
     */
    public ArrayList<Binder_Card> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Binder_Card> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Binder_Card entity into database.
     *
     * @param item The Binder_Card entity to persist
     * @return Id of the Binder_Card entity
     */
    public long insert(final Binder_Card item) {
        if (MagicBinderApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + Binder_CardContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                Binder_CardContract.itemToContentValues(item);
        values.remove(Binder_CardContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    Binder_CardContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a Binder_Card entity into database whether.
     * it already exists or not.
     *
     * @param item The Binder_Card entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Binder_Card item) {
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
     * Update a Binder_Card entity into database.
     *
     * @param item The Binder_Card entity to persist
     * @return count of updated entities
     */
    public int update(final Binder_Card item) {
        if (MagicBinderApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + Binder_CardContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                Binder_CardContract.itemToContentValues(item);
        final String whereClause =
                 Binder_CardContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Binder_Card entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (MagicBinderApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB(" 
                    + Binder_CardContract.TABLE_NAME 
                    + ")"
                    + " id : "+ id);
        }
        
        final String whereClause =
                Binder_CardContract.COL_ID 
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param binder_Card The entity to delete
     * @return count of updated entities
     */
    public int delete(final Binder_Card binder_Card) {
        return this.remove(binder_Card.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Binder_Card corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (MagicBinderApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                Binder_CardContract.ALIASED_COL_ID 
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                Binder_CardContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Binder_Card entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = Binder_CardContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                Binder_CardContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }


}

