/**************************************************************************
 * BinderCreateActivity.java, MagicBinder Android
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

import android.os.Bundle;

/** 
 * Binder create Activity.
 *
 * This only contains a BinderCreateFragment.
 *
 * @see android.app.Activity
 */
public class BinderCreateActivity extends HarmonyFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_binder_create);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
