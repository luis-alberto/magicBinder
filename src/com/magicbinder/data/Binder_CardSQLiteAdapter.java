/**************************************************************************
 * Binder_CardSQLiteAdapter.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.data;

import java.util.ArrayList;

import com.magicbinder.data.base.Binder_CardSQLiteAdapterBase;
import com.magicbinder.entity.Binder;
import com.magicbinder.entity.Binder_Card;
import com.magicbinder.entity.Card;
import com.magicbinder.entity.Quality;


/**
 * Binder_Card adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class Binder_CardSQLiteAdapter extends Binder_CardSQLiteAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public Binder_CardSQLiteAdapter(final android.content.Context ctx) {
        super(ctx);
    }

    /**
     * Get Binder_Card with same binder,card and quality sent.
     * @param binder of Binder_Card.
     * @param card of Binder_Card.
     * @param quality of Binder_Card.
     * @return Binder_Card selected.
     */
    public Binder_Card getBinderCardByBinderCardQuality(Binder binder,Card card,Quality quality) {
        ArrayList<Binder_Card> bindersCards = this.getAll();
        Binder_Card binderCardReturn = null;
        for (Binder_Card binderCard : bindersCards) {
            if(binderCard.getBinder().equalsBinder(binder)
                    && binderCard.getCard().equalsCard(card)
                    && binderCard.getQuality().equalsQuality(quality)){
                binderCardReturn= binderCard;
            }
        }
        return binderCardReturn;
    }

    /**
     * Get all Binder_Card with send Binder.
     * @param binder in Binder_Card.
     * @return ArrayList<Binder_Card> with Binder sent.
     */
    public ArrayList<Binder_Card> getAllBinderCardsByBinder(Binder binder){
        ArrayList<Binder_Card> binderCardList = new ArrayList<Binder_Card>();
        ArrayList<Binder_Card> bindersCards = this.getAll();
        for (Binder_Card binderCard : bindersCards) {
            if(binderCard.getBinder().equalsBinder(binder)){
                binderCardList.add(binderCard);
            }
        }
        return binderCardList;
    }
}
