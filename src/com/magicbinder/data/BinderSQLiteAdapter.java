/**************************************************************************
 * BinderSQLiteAdapter.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.data;

import com.magicbinder.data.base.BinderSQLiteAdapterBase;


/**
 * Binder adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class BinderSQLiteAdapter extends BinderSQLiteAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public BinderSQLiteAdapter(final android.content.Context ctx) {
        super(ctx);
    }
}
