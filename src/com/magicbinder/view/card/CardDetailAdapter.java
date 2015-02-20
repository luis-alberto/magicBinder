package com.magicbinder.view.card;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.magicbinder.entity.Card;

/**
 * CardDetailAdapter is a FragmentPagerAdapter.
 * @author Luis
 *
 */
public class CardDetailAdapter extends FragmentPagerAdapter{
	/**
	 * List of cards.
	 */
    private ArrayList<Card> listCards;
    
    /**
     * Construtor of CardDetailAdapter.
     * @param fragmnetManager .
     * @param listCards .
     */
    public CardDetailAdapter(FragmentManager fragmnetManager,ArrayList<Card> listCards) {
        // TODO Auto-generated constructor stub
        super(fragmnetManager);
        this.listCards = listCards;
    }
    /**
     * Get a card in indew position.
     */
    @Override
    public Fragment getItem(int index) {
        if (!this.listCards.isEmpty()){
            return new CardDetailFragment(this.listCards.get(index));
        }
        return null;
    }

    /**
     * Count numbers of cards in arraylist.
     */
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return this.listCards.size();
    }
}
