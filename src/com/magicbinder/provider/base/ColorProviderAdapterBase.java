/**************************************************************************
 * ColorProviderAdapterBase.java, MagicBinder Android
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



import com.magicbinder.entity.Color;
import com.magicbinder.provider.ProviderAdapter;
import com.magicbinder.provider.MagicBinderProvider;
import com.magicbinder.provider.contract.ColorContract;
import com.magicbinder.provider.contract.CardContract;
import com.magicbinder.data.ColorSQLiteAdapter;
import com.magicbinder.data.ColortoCardSQLiteAdapter;
import com.magicbinder.data.CardSQLiteAdapter;

/**
 * ColorProviderAdapterBase.
 */
public abstract class ColorProviderAdapterBase
                extends ProviderAdapter<Color> {

    /** TAG for debug purpose. */
    protected static final String TAG = "ColorProviderAdapter";

    /** COLOR_URI. */
    public      static Uri COLOR_URI;

    /** color type. */
    protected static final String colorType =
            "color";

    /** COLOR_ALL. */
    protected static final int COLOR_ALL =
            65290051;
    /** COLOR_ONE. */
    protected static final int COLOR_ONE =
            65290052;

    /** COLOR_CARDS. */
    protected static final int COLOR_CARDS =
            65290053;

    /**
     * Static constructor.
     */
    static {
        COLOR_URI =
                MagicBinderProvider.generateUri(
                        colorType);
        MagicBinderProvider.getUriMatcher().addURI(
                MagicBinderProvider.authority,
                colorType,
                COLOR_ALL);
        MagicBinderProvider.getUriMatcher().addURI(
                MagicBinderProvider.authority,
                colorType + "/#",
                COLOR_ONE);
        MagicBinderProvider.getUriMatcher().addURI(
                MagicBinderProvider.authority,
                colorType + "/#" + "/cards",
                COLOR_CARDS);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public ColorProviderAdapterBase(
            MagicBinderProviderBase provider) {
        super(
            provider,
            new ColorSQLiteAdapter(provider.getContext()));

        this.uriIds.add(COLOR_ALL);
        this.uriIds.add(COLOR_ONE);
        this.uriIds.add(COLOR_CARDS);
    }

    @Override
    public String getType(final Uri uri) {
        String result;
        final String single =
                "vnc.android.cursor.item/"
                    + MagicBinderProvider.authority + ".";
        final String collection =
                "vnc.android.cursor.collection/"
                    + MagicBinderProvider.authority + ".";

        int matchedUri = MagicBinderProviderBase
                .getUriMatcher().match(uri);

        switch (matchedUri) {
            case COLOR_ALL:
                result = collection + "color";
                break;
            case COLOR_ONE:
                result = single + "color";
                break;
            case COLOR_CARDS:
                result = collection + "color";
                break;
            default:
                result = null;
                break;
        }

        return result;
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
            case COLOR_ONE:
                String id = uri.getPathSegments().get(1);
                selection = ColorContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case COLOR_ALL:
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
            case COLOR_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(ColorContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            COLOR_URI,
                            String.valueOf(id));
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
        int colorId;

        switch (matchedUri) {

            case COLOR_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case COLOR_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;
            
            case COLOR_CARDS:
                colorId = Integer.parseInt(uri.getPathSegments().get(1));
                ColortoCardSQLiteAdapter cardsAdapter = new ColortoCardSQLiteAdapter(this.ctx);
                cardsAdapter.open(this.getDb());
                result = cardsAdapter.getByColors(colorId, CardContract.ALIASED_COLS, selection, selectionArgs, null);
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
            case COLOR_ONE:
                selectionArgs = new String[1];
                selection = ColorContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case COLOR_ALL:
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
        return COLOR_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = ColorContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    ColorContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

