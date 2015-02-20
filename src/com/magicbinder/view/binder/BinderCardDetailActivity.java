package com.magicbinder.view.binder;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.magicbinder.R;
import com.magicbinder.entity.Binder_Card;
import com.magicbinder.harmony.view.HarmonyFragmentActivity;

/**
 * BinderCardDetailActivity
 * @author Luis
 *
 */
public class BinderCardDetailActivity extends HarmonyFragmentActivity{
    /**
     * TAG GetExtra first message.
     */
    private static final String MESSAGE1 = "message1";
    /**
     * TAG GetExtra second message.
     */
    private static final String POSITION = "position";
    /**
     * Intent of this activity.
     */
    private Intent intent;
    /**
     * Array of binder_card.
     */
    private ArrayList<Binder_Card> binderCardList;
    /**
     * Create activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get and init fiels
        setContentView(R.layout.activity_card_detail);
        intent = getIntent();
        binderCardList = this.intent.getParcelableArrayListExtra(MESSAGE1);
        int position = this.intent.getIntExtra(POSITION, 0);
        ViewPager viewPager = (ViewPager) findViewById(R.id.cardDetailPager);
        BinderCardDetailAdapter cardDetailPagerAdapter=
        		new BinderCardDetailAdapter(getSupportFragmentManager(),binderCardList);
        viewPager.setAdapter(cardDetailPagerAdapter);
        viewPager.setCurrentItem(position, true);
    }

}
