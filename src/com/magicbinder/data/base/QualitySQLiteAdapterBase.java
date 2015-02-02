/**************************************************************************
 * QualitySQLiteAdapterBase.java, MagicBinder Android
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
import com.magicbinder.data.QualitySQLiteAdapter;
import com.magicbinder.data.Binder_CardSQLiteAdapter;
import com.magicbinder.provider.contract.QualityContract;
import com.magicbinder.provider.contract.Binder_CardContract;
import com.magicbinder.entity.Quality;
import com.magicbinder.entity.Binder_Card;


import com.magicbinder.MagicBinderApplication;


/** Quality adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit QualityAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class QualitySQLiteAdapterBase
                        extends SQLiteAdapter<Quality> {

    /** TAG for debug purpose. */
    protected static final String TAG = "QualityDBAdapter";


    /**
     * Get the table name used in DB for your Quality entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return QualityContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Quality entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = QualityContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Quality entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return QualityContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + QualityContract.TABLE_NAME    + " ("
        
         + QualityContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + QualityContract.COL_LABEL    + " VARCHAR NOT NULL"

        
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public QualitySQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Quality entity to Content Values for database.
     * @param item Quality entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Quality item) {
        return QualityContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Quality entity.
     * @param cursor android.database.Cursor object
     * @return Quality entity
     */
    public Quality cursorToItem(final android.database.Cursor cursor) {
        return QualityContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Quality entity.
     * @param cursor android.database.Cursor object
     * @param result Quality entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Quality result) {
        QualityContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Quality by id in database.
     *
     * @param id Identify of Quality
     * @return Quality entity
     */
    public Quality getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Quality result = this.cursorToItem(cursor);
        cursor.close();

        final Binder_CardSQLiteAdapter binders_cards_qualityAdapter =
                new Binder_CardSQLiteAdapter(this.ctx);
        binders_cards_qualityAdapter.open(this.mDatabase);
        android.database.Cursor binders_cards_qualityCursor = binders_cards_qualityAdapter
                    .getByQuality(
                            result.getId(),
                            Binder_CardContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setBinders_cards_quality(binders_cards_qualityAdapter.cursorToItems(binders_cards_qualityCursor));
        return result;
    }


    /**
     * Read All Qualitys entities.
     *
     * @return List of Quality entities
     */
    public ArrayList<Quality> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Quality> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Quality entity into database.
     *
     * @param item The Quality entity to persist
     * @return Id of the Quality entity
     */
    public long insert(final Quality item) {
        if (MagicBinderApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + QualityContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                QualityContract.itemToContentValues(item);
        values.remove(QualityContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    QualityContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        if (item.getBinders_cards_quality() != null) {
            Binder_CardSQLiteAdapterBase binders_cards_qualityAdapter =
                    new Binder_CardSQLiteAdapter(this.ctx);
            binders_cards_qualityAdapter.open(this.mDatabase);
            for (Binder_Card binder_card
                        : item.getBinders_cards_quality()) {
                binder_card.setQuality(item);
                binders_cards_qualityAdapter.insertOrUpdate(binder_card);
            }
        }
        return insertResult;
    }

    /**
     * Either insert or update a Quality entity into database whether.
     * it already exists or not.
     *
     * @param item The Quality entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Quality item) {
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
     * Update a Quality entity into database.
     *
     * @param item The Quality entity to persist
     * @return count of updated entities
     */
    public int update(final Quality item) {
        if (MagicBinderApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + QualityContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                QualityContract.itemToContentValues(item);
        final String whereClause =
                 QualityContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Quality entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (MagicBinderApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB(" 
                    + QualityContract.TABLE_NAME 
                    + ")"
                    + " id : "+ id);
        }
        
        final String whereClause =
                QualityContract.COL_ID 
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param quality The entity to delete
     * @return count of updated entities
     */
    public int delete(final Quality quality) {
        return this.remove(quality.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Quality corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (MagicBinderApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                QualityContract.ALIASED_COL_ID 
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                QualityContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Quality entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = QualityContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                QualityContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }


}

