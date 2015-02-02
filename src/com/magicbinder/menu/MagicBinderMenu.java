/**************************************************************************
 * MagicBinderMenu.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.menu;


import android.support.v4.app.Fragment;

import com.magicbinder.menu.base.MagicBinderMenuBase;

/**
 * MagicBinderMenu.
 * 
 * This class is an engine used to manage the different menus of your application.
 * Its use is quite simple :
 * Create a class called [YourMenuName]MenuWrapper in this package and
 * make it implement the interface MenuWrapperBase.
 * (For examples, please see CrudCreateMenuWrapper and CrudEditDeleteMenuWrapper in
 * this package.)
 * When this is done, just call this harmony command :
 * script/console.sh orm:menu:update.
 * This will auto-generate a group id for your menu.
 */
public class MagicBinderMenu
                extends MagicBinderMenuBase {

    /** Singleton unique instance. */
    private static volatile MagicBinderMenu singleton;

    /**
     * Constructor.
     * @param ctx The android.content.Context
     * @throws Exception If something bad happened
     */
    public MagicBinderMenu(final android.content.Context ctx) throws Exception {
        super(ctx);
    }

    /**
     * Constructor.
     * @param ctx The context
     * @param fragment The parent fragment
     * @throws Exception If something bad happened
     */
    public MagicBinderMenu(final android.content.Context ctx,
                        final Fragment fragment) throws Exception {
        super(ctx, fragment);
    }

    /** Get unique instance.
     * @param ctx The context
     * @return MagicBinderMenu instance
     * @throws Exception If something bad happened
     */
    public static final synchronized MagicBinderMenu getInstance(
                        final android.content.Context ctx) throws Exception {
        return getInstance(ctx, null);
    }

    /** Get unique instance.
     * @param ctx The context
     * @param fragment The parent fragment
     * @return MagicBinderMenu instance
     * @throws Exception If something bad happened
     */
    public static final synchronized MagicBinderMenu getInstance(
            final android.content.Context ctx, final Fragment fragment) throws Exception {
        if (singleton == null) {
            singleton = new MagicBinderMenu(ctx, fragment);
        }  else {
            singleton.ctx = ctx;
            singleton.fragment = fragment;
        }

        return singleton;
    }
}
