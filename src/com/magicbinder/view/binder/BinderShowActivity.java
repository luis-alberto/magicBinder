/**************************************************************************
 * BinderShowActivity.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.view.binder;

import com.magicbinder.R;

import com.magicbinder.harmony.view.HarmonyFragmentActivity;
import com.magicbinder.view.binder.BinderShowFragment.DeleteCallback;
import android.os.Bundle;

/** Binder show Activity.
 *
 * This only contains a BinderShowFragment.
 *
 * @see android.app.Activity
 */
public class BinderShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_binder_show);
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
