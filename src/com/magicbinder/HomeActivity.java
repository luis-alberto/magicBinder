/**************************************************************************
 * HomeActivity.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder;

import com.magicbinder.harmony.view.HarmonyFragmentActivity;
import com.magicbinder.view.binder.BinderListActivity;
import com.magicbinder.view.quality.QualityListActivity;
import com.magicbinder.view.color.ColorListActivity;
import com.magicbinder.view.binder_card.Binder_CardListActivity;
import com.magicbinder.view.card.CardListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Home Activity.
 * This is from where you can access to your entities activities by default.
 * BEWARE : This class is regenerated with orm:generate:crud. Don't modify it.
 * @see android.app.Activity
 */
public class HomeActivity extends HarmonyFragmentActivity 
        implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState)    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);
        
        this.initButtons();
    }

    /**
     * Initialize the buttons click listeners.
     */
    private void initButtons() {
        this.findViewById(R.id.binder_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.quality_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.color_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.binder_card_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.card_list_button)
                        .setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.binder_list_button:
                intent = new Intent(this,
                        BinderListActivity.class);
                break;

            case R.id.quality_list_button:
                intent = new Intent(this,
                        QualityListActivity.class);
                break;

            case R.id.color_list_button:
                intent = new Intent(this,
                        ColorListActivity.class);
                break;

            case R.id.binder_card_list_button:
                intent = new Intent(this,
                        Binder_CardListActivity.class);
                break;

            case R.id.card_list_button:
                intent = new Intent(this,
                        CardListActivity.class);
                break;

            default:
                intent = null;
                break;
        }

        if (intent != null) {
            this.startActivity(intent);
        }
    }

}
