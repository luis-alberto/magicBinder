/**************************************************************************
 * QualityUtilsBase.java, MagicBinder Android
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
import com.magicbinder.entity.Quality;



import com.magicbinder.test.utils.TestUtils;
import com.magicbinder.entity.Binder_Card;
import com.magicbinder.test.utils.Binder_CardUtils;

import java.util.ArrayList;

public abstract class QualityUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Quality generateRandom(android.content.Context ctx){
        Quality quality = new Quality();

        quality.setId(TestUtils.generateRandomInt(0,100) + 1);
        quality.setLabel("label_"+TestUtils.generateRandomString(10));
        ArrayList<Binder_Card> relatedBinders_cards_qualitys = new ArrayList<Binder_Card>();
        relatedBinders_cards_qualitys.add(Binder_CardUtils.generateRandom(ctx));
        quality.setBinders_cards_quality(relatedBinders_cards_qualitys);

        return quality;
    }

    public static boolean equals(Quality quality1,
            Quality quality2){
        return equals(quality1, quality2, true);
    }
    
    public static boolean equals(Quality quality1,
            Quality quality2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(quality1);
        Assert.assertNotNull(quality2);
        if (quality1!=null && quality2 !=null){
            Assert.assertEquals(quality1.getId(), quality2.getId());
            Assert.assertEquals(quality1.getLabel(), quality2.getLabel());
            if (quality1.getBinders_cards_quality() != null
                    && quality2.getBinders_cards_quality() != null) {
                Assert.assertEquals(quality1.getBinders_cards_quality().size(),
                    quality2.getBinders_cards_quality().size());
                if (checkRecursiveId) {
                    for (Binder_Card binders_cards_quality1 : quality1.getBinders_cards_quality()) {
                        boolean found = false;
                        for (Binder_Card binders_cards_quality2 : quality2.getBinders_cards_quality()) {
                            if (binders_cards_quality1.getId() == binders_cards_quality2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated binders_cards_quality (id = %s) in Quality (id = %s)",
                                        binders_cards_quality1.getId(),
                                        quality1.getId()),
                                found);
                    }
                }
            }
        }

        return ret;
    }
}

