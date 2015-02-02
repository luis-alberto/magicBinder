/**************************************************************************
 * ColorUtilsBase.java, MagicBinder Android
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
import com.magicbinder.entity.Color;



import com.magicbinder.test.utils.TestUtils;
import com.magicbinder.entity.Card;
import com.magicbinder.test.utils.CardUtils;

import java.util.ArrayList;

public abstract class ColorUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Color generateRandom(android.content.Context ctx){
        Color color = new Color();

        color.setId(TestUtils.generateRandomInt(0,100) + 1);
        color.setLabel("label_"+TestUtils.generateRandomString(10));
        ArrayList<Card> relatedCardss = new ArrayList<Card>();
        relatedCardss.add(CardUtils.generateRandom(ctx));
        color.setCards(relatedCardss);

        return color;
    }

    public static boolean equals(Color color1,
            Color color2){
        return equals(color1, color2, true);
    }
    
    public static boolean equals(Color color1,
            Color color2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(color1);
        Assert.assertNotNull(color2);
        if (color1!=null && color2 !=null){
            Assert.assertEquals(color1.getId(), color2.getId());
            Assert.assertEquals(color1.getLabel(), color2.getLabel());
            if (color1.getCards() != null
                    && color2.getCards() != null) {
                Assert.assertEquals(color1.getCards().size(),
                    color2.getCards().size());
                if (checkRecursiveId) {
                    for (Card cards1 : color1.getCards()) {
                        boolean found = false;
                        for (Card cards2 : color2.getCards()) {
                            if (cards1.getId() == cards2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated cards (id = %s) in Color (id = %s)",
                                        cards1.getId(),
                                        color1.getId()),
                                found);
                    }
                }
            }
        }

        return ret;
    }
}

