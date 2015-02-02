/**************************************************************************
 * ColorEditActivity.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.view.color;

import com.magicbinder.R;

import com.magicbinder.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** Color edit Activity.
 *
 * This only contains a ColorEditFragment.
 *
 * @see android.app.Activity
 */
public class ColorEditActivity extends HarmonyFragmentActivity {

    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_color_edit);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
