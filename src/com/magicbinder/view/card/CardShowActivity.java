/**************************************************************************
 * CardShowActivity.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.view.card;

import com.magicbinder.R;

import com.magicbinder.harmony.view.HarmonyFragmentActivity;
import com.magicbinder.view.card.CardShowFragment.DeleteCallback;
import android.os.Bundle;

/** Card show Activity.
 *
 * This only contains a CardShowFragment.
 *
 * @see android.app.Activity
 */
public class CardShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_card_show);
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}