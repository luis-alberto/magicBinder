package com.magicbinder.view.card;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.magicbinder.R;
import com.magicbinder.entity.Card;
import com.magicbinder.harmony.view.HarmonyFragmentActivity;

/**
 * CardDetailActivity fragmentActivity.
 * @author Luis
 *
 */
public class CardDetailActivity extends HarmonyFragmentActivity {
	/**
	 * Tag first putExtra message.
	 */
    private static final String MESSAGE1= "message1";
    /**
     * Tag second putExtra message.
     */
    private static final String POSITION ="position";
	/**
	 * Intent of activity.
	 */
    private Intent intent;

    /**
     * List of cards.
     */
    private ArrayList<Card> listCards;
    /**
     * Create activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);
        //set messages.
        this.intent = this.getIntent();
        listCards =this.intent.getParcelableArrayListExtra(MESSAGE1);
        int position = this.intent.getIntExtra(POSITION, 0);
        //Creating view.
        ViewPager viewPager = (ViewPager) findViewById(R.id.cardDetailPager);
        CardDetailAdapter cardDetailPagerAdapter=new CardDetailAdapter(getSupportFragmentManager(),listCards);
        viewPager.setAdapter(cardDetailPagerAdapter);
        viewPager.setCurrentItem(position, true);
    }
}
