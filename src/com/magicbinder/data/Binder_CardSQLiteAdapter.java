/**************************************************************************
 * Binder_CardSQLiteAdapter.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.data;

import com.magicbinder.data.base.Binder_CardSQLiteAdapterBase;


/**
 * Binder_Card adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class Binder_CardSQLiteAdapter extends Binder_CardSQLiteAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public Binder_CardSQLiteAdapter(final android.content.Context ctx) {
        super(ctx);
    }
}
