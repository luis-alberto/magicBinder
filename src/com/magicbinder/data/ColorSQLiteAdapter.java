/**************************************************************************
 * ColorSQLiteAdapter.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.data;

import java.util.ArrayList;

import com.magicbinder.data.base.ColorSQLiteAdapterBase;
import com.magicbinder.entity.Color;
import com.magicbinder.provider.contract.CardContract;


/**
 * Color adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class ColorSQLiteAdapter extends ColorSQLiteAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public ColorSQLiteAdapter(final android.content.Context ctx) {
        super(ctx);
    }
    /**
     * Find & read Color by label in database.
     *
     * @param label Label of Color
     * @return Color entity
     */
    public Color getByLabel(final String label) {
        ArrayList<Color> colors = this.getAll();
        for (Color color : colors) {
            if (color.getLabel().equalsIgnoreCase(label)){
                return color;
            }
        }
        return null;
    }
}
