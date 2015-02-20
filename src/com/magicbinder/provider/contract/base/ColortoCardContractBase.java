/**************************************************************************
 * ColortoCardContractBase.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.provider.contract.base;

import android.content.ContentValues;


import java.util.ArrayList;

import com.magicbinder.entity.Card;
import com.magicbinder.entity.Color;



import com.magicbinder.provider.contract.ColortoCardContract;

/** MagicBinder contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class ColortoCardContractBase {


    /** cards_id. */
    public static final String COL_CARDS_ID =
            "cards_id";
    /** Alias. */
    public static final String ALIASED_COL_CARDS_ID =
            ColortoCardContract.TABLE_NAME + "." + COL_CARDS_ID;

    /** colors_id. */
    public static final String COL_COLORS_ID =
            "colors_id";
    /** Alias. */
    public static final String ALIASED_COL_COLORS_ID =
            ColortoCardContract.TABLE_NAME + "." + COL_COLORS_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "ColortoCard";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "ColortoCard";
    /** Global Fields. */
    public static final String[] COLS = new String[] {
        ColortoCardContract.COL_CARDS_ID,
        ColortoCardContract.COL_COLORS_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        ColortoCardContract.ALIASED_COL_CARDS_ID,
        ColortoCardContract.ALIASED_COL_COLORS_ID
    };

}
