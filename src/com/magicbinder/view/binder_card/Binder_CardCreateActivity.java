/**************************************************************************
 * Binder_CardCreateActivity.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.view.binder_card;

import com.magicbinder.R;

import com.magicbinder.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** 
 * Binder_Card create Activity.
 *
 * This only contains a Binder_CardCreateFragment.
 *
 * @see android.app.Activity
 */
public class Binder_CardCreateActivity extends HarmonyFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_binder_card_create);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
