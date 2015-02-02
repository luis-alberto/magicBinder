/**************************************************************************
 * ColorShowActivity.java, MagicBinder Android
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
import com.magicbinder.view.color.ColorShowFragment.DeleteCallback;
import android.os.Bundle;

/** Color show Activity.
 *
 * This only contains a ColorShowFragment.
 *
 * @see android.app.Activity
 */
public class ColorShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_color_show);
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
