/**************************************************************************
 * QualityEditActivity.java, MagicBinder Android
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

/** Quality edit Activity.
 *
 * This only contains a QualityEditFragment.
 *
 * @see android.app.Activity
 */
public class QualityEditActivity extends HarmonyFragmentActivity {

    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_quality_edit);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
