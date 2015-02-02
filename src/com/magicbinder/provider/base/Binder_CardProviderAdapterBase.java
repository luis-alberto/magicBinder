/**************************************************************************
 * Binder_CardProviderAdapterBase.java, MagicBinder Android
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



import com.magicbinder.entity.Binder_Card;
import com.magicbinder.provider.ProviderAdapter;
import com.magicbinder.provider.MagicBinderProvider;
import com.magicbinder.provider.contract.Binder_CardContract;
import com.magicbinder.data.Binder_CardSQLiteAdapter;
import com.magicbinder.data.CardSQLiteAdapter;
import com.magicbinder.data.BinderSQLiteAdapter;
import com.magicbinder.data.QualitySQLiteAdapter;

/**
 * Binder_CardProviderAdapterBase.
 */
public abstract class Binder_CardProviderAdapterBase
                extends ProviderAdapter<Binder_Card> {

    /** TAG for debug purpose. */
    protected static final String TAG = "Binder_CardProviderAdapter";

    /** BINDER_CARD_URI. */
    public      static Uri BINDER_CARD_URI;

    /** binder_Card type. */
    protected static final String binder_CardType =
            "binder_card";

    /** BINDER_CARD_ALL. */
    protected static final int BINDER_CARD_ALL =
            2060766075;
    /** BINDER_CARD_ONE. */
    protected static final int BINDER_CARD_ONE =
            2060766076;

    /** BINDER_CARD_CARD. */
    protected static final int BINDER_CARD_CARD =
            2060766077;
    /** BINDER_CARD_BINDER. */
    protected static final int BINDER_CARD_BINDER =
            2060766078;
    /** BINDER_CARD_QUALITY. */
    protected static final int BINDER_CARD_QUALITY =
            2060766079;

    /**
     * Static constructor.
     */
    static {
        BINDER_CARD_URI =
                MagicBinderProvider.generateUri(
                        binder_CardType);
        MagicBinderProvider.getUriMatcher().addURI(
                MagicBinderProvider.authority,
                binder_CardType,
                BINDER_CARD_ALL);
        MagicBinderProvider.getUriMatcher().addURI(
                MagicBinderProvider.authority,
                binder_CardType + "/#",
                BINDER_CARD_ONE);
        MagicBinderProvider.getUriMatcher().addURI(
                MagicBinderProvider.authority,
                binder_CardType + "/#" + "/card",
                BINDER_CARD_CARD);
        MagicBinderProvider.getUriMatcher().addURI(
                MagicBinderProvider.authority,
                binder_CardType + "/#" + "/binder",
                BINDER_CARD_BINDER);
        MagicBinderProvider.getUriMatcher().addURI(
                MagicBinderProvider.authority,
                binder_CardType + "/#" + "/quality",
                BINDER_CARD_QUALITY);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public Binder_CardProviderAdapterBase(
            MagicBinderProviderBase provider) {
        super(
            provider,
            new Binder_CardSQLiteAdapter(provider.getContext()));

        this.uriIds.add(BINDER_CARD_ALL);
        this.uriIds.add(BINDER_CARD_ONE);
        this.uriIds.add(BINDER_CARD_CARD);
        this.uriIds.add(BINDER_CARD_BINDER);
        this.uriIds.add(BINDER_CARD_QUALITY);
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
            case BINDER_CARD_ALL:
                result = collection + "binder_card";
                break;
            case BINDER_CARD_ONE:
                result = single + "binder_card";
                break;
            case BINDER_CARD_CARD:
                result = single + "binder_card";
                break;
            case BINDER_CARD_BINDER:
                result = single + "binder_card";
                break;
            case BINDER_CARD_QUALITY:
                result = single + "binder_card";
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
            case BINDER_CARD_ONE:
                String id = uri.getPathSegments().get(1);
                selection = Binder_CardContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case BINDER_CARD_ALL:
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
            case BINDER_CARD_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(Binder_CardContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            BINDER_CARD_URI,
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
        android.database.Cursor binder_CardCursor;

        switch (matchedUri) {

            case BINDER_CARD_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case BINDER_CARD_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;
            
            case BINDER_CARD_CARD:
                binder_CardCursor = this.queryById(
                        uri.getPathSegments().get(1));
                
                if (binder_CardCursor.getCount() > 0) {
                    binder_CardCursor.moveToFirst();
                    int cardId = binder_CardCursor.getInt(
                            binder_CardCursor.getColumnIndex(
                                    Binder_CardContract.COL_CARD_ID));
                    
                    CardSQLiteAdapter cardAdapter = new CardSQLiteAdapter(this.ctx);
                    cardAdapter.open(this.getDb());
                    result = cardAdapter.query(cardId);
                }
                break;

            case BINDER_CARD_BINDER:
                binder_CardCursor = this.queryById(
                        uri.getPathSegments().get(1));
                
                if (binder_CardCursor.getCount() > 0) {
                    binder_CardCursor.moveToFirst();
                    int binderId = binder_CardCursor.getInt(
                            binder_CardCursor.getColumnIndex(
                                    Binder_CardContract.COL_BINDER_ID));
                    
                    BinderSQLiteAdapter binderAdapter = new BinderSQLiteAdapter(this.ctx);
                    binderAdapter.open(this.getDb());
                    result = binderAdapter.query(binderId);
                }
                break;

            case BINDER_CARD_QUALITY:
                binder_CardCursor = this.queryById(
                        uri.getPathSegments().get(1));
                
                if (binder_CardCursor.getCount() > 0) {
                    binder_CardCursor.moveToFirst();
                    int qualityId = binder_CardCursor.getInt(
                            binder_CardCursor.getColumnIndex(
                                    Binder_CardContract.COL_QUALITY_ID));
                    
                    QualitySQLiteAdapter qualityAdapter = new QualitySQLiteAdapter(this.ctx);
                    qualityAdapter.open(this.getDb());
                    result = qualityAdapter.query(qualityId);
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
            case BINDER_CARD_ONE:
                selectionArgs = new String[1];
                selection = Binder_CardContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case BINDER_CARD_ALL:
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
        return BINDER_CARD_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = Binder_CardContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    Binder_CardContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

