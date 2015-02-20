/**************************************************************************
 * CardProviderAdapter.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.provider;

import com.magicbinder.provider.base.CardProviderAdapterBase;
import com.magicbinder.provider.base.MagicBinderProviderBase;

/**
 * CardProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class CardProviderAdapter
                    extends CardProviderAdapterBase {

    /**
     * Constructor.
     * @param provider context
     */
    public CardProviderAdapter(
            final MagicBinderProviderBase provider) {
        super(provider);
    }
}

