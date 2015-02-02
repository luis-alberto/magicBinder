/**************************************************************************
 * CardUtilsBase.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.test.utils.base;


import junit.framework.Assert;
import com.magicbinder.entity.Card;



import com.magicbinder.test.utils.TestUtils;
import com.magicbinder.entity.Color;
import com.magicbinder.test.utils.ColorUtils;
import com.magicbinder.entity.Binder_Card;
import com.magicbinder.test.utils.Binder_CardUtils;

import java.util.ArrayList;

public abstract class CardUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Card generateRandom(android.content.Context ctx){
        Card card = new Card();

        card.setId(TestUtils.generateRandomInt(0,100) + 1);
        card.setName("name_"+TestUtils.generateRandomString(10));
        card.setImage("image_"+TestUtils.generateRandomString(10));
        card.setConvertedManaCost(TestUtils.generateRandomInt(0,100));
        card.setTypeCard("typeCard_"+TestUtils.generateRandomString(10));
        card.setRarity("rarity_"+TestUtils.generateRandomString(10));
        card.setCardSetId("cardSetId_"+TestUtils.generateRandomString(10));
        ArrayList<Color> relatedColorss = new ArrayList<Color>();
        relatedColorss.add(ColorUtils.generateRandom(ctx));
        card.setColors(relatedColorss);
        ArrayList<Binder_Card> relatedBinders_cards_cards = new ArrayList<Binder_Card>();
        relatedBinders_cards_cards.add(Binder_CardUtils.generateRandom(ctx));
        card.setBinders_cards_card(relatedBinders_cards_cards);

        return card;
    }

    public static boolean equals(Card card1,
            Card card2){
        return equals(card1, card2, true);
    }
    
    public static boolean equals(Card card1,
            Card card2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(card1);
        Assert.assertNotNull(card2);
        if (card1!=null && card2 !=null){
            Assert.assertEquals(card1.getId(), card2.getId());
            Assert.assertEquals(card1.getName(), card2.getName());
            Assert.assertEquals(card1.getImage(), card2.getImage());
            Assert.assertEquals(card1.getConvertedManaCost(), card2.getConvertedManaCost());
            Assert.assertEquals(card1.getTypeCard(), card2.getTypeCard());
            Assert.assertEquals(card1.getRarity(), card2.getRarity());
            Assert.assertEquals(card1.getCardSetId(), card2.getCardSetId());
            if (card1.getColors() != null
                    && card2.getColors() != null) {
                Assert.assertEquals(card1.getColors().size(),
                    card2.getColors().size());
                if (checkRecursiveId) {
                    for (Color colors1 : card1.getColors()) {
                        boolean found = false;
                        for (Color colors2 : card2.getColors()) {
                            if (colors1.getId() == colors2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated colors (id = %s) in Card (id = %s)",
                                        colors1.getId(),
                                        card1.getId()),
                                found);
                    }
                }
            }
            if (card1.getBinders_cards_card() != null
                    && card2.getBinders_cards_card() != null) {
                Assert.assertEquals(card1.getBinders_cards_card().size(),
                    card2.getBinders_cards_card().size());
                if (checkRecursiveId) {
                    for (Binder_Card binders_cards_card1 : card1.getBinders_cards_card()) {
                        boolean found = false;
                        for (Binder_Card binders_cards_card2 : card2.getBinders_cards_card()) {
                            if (binders_cards_card1.getId() == binders_cards_card2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated binders_cards_card (id = %s) in Card (id = %s)",
                                        binders_cards_card1.getId(),
                                        card1.getId()),
                                found);
                    }
                }
            }
        }

        return ret;
    }
}

