/**************************************************************************
 * ColortoCardSQLiteAdapterBase.java, MagicBinder Android
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
import com.magicbinder.data.ColortoCardSQLiteAdapter;
import com.magicbinder.data.CardSQLiteAdapter;
import com.magicbinder.data.ColorSQLiteAdapter;
import com.magicbinder.provider.contract.ColortoCardContract;
import com.magicbinder.provider.contract.CardContract;
import com.magicbinder.provider.contract.ColorContract;
import com.magicbinder.entity.Card;
import com.magicbinder.entity.Color;


import com.magicbinder.MagicBinderApplication;

import com.magicbinder.criterias.base.CriteriaExpression;
import com.magicbinder.criterias.base.Criterion;
import com.magicbinder.criterias.base.Criterion.Type;
import com.magicbinder.criterias.base.CriteriaExpression.GroupType;
import com.magicbinder.criterias.base.value.SelectValue;

/** ColortoCard adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit ColortoCardAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class ColortoCardSQLiteAdapterBase
                        extends SQLiteAdapter<Void> {

    /** TAG for debug purpose. */
    protected static final String TAG = "ColortoCardDBAdapter";


    /**
     * Get the table name used in DB for your ColortoCard entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return ColortoCardContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your ColortoCard entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = ColortoCardContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the ColortoCard entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return ColortoCardContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + ColortoCardContract.TABLE_NAME    + " ("
        
         + ColortoCardContract.COL_CARDS_ID    + " INTEGER NOT NULL,"
         + ColortoCardContract.COL_COLORS_ID    + " INTEGER NOT NULL,"

        
         + "FOREIGN KEY(" + ColortoCardContract.COL_CARDS_ID + ") REFERENCES " 
             + CardContract.TABLE_NAME 
                + " (" + CardContract.COL_ID + "),"
         + "FOREIGN KEY(" + ColortoCardContract.COL_COLORS_ID + ") REFERENCES " 
             + ColorContract.TABLE_NAME 
                + " (" + ColorContract.COL_ID + ")"
        + ", PRIMARY KEY (" + ColortoCardContract.COL_CARDS_ID + "," + ColortoCardContract.COL_COLORS_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public ColortoCardSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }


    /**
     * Query the DB to find a ColortoCard entity.
     *
     * @param cards The cards of the entity to get from the DB
     * @param colors The colors of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final Card cards,
                final Color colors) {

        String selection = ColortoCardContract.ALIASED_COL_CARDS_ID + " = ?";
        selection += " AND ";
        selection += ColortoCardContract.ALIASED_COL_COLORS_ID + " = ?";
        

        String[] selectionArgs = new String[2];
        selectionArgs[0] = String.valueOf(cards.getId());
        selectionArgs[1] = String.valueOf(colors.getId());

        return this.query(
                ColortoCardContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }



    /**
     * Insert a ColortoCard entity into database.
     *
     * @param cardsId cards
     * @param colorsId colors
     * @return Id of the ColortoCard entity
     */
    public long insert(final int cardsId,
            final int colorsId) {
        if (MagicBinderApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + ColortoCardContract.TABLE_NAME + ")");
        }

        ContentValues values = new ContentValues();
        values.put(ColortoCardContract.COL_CARDS_ID,
                cardsId);
        values.put(ColortoCardContract.COL_COLORS_ID,
                colorsId);

        return this.mDatabase.insert(
                ColortoCardContract.TABLE_NAME,
                null,
                values);
    }


    /**
     * Find & read ColortoCard by cards.
     * @param cardsId colors
     * @param orderBy Order by string (can be null)
     * @return ArrayList of Color matching cards
     */
    public android.database.Cursor getByCards(
            final int cardsId,
            final String[] projection,
            String selection,
            String[] selectionArgs,
            final String orderBy) {

        android.database.Cursor ret = null;
        CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
        crit.add(ColortoCardContract.COL_CARDS_ID,
                String.valueOf(cardsId),
                Type.EQUALS);
        SelectValue value = new SelectValue();
        value.setRefKey(ColortoCardContract.COL_COLORS_ID);
        value.setRefTable(ColortoCardContract.TABLE_NAME);
        value.setCriteria(crit);
        CriteriaExpression colorCrit = new CriteriaExpression(GroupType.AND);
        Criterion colorSelectCrit = new Criterion();
        colorSelectCrit.setKey(ColorContract.ALIASED_COL_ID);
        colorSelectCrit.setType(Type.IN);
        colorSelectCrit.addValue(value);
        colorCrit.add(colorSelectCrit);
        
        if (Strings.isNullOrEmpty(selection)) {
            selection = colorCrit.toSQLiteSelection();
            selectionArgs = colorCrit.toSQLiteSelectionArgs();
        } else {
            selection += " AND " + colorCrit.toSQLiteSelection();
            selectionArgs = ObjectArrays.concat(
                        colorCrit.toSQLiteSelectionArgs(),
                        selectionArgs,
                        String.class);
        }

        ret = this.mDatabase.query(ColorContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);
        return ret;
    }

    /**
     * Find & read ColortoCard by colors.
     * @param colorsId cards
     * @param orderBy Order by string (can be null)
     * @return ArrayList of Card matching colors
     */
    public android.database.Cursor getByColors(
            final int colorsId,
            final String[] projection,
            String selection,
            String[] selectionArgs,
            final String orderBy) {

        android.database.Cursor ret = null;
        CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
        crit.add(ColortoCardContract.COL_COLORS_ID,
                String.valueOf(colorsId),
                Type.EQUALS);
        SelectValue value = new SelectValue();
        value.setRefKey(ColortoCardContract.COL_CARDS_ID);
        value.setRefTable(ColortoCardContract.TABLE_NAME);
        value.setCriteria(crit);
        CriteriaExpression cardCrit = new CriteriaExpression(GroupType.AND);
        Criterion cardSelectCrit = new Criterion();
        cardSelectCrit.setKey(CardContract.ALIASED_COL_ID);
        cardSelectCrit.setType(Type.IN);
        cardSelectCrit.addValue(value);
        cardCrit.add(cardSelectCrit);
        
        if (Strings.isNullOrEmpty(selection)) {
            selection = cardCrit.toSQLiteSelection();
            selectionArgs = cardCrit.toSQLiteSelectionArgs();
        } else {
            selection += " AND " + cardCrit.toSQLiteSelection();
            selectionArgs = ObjectArrays.concat(
                        cardCrit.toSQLiteSelectionArgs(),
                        selectionArgs,
                        String.class);
        }

        ret = this.mDatabase.query(CardContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);
        return ret;
    }


    @Override
    public Void cursorToItem(android.database.Cursor c) {
        return null;
    }

    @Override
    public ContentValues itemToContentValues(Void item) {
        return null;
    }

    @Override
    public long insert(Void item) {
        return -1;
    }

    @Override
    public int update(Void item) {
        return 0;
    }

    @Override
    public int delete(Void item) {
        return 0;
    }
}

