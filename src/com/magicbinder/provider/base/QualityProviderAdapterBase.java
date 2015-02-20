/**************************************************************************
 * QualityProviderAdapterBase.java, MagicBinder Android
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



import com.magicbinder.entity.Quality;
import com.magicbinder.provider.ProviderAdapter;
import com.magicbinder.provider.MagicBinderProvider;
import com.magicbinder.provider.contract.QualityContract;
import com.magicbinder.provider.contract.Binder_CardContract;
import com.magicbinder.data.QualitySQLiteAdapter;
import com.magicbinder.data.Binder_CardSQLiteAdapter;

/**
 * QualityProviderAdapterBase.
 */
public abstract class QualityProviderAdapterBase
                extends ProviderAdapter<Quality> {

    /** TAG for debug purpose. */
    protected static final String TAG = "QualityProviderAdapter";

    /** QUALITY_URI. */
    public      static Uri QUALITY_URI;

    /** quality type. */
    protected static final String qualityType =
            "quality";

    /** QUALITY_ALL. */
    protected static final int QUALITY_ALL =
            1979098913;
    /** QUALITY_ONE. */
    protected static final int QUALITY_ONE =
            1979098914;

    /** QUALITY_BINDERS_CARDS_QUALITY. */
    protected static final int QUALITY_BINDERS_CARDS_QUALITY =
            1979098915;

    /**
     * Static constructor.
     */
    static {
        QUALITY_URI =
                MagicBinderProvider.generateUri(
                        qualityType);
        MagicBinderProvider.getUriMatcher().addURI(
                MagicBinderProvider.authority,
                qualityType,
                QUALITY_ALL);
        MagicBinderProvider.getUriMatcher().addURI(
                MagicBinderProvider.authority,
                qualityType + "/#",
                QUALITY_ONE);
        MagicBinderProvider.getUriMatcher().addURI(
                MagicBinderProvider.authority,
                qualityType + "/#" + "/binders_cards_quality",
                QUALITY_BINDERS_CARDS_QUALITY);
    }

    /**
     * Constructor.
     * @param provider context
     */
    public QualityProviderAdapterBase(
            MagicBinderProviderBase provider) {
        super(
            provider,
            new QualitySQLiteAdapter(provider.getContext()));

        this.uriIds.add(QUALITY_ALL);
        this.uriIds.add(QUALITY_ONE);
        this.uriIds.add(QUALITY_BINDERS_CARDS_QUALITY);
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
            case QUALITY_ALL:
                result = collection + "quality";
                break;
            case QUALITY_ONE:
                result = single + "quality";
                break;
            case QUALITY_BINDERS_CARDS_QUALITY:
                result = collection + "quality";
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
            case QUALITY_ONE:
                String id = uri.getPathSegments().get(1);
                selection = QualityContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case QUALITY_ALL:
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
            case QUALITY_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(QualityContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            QUALITY_URI,
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
        int qualityId;

        switch (matchedUri) {

            case QUALITY_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case QUALITY_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;
            
            case QUALITY_BINDERS_CARDS_QUALITY:
                qualityId = Integer.parseInt(uri.getPathSegments().get(1));
                Binder_CardSQLiteAdapter binders_cards_qualityAdapter = new Binder_CardSQLiteAdapter(this.ctx);
                binders_cards_qualityAdapter.open(this.getDb());
                result = binders_cards_qualityAdapter.getByQuality(qualityId, Binder_CardContract.ALIASED_COLS, selection, selectionArgs, null);
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
            case QUALITY_ONE:
                selectionArgs = new String[1];
                selection = QualityContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case QUALITY_ALL:
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
        return QUALITY_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = QualityContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    QualityContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

