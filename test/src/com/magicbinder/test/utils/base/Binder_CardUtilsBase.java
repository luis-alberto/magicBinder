/**************************************************************************
 * Binder_CardUtilsBase.java, MagicBinder Android
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
import com.magicbinder.entity.Binder_Card;



import com.magicbinder.test.utils.TestUtils;

import com.magicbinder.test.utils.CardUtils;

import com.magicbinder.test.utils.BinderUtils;

import com.magicbinder.test.utils.QualityUtils;


public abstract class Binder_CardUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Binder_Card generateRandom(android.content.Context ctx){
        Binder_Card binder_Card = new Binder_Card();

        binder_Card.setId(TestUtils.generateRandomInt(0,100) + 1);
        binder_Card.setQuantity(TestUtils.generateRandomInt(0,100));
        binder_Card.setCard(CardUtils.generateRandom(ctx));
        binder_Card.setBinder(BinderUtils.generateRandom(ctx));
        binder_Card.setQuality(QualityUtils.generateRandom(ctx));

        return binder_Card;
    }

    public static boolean equals(Binder_Card binder_Card1,
            Binder_Card binder_Card2){
        return equals(binder_Card1, binder_Card2, true);
    }
    
    public static boolean equals(Binder_Card binder_Card1,
            Binder_Card binder_Card2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(binder_Card1);
        Assert.assertNotNull(binder_Card2);
        if (binder_Card1!=null && binder_Card2 !=null){
            Assert.assertEquals(binder_Card1.getId(), binder_Card2.getId());
            Assert.assertEquals(binder_Card1.getQuantity(), binder_Card2.getQuantity());
            if (binder_Card1.getCard() != null
                    && binder_Card2.getCard() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(binder_Card1.getCard().getId(),
                            binder_Card2.getCard().getId());
                }
            }
            if (binder_Card1.getBinder() != null
                    && binder_Card2.getBinder() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(binder_Card1.getBinder().getId(),
                            binder_Card2.getBinder().getId());
                }
            }
            if (binder_Card1.getQuality() != null
                    && binder_Card2.getQuality() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(binder_Card1.getQuality().getId(),
                            binder_Card2.getQuality().getId());
                }
            }
        }

        return ret;
    }
}

