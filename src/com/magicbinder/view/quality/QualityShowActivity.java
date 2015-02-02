/**************************************************************************
 * QualityShowActivity.java, MagicBinder Android
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
import com.magicbinder.view.quality.QualityShowFragment.DeleteCallback;
import android.os.Bundle;

/** Quality show Activity.
 *
 * This only contains a QualityShowFragment.
 *
 * @see android.app.Activity
 */
public class QualityShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_quality_show);
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
