/**************************************************************************
 * Binder_CardEditActivity.java, MagicBinder Android
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

/** Binder_Card edit Activity.
 *
 * This only contains a Binder_CardEditFragment.
 *
 * @see android.app.Activity
 */
public class Binder_CardEditActivity extends HarmonyFragmentActivity {

    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_binder_card_edit);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
