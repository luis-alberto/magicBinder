/**************************************************************************
 * ColortoCardProviderAdapterBase.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.provider.base;

import android.content.ContentUris;
import android.content.ContentValues;


import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;



import com.magicbinder.provider.ProviderAdapter;
import com.magicbinder.provider.MagicBinderProvider;
import com.magicbinder.provider.contract.ColortoCardContract;
import com.magicbinder.data.ColortoCardSQLiteAdapter;
import com.magicbinder.data.CardSQLiteAdapter;
import com.magicbinder.data.ColorSQLiteAdapter;

/**
 * ColortoCardProviderAdapterBase.
 */
public abstract class ColortoCardProviderAdapterBase
                extends ProviderAdapter<Void> {

    /** TAG for debug purpose. */
    protected static final String TAG = "ColortoCardProviderAdapter";

    /** COLORTOCARD_URI. */
    public      static Uri COLORTOCARD_URI;

    /** colortoCard type. */
    protected static final String colortoCardType =
            "colortocard";

    /** COLORTOCARD_ALL. */
    protected static final int COLORTOCARD_ALL =
            706164434;
    /** COLORTOCARD_ONE. */
    protected static final int COLORTOCARD_ONE =
            706164435;

    /** COLORTOCARD_CARDS. */
    protected static final int COLORTOCARD_CARDS =
            706164436;
    /** COLORTOCARD_COLORS. */
    protected static final int COLORTOCARD_COLORS =
            706164437;

    /**
     * Static constructor.
     */
    static {
        COLORTOCARD_URI =
                MagicBinderProvider.generateUri(
                        colortoCardType);
        MagicBinderProvider.getUriMatcher().addURI(
                MagicBinderProvider.authority,
                colortoCardType,
                COLORTOCARD_ALL);
        MagicBinderProvider.getUriMatcher().addURI(
                MagicBinderProvider.authority,
                colortoCardType + "/#" + "/#",
                COLORTOCARD_ONE);
        MagicBinderProvider.getUriMatcher().addURI(
                MagicBinderProvider.authority,
                colortoCardType + "/#" + "/#" + "/cards",
                COLORTOCARD_CARDS);
        MagicBinderProvider.getUriMatcher().addURI(
                MagicBinderProvider.authority,
                colortoCardType + "/#" + "/#" + "/colors",
                COLORTOCARD_COLORS);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public ColortoCardProviderAdapterBase(
            MagicBinderProviderBase provider) {
        super(
            provider,
            new ColortoCardSQLiteAdapter(provider.getContext()));

        this.uriIds.add(COLORTOCARD_ALL);
        this.uriIds.add(COLORTOCARD_ONE);
        this.uriIds.add(COLORTOCARD_CARDS);
        this.uriIds.add(COLORTOCARD_COLORS);
    }

    @Override
    public String getType(final Uri uri) {
        return null;
    }

    @Override
    public int delete(
            final Uri uri,
            String selection,
            String[] selectionArgs) {
        int matchedUri = MagicBinderProviderBase
                    .getUriMatcher().match(uri);
        int result = -1;
        switch (matchedUri) {
            case COLORTOCARD_ONE:
                String cardsId = uri.getPathSegments().get(1);
                String colorsId = uri.getPathSegments().get(2);
                selection = ColortoCardContract.COL_CARDS_ID
                        + " = ?"
                        + " AND "
                        + ColortoCardContract.COL_COLORS_ID
                        + " = ?";
                selectionArgs = new String[2];
                selectionArgs[0] = cardsId;
                selectionArgs[1] = colorsId;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case COLORTOCARD_ALL:
                result = this.adapter.delete(
                            selection,
                            selectionArgs);
                break;
            default:
                result = -1;
                break;
        }
        return result;
    }
    
    @Override
    public Uri insert(final Uri uri, final ContentValues values) {
        int matchedUri = MagicBinderProviderBase
                .getUriMatcher().match(uri);
                Uri result = null;
        int id = 0;
        switch (matchedUri) {
            case COLORTOCARD_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(ColortoCardContract.COL_CARDS_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            COLORTOCARD_URI,
                            values.getAsString(ColortoCardContract.COL_CARDS_ID)
                            + "/"
                            + values.getAsString(ColortoCardContract.COL_COLORS_ID));
                }
                break;
            default:
                result = null;
                break;
        }
        return result;
    }

    @Override
    public android.database.Cursor query(final Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        final String sortOrder) {

        int matchedUri = MagicBinderProviderBase.getUriMatcher()
                .match(uri);
        android.database.Cursor result = null;
        android.database.Cursor colortoCardCursor;

        switch (matchedUri) {

            case COLORTOCARD_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case COLORTOCARD_ONE:
                result = this.queryById(uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2));
                break;
            
            case COLORTOCARD_CARDS:
                colortoCardCursor = this.queryById(
                        uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2));
                
                if (colortoCardCursor.getCount() > 0) {
                    colortoCardCursor.moveToFirst();
                    int cardsId = colortoCardCursor.getInt(
                            colortoCardCursor.getColumnIndex(
                                    ColortoCardContract.COL_CARDS_ID));
                    
                    CardSQLiteAdapter cardAdapter = new CardSQLiteAdapter(this.ctx);
                    cardAdapter.open(this.getDb());
                    result = cardAdapter.query(cardsId);
                }
                break;

            case COLORTOCARD_COLORS:
                colortoCardCursor = this.queryById(
                        uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2));
                
                if (colortoCardCursor.getCount() > 0) {
                    colortoCardCursor.moveToFirst();
                    int colorsId = colortoCardCursor.getInt(
                            colortoCardCursor.getColumnIndex(
                                    ColortoCardContract.COL_COLORS_ID));
                    
                    ColorSQLiteAdapter colorAdapter = new ColorSQLiteAdapter(this.ctx);
                    colorAdapter.open(this.getDb());
                    result = colorAdapter.query(colorsId);
                }
                break;

            default:
                result = null;
                break;
        }

        return result;
    }

    @Override
    public int update(
            final Uri uri,
            final ContentValues values,
            String selection,
            String[] selectionArgs) {

        
        int matchedUri = MagicBinderProviderBase.getUriMatcher()
                .match(uri);
        int result = -1;
        switch (matchedUri) {
            case COLORTOCARD_ONE:
                selectionArgs = new String[2];
                selection = ColortoCardContract.COL_CARDS_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                selection += " AND " + ColortoCardContract.COL_COLORS_ID + " = ?";
                selectionArgs[1] = uri.getPathSegments().get(2);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case COLORTOCARD_ALL:
                result = this.adapter.update(
                            values,
                            selection,
                            selectionArgs);
                break;
            default:
                result = -1;
                break;
        }
        return result;
    }



    /**
     * Get the entity URI.
     * @return The URI
     */
    @Override
    public Uri getUri() {
        return COLORTOCARD_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String cardsId, String colorsId) {
        android.database.Cursor result = null;
        String selection = ColortoCardContract.ALIASED_COL_CARDS_ID
                        + " = ?"
                        + " AND "
                        + ColortoCardContract.ALIASED_COL_COLORS_ID
                        + " = ?";

        String[] selectionArgs = new String[2];
        selectionArgs[0] = cardsId;
        selectionArgs[1] = colorsId;
        
        

        result = this.adapter.query(
                    ColortoCardContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

