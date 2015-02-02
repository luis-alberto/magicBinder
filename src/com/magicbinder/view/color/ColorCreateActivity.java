/**************************************************************************
 * ColorCreateActivity.java, MagicBinder Android
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

/** 
 * Color create Activity.
 *
 * This only contains a ColorCreateFragment.
 *
 * @see android.app.Activity
 */
public class ColorCreateActivity extends HarmonyFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_color_create);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
