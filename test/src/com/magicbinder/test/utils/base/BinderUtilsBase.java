/**************************************************************************
 * BinderUtilsBase.java, MagicBinder Android
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
import com.magicbinder.entity.Binder;



import com.magicbinder.test.utils.TestUtils;
import com.magicbinder.entity.Binder_Card;
import com.magicbinder.test.utils.Binder_CardUtils;

import java.util.ArrayList;

public abstract class BinderUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Binder generateRandom(android.content.Context ctx){
        Binder binder = new Binder();

        binder.setId(TestUtils.generateRandomInt(0,100) + 1);
        binder.setName("name_"+TestUtils.generateRandomString(10));
        ArrayList<Binder_Card> relatedBinders_cards_binders = new ArrayList<Binder_Card>();
        relatedBinders_cards_binders.add(Binder_CardUtils.generateRandom(ctx));
        binder.setBinders_cards_binder(relatedBinders_cards_binders);

        return binder;
    }

    public static boolean equals(Binder binder1,
            Binder binder2){
        return equals(binder1, binder2, true);
    }
    
    public static boolean equals(Binder binder1,
            Binder binder2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(binder1);
        Assert.assertNotNull(binder2);
        if (binder1!=null && binder2 !=null){
            Assert.assertEquals(binder1.getId(), binder2.getId());
            Assert.assertEquals(binder1.getName(), binder2.getName());
            if (binder1.getBinders_cards_binder() != null
                    && binder2.getBinders_cards_binder() != null) {
                Assert.assertEquals(binder1.getBinders_cards_binder().size(),
                    binder2.getBinders_cards_binder().size());
                if (checkRecursiveId) {
                    for (Binder_Card binders_cards_binder1 : binder1.getBinders_cards_binder()) {
                        boolean found = false;
                        for (Binder_Card binders_cards_binder2 : binder2.getBinders_cards_binder()) {
                            if (binders_cards_binder1.getId() == binders_cards_binder2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated binders_cards_binder (id = %s) in Binder (id = %s)",
                                        binders_cards_binder1.getId(),
                                        binder1.getId()),
                                found);
                    }
                }
            }
        }

        return ret;
    }
}

