package com.magicbinder.view.binder;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.magicbinder.entity.Binder_Card;
/**
 * BinderCardDetailAdapter is a FragmentPagerAdapter.
 * @author Luis
 *
 */
public class BinderCardDetailAdapter  extends FragmentPagerAdapter{
	/**
	 * Array of binder_card.
	 */
    private ArrayList<Binder_Card> binderCardList;
    
    /**
     * Contructor of BinderCardDetailAdapter.
     * @param fragmnetManager
     * @param binderCardList
     */
    public BinderCardDetailAdapter(FragmentManager fragmnetManager,ArrayList<Binder_Card> binderCardList) {
        // TODO Auto-generated constructor stub
        super(fragmnetManager);
        this.binderCardList = binderCardList;
    }
    /**
     * Get binde_card in index position.
     * @param index position in binder_card array.
     * @return BinderCardDetailFragment for a binder_card in a position.</br>
     * Null if no binder_card.
     */
    @Override
    public Fragment getItem(int index) {
        if (!this.binderCardList.isEmpty()){
            return new BinderCardDetailFragment(this.binderCardList.get(index));
        }
        return null;
    }

    /**
     * Count numbers of binder_cards.
     */
    @Override
    public int getCount() {
        return this.binderCardList.size();
    }
}