/**************************************************************************
 * BinderProviderAdapterBase.java, MagicBinder Android
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



import com.magicbinder.entity.Binder;
import com.magicbinder.provider.ProviderAdapter;
import com.magicbinder.provider.MagicBinderProvider;
import com.magicbinder.provider.contract.BinderContract;
import com.magicbinder.provider.contract.Binder_CardContract;
import com.magicbinder.data.BinderSQLiteAdapter;
import com.magicbinder.data.Binder_CardSQLiteAdapter;

/**
 * BinderProviderAdapterBase.
 */
public abstract class BinderProviderAdapterBase
                extends ProviderAdapter<Binder> {

    /** TAG for debug purpose. */
    protected static final String TAG = "BinderProviderAdapter";

    /** BINDER_URI. */
    public      static Uri BINDER_URI;

    /** binder type. */
    protected static final String binderType =
            "binder";

    /** BINDER_ALL. */
    protected static final int BINDER_ALL =
            1989870026;
    /** BINDER_ONE. */
    protected static final int BINDER_ONE =
            1989870027;

    /** BINDER_BINDERS_CARDS_BINDER. */
    protected static final int BINDER_BINDERS_CARDS_BINDER =
            1989870028;

    /**
     * Static constructor.
     */
    static {
        BINDER_URI =
                MagicBinderProvider.generateUri(
                        binderType);
        MagicBinderProvider.getUriMatcher().addURI(
                MagicBinderProvider.authority,
                binderType,
                BINDER_ALL);
        MagicBinderProvider.getUriMatcher().addURI(
                MagicBinderProvider.authority,
                binderType + "/#",
                BINDER_ONE);
        MagicBinderProvider.getUriMatcher().addURI(
                MagicBinderProvider.authority,
                binderType + "/#" + "/binders_cards_binder",
                BINDER_BINDERS_CARDS_BINDER);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public BinderProviderAdapterBase(
            MagicBinderProviderBase provider) {
        super(
            provider,
            new BinderSQLiteAdapter(provider.getContext()));

        this.uriIds.add(BINDER_ALL);
        this.uriIds.add(BINDER_ONE);
        this.uriIds.add(BINDER_BINDERS_CARDS_BINDER);
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
            case BINDER_ALL:
                result = collection + "binder";
                break;
            case BINDER_ONE:
                result = single + "binder";
                break;
            case BINDER_BINDERS_CARDS_BINDER:
                result = collection + "binder";
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
            case BINDER_ONE:
                String id = uri.getPathSegments().get(1);
                selection = BinderContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case BINDER_ALL:
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
            case BINDER_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(BinderContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            BINDER_URI,
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
        int binderId;

        switch (matchedUri) {

            case BINDER_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case BINDER_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;
            
            case BINDER_BINDERS_CARDS_BINDER:
                binderId = Integer.parseInt(uri.getPathSegments().get(1));
                Binder_CardSQLiteAdapter binders_cards_binderAdapter = new Binder_CardSQLiteAdapter(this.ctx);
                binders_cards_binderAdapter.open(this.getDb());
                result = binders_cards_binderAdapter.getByBinder(binderId, Binder_CardContract.ALIASED_COLS, selection, selectionArgs, null);
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
            case BINDER_ONE:
                selectionArgs = new String[1];
                selection = BinderContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case BINDER_ALL:
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
        return BINDER_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = BinderContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    BinderContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

