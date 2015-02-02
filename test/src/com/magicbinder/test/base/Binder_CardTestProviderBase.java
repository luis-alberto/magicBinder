/**************************************************************************
 * Binder_CardTestProviderBase.java, MagicBinder Android
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

import com.magicbinder.provider.Binder_CardProviderAdapter;
import com.magicbinder.provider.utils.Binder_CardProviderUtils;
import com.magicbinder.provider.contract.Binder_CardContract;

import com.magicbinder.data.Binder_CardSQLiteAdapter;

import com.magicbinder.entity.Binder_Card;


import java.util.ArrayList;
import com.magicbinder.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Binder_Card database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit Binder_CardTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class Binder_CardTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected Binder_CardSQLiteAdapter adapter;

    protected Binder_Card entity;
    protected ContentResolver provider;
    protected Binder_CardProviderUtils providerUtils;

    protected ArrayList<Binder_Card> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new Binder_CardSQLiteAdapter(this.ctx);

        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new Binder_CardProviderUtils(this.getContext());
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
            Binder_Card binder_Card = Binder_CardUtils.generateRandom(this.ctx);

            try {
                ContentValues values = Binder_CardContract.itemToContentValues(binder_Card);
                values.remove(Binder_CardContract.COL_ID);
                result = this.provider.insert(Binder_CardProviderAdapter.BINDER_CARD_URI, values);

            } catch (Exception e) {
                e.printStackTrace();
            }

            Assert.assertNotNull(result);
            Assert.assertTrue(Integer.parseInt(result.getPathSegments().get(1)) > 0);        
            
        }
    }

    /** Test case Read Entity */
    @SmallTest
    public void testRead() {
        Binder_Card result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        Binder_CardProviderAdapter.BINDER_CARD_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = Binder_CardContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Binder_CardUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Binder_Card> result = null;
        try {
            android.database.Cursor c = this.provider.query(Binder_CardProviderAdapter.BINDER_CARD_URI, this.adapter.getCols(), null, null, null);
            result = Binder_CardContract.cursorToItems(c);
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
            Binder_Card binder_Card = Binder_CardUtils.generateRandom(this.ctx);

            try {
                binder_Card.setId(this.entity.getId());

                ContentValues values = Binder_CardContract.itemToContentValues(binder_Card);
                result = this.provider.update(
                    Uri.parse(Binder_CardProviderAdapter.BINDER_CARD_URI
                        + "/"
                        + binder_Card.getId()),
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
            Binder_Card binder_Card = Binder_CardUtils.generateRandom(this.ctx);

            try {
                ContentValues values = Binder_CardContract.itemToContentValues(binder_Card);
                values.remove(Binder_CardContract.COL_ID);

                result = this.provider.update(Binder_CardProviderAdapter.BINDER_CARD_URI, values, null, null);
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
                        Uri.parse(Binder_CardProviderAdapter.BINDER_CARD_URI
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
                result = this.provider.delete(Binder_CardProviderAdapter.BINDER_CARD_URI, null, null);

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
        Binder_Card result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            Binder_CardUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Binder_Card> result = null;
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
            Binder_Card binder_Card = Binder_CardUtils.generateRandom(this.ctx);

            binder_Card.setId(this.entity.getId());
            result = this.providerUtils.update(binder_Card);

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
