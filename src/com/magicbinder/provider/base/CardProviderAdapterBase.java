/**************************************************************************
 * CardProviderAdapterBase.java, MagicBinder Android
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



import com.magicbinder.entity.Card;
import com.magicbinder.provider.ProviderAdapter;
import com.magicbinder.provider.MagicBinderProvider;
import com.magicbinder.provider.contract.CardContract;
import com.magicbinder.provider.contract.ColorContract;
import com.magicbinder.provider.contract.Binder_CardContract;
import com.magicbinder.data.CardSQLiteAdapter;
import com.magicbinder.data.ColortoCardSQLiteAdapter;
import com.magicbinder.data.ColorSQLiteAdapter;
import com.magicbinder.data.Binder_CardSQLiteAdapter;

/**
 * CardProviderAdapterBase.
 */
public abstract class CardProviderAdapterBase
                extends ProviderAdapter<Card> {

    /** TAG for debug purpose. */
    protected static final String TAG = "CardProviderAdapter";

    /** CARD_URI. */
    public      static Uri CARD_URI;

    /** card type. */
    protected static final String cardType =
            "card";

    /** CARD_ALL. */
    protected static final int CARD_ALL =
            2092848;
    /** CARD_ONE. */
    protected static final int CARD_ONE =
            2092849;

    /** CARD_COLORS. */
    protected static final int CARD_COLORS =
            2092850;
    /** CARD_BINDERS_CARDS_CARD. */
    protected static final int CARD_BINDERS_CARDS_CARD =
            2092851;

    /**
     * Static constructor.
     */
    static {
        CARD_URI =
                MagicBinderProvider.generateUri(
                        cardType);
        MagicBinderProvider.getUriMatcher().addURI(
                MagicBinderProvider.authority,
                cardType,
                CARD_ALL);
        MagicBinderProvider.getUriMatcher().addURI(
                MagicBinderProvider.authority,
                cardType + "/#",
                CARD_ONE);
        MagicBinderProvider.getUriMatcher().addURI(
                MagicBinderProvider.authority,
                cardType + "/#" + "/colors",
                CARD_COLORS);
        MagicBinderProvider.getUriMatcher().addURI(
                MagicBinderProvider.authority,
                cardType + "/#" + "/binders_cards_card",
                CARD_BINDERS_CARDS_CARD);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public CardProviderAdapterBase(
            MagicBinderProviderBase provider) {
        super(
            provider,
            new CardSQLiteAdapter(provider.getContext()));

        this.uriIds.add(CARD_ALL);
        this.uriIds.add(CARD_ONE);
        this.uriIds.add(CARD_COLORS);
        this.uriIds.add(CARD_BINDERS_CARDS_CARD);
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
            case CARD_ALL:
                result = collection + "card";
                break;
            case CARD_ONE:
                result = single + "card";
                break;
            case CARD_COLORS:
                result = collection + "card";
                break;
            case CARD_BINDERS_CARDS_CARD:
                result = collection + "card";
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
            case CARD_ONE:
                String id = uri.getPathSegments().get(1);
                selection = CardContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case CARD_ALL:
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
            case CARD_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(CardContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            CARD_URI,
                            values.getAsString(CardContract.COL_ID));
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
        int cardId;

        switch (matchedUri) {

            case CARD_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case CARD_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;
            
            case CARD_COLORS:
                cardId = Integer.parseInt(uri.getPathSegments().get(1));
                ColortoCardSQLiteAdapter colorsAdapter = new ColortoCardSQLiteAdapter(this.ctx);
                colorsAdapter.open(this.getDb());
                result = colorsAdapter.getByCards(cardId, ColorContract.ALIASED_COLS, selection, selectionArgs, null);
                break;

            case CARD_BINDERS_CARDS_CARD:
                cardId = Integer.parseInt(uri.getPathSegments().get(1));
                Binder_CardSQLiteAdapter binders_cards_cardAdapter = new Binder_CardSQLiteAdapter(this.ctx);
                binders_cards_cardAdapter.open(this.getDb());
                result = binders_cards_cardAdapter.getByCard(cardId, Binder_CardContract.ALIASED_COLS, selection, selectionArgs, null);
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
            case CARD_ONE:
                selectionArgs = new String[1];
                selection = CardContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case CARD_ALL:
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
        return CARD_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = CardContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    CardContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

