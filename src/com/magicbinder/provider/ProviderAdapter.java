/**************************************************************************
 * ProviderAdapter.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.provider;

import com.magicbinder.provider.base.ProviderAdapterBase;
import com.magicbinder.provider.base.MagicBinderProviderBase;
import com.magicbinder.data.base.SQLiteAdapterBase;

/**
 * ProviderAdapter<T>. 
 *
 * Feel free to add your custom generic methods here.
 *
 * @param <T> must extends Serializable
 */
public abstract class ProviderAdapter<T> extends ProviderAdapterBase<T> {

    /**
     * Provider Adapter Base constructor.
     *
     * @param adapter The context.
     */
    public ProviderAdapter(
                final MagicBinderProviderBase provider,
                final SQLiteAdapterBase<T> adapter) {
        super(provider, adapter);
    }
}
