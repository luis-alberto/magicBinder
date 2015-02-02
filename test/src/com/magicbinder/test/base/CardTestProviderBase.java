/**************************************************************************
 * CardTestProviderBase.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.test.base;

import android.test.suitebuilder.annotation.SmallTest;

import com.magicbinder.provider.CardProviderAdapter;
import com.magicbinder.provider.utils.CardProviderUtils;
import com.magicbinder.provider.contract.CardContract;

import com.magicbinder.data.CardSQLiteAdapter;

import com.magicbinder.entity.Card;
import com.magicbinder.entity.Color;
import com.magicbinder.entity.Binder_Card;


import java.util.ArrayList;
import com.magicbinder.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Card database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit CardTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class CardTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected CardSQLiteAdapter adapter;

    protected Card entity;
    protected ContentResolver provider;
    protected CardProviderUtils providerUtils;

    protected ArrayList<Card> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new CardSQLiteAdapter(this.ctx);

        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new CardProviderUtils(this.getContext());
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /********** Direct Provider calls. *******/

    /** Test case Create Entity */
    @SmallTest
    public void testCreate() {
        Uri result = null;
        if (this.entity != null) {
            Card card = CardUtils.generateRandom(this.ctx);

            try {
                ContentValues values = CardContract.itemToContentValues(card);
                result = this.provider.insert(CardProviderAdapter.CARD_URI, values);

            } catch (Exception e) {
                e.printStackTrace();
            }

            Assert.assertNotNull(result);
            Assert.assertTrue(Integer.parseInt(result.getPathSegments().get(1)) == card.getId());
            
        }
    }

    /** Test case Read Entity */
    @SmallTest
    public void testRead() {
        Card result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        CardProviderAdapter.CARD_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = CardContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            CardUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Card> result = null;
        try {
            android.database.Cursor c = this.provider.query(CardProviderAdapter.CARD_URI, this.adapter.getCols(), null, null, null);
            result = CardContract.cursorToItems(c);
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(result);
        if (result != null) {
            Assert.assertEquals(result.size(), this.nbEntities);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testUpdate() {
        int result = -1;
        if (this.entity != null) {
            Card card = CardUtils.generateRandom(this.ctx);

            try {
                card.setId(this.entity.getId());
                if (this.entity.getColors() != null) {
                    card.getColors().addAll(this.entity.getColors());
                }
                if (this.entity.getBinders_cards_card() != null) {
                    card.getBinders_cards_card().addAll(this.entity.getBinders_cards_card());
                }

                ContentValues values = CardContract.itemToContentValues(card);
                result = this.provider.update(
                    Uri.parse(CardProviderAdapter.CARD_URI
                        + "/"
                        + card.getId()),
                    values,
                    null,
                    null);

            } catch (Exception e) {
                e.printStackTrace();
            }

            Assert.assertTrue(result > 0);
        }
    }

    /** Test case UpdateAll Entity */
    @SmallTest
    public void testUpdateAll() {
        int result = -1;
        if (this.entities.size() > 0) {
            Card card = CardUtils.generateRandom(this.ctx);

            try {
                ContentValues values = CardContract.itemToContentValues(card);
                values.remove(CardContract.COL_ID);

                result = this.provider.update(CardProviderAdapter.CARD_URI, values, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Assert.assertEquals(result, this.nbEntities);
        }
    }

    /** Test case Delete Entity */
    @SmallTest
    public void testDelete() {
        int result = -1;
        if (this.entity != null) {
            try {
                result = this.provider.delete(
                        Uri.parse(CardProviderAdapter.CARD_URI
                            + "/" 
                            + this.entity.getId()),
                        null,
                        null);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Assert.assertTrue(result >= 0);
        }

    }

    /** Test case DeleteAll Entity */
    @SmallTest
    public void testDeleteAll() {
        int result = -1;
        if (this.entities.size() > 0) {

            try {
                result = this.provider.delete(CardProviderAdapter.CARD_URI, null, null);

            } catch (Exception e) {
                e.printStackTrace();
            }

            Assert.assertEquals(result, this.nbEntities);
        }
    }

    /****** Provider Utils calls ********/

    /** Test case Read Entity by provider utils. */
    @SmallTest
    public void testUtilsRead() {
        Card result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            CardUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Card> result = null;
        result = this.providerUtils.queryAll();

        Assert.assertNotNull(result);
        if (result != null) {
            Assert.assertEquals(result.size(), this.nbEntities);
        }
    }

    /** Test case Update Entity by provider utils. */
    @SmallTest
    public void testUtilsUpdate() {
        int result = -1;
        if (this.entity != null) {
            Card card = CardUtils.generateRandom(this.ctx);

            card.setId(this.entity.getId());
            if (this.entity.getColors() != null) {
                for (Color colors : this.entity.getColors()) {
                    boolean found = false;
                    for (Color colors2 : card.getColors()) {
                        if (colors.getId() == colors2.getId() ) {
                            found = true;
                            break;
                        }
                    }                    
                    if(!found) {
                        card.getColors().add(colors);
                    }
                }
            }
            if (this.entity.getBinders_cards_card() != null) {
                for (Binder_Card binders_cards_card : this.entity.getBinders_cards_card()) {
                    boolean found = false;
                    for (Binder_Card binders_cards_card2 : card.getBinders_cards_card()) {
                        if (binders_cards_card.getId() == binders_cards_card2.getId() ) {
                            found = true;
                            break;
                        }
                    }                    
                    if(!found) {
                        card.getBinders_cards_card().add(binders_cards_card);
                    }
                }
            }
            result = this.providerUtils.update(card);

            Assert.assertTrue(result > 0);
        }
    }


    /** Test case Delete Entity by provider utils. */
    @SmallTest
    public void testUtilsDelete() {
        int result = -1;
        if (this.entity != null) {
            result = this.providerUtils.delete(this.entity);
            Assert.assertTrue(result >= 0);
        }

    }
}
