/**************************************************************************
 * QualityCreateActivity.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.view.quality;

import com.magicbinder.R;

import com.magicbinder.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** 
 * Quality create Activity.
 *
 * This only contains a QualityCreateFragment.
 *
 * @see android.app.Activity
 */
public class QualityCreateActivity extends HarmonyFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_quality_create);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
