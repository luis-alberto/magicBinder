/**************************************************************************
 * BinderProviderAdapter.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.provider;

import com.magicbinder.provider.base.BinderProviderAdapterBase;
import com.magicbinder.provider.base.MagicBinderProviderBase;

/**
 * BinderProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class BinderProviderAdapter
                    extends BinderProviderAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public BinderProviderAdapter(
            final MagicBinderProviderBase provider) {
        super(provider);
    }
}

