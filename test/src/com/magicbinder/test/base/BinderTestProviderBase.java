/**************************************************************************
 * BinderTestProviderBase.java, MagicBinder Android
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

import com.magicbinder.provider.BinderProviderAdapter;
import com.magicbinder.provider.utils.BinderProviderUtils;
import com.magicbinder.provider.contract.BinderContract;

import com.magicbinder.data.BinderSQLiteAdapter;

import com.magicbinder.entity.Binder;
import com.magicbinder.entity.Binder_Card;


import java.util.ArrayList;
import com.magicbinder.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Binder database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit BinderTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class BinderTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected BinderSQLiteAdapter adapter;

    protected Binder entity;
    protected ContentResolver provider;
    protected BinderProviderUtils providerUtils;

    protected ArrayList<Binder> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new BinderSQLiteAdapter(this.ctx);

        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new BinderProviderUtils(this.getContext());
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
            Binder binder = BinderUtils.generateRandom(this.ctx);

            try {
                ContentValues values = BinderContract.itemToContentValues(binder);
                values.remove(BinderContract.COL_ID);
                result = this.provider.insert(BinderProviderAdapter.BINDER_URI, values);

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
        Binder result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        BinderProviderAdapter.BINDER_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = BinderContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            BinderUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Binder> result = null;
        try {
            android.database.Cursor c = this.provider.query(BinderProviderAdapter.BINDER_URI, this.adapter.getCols(), null, null, null);
            result = BinderContract.cursorToItems(c);
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
            Binder binder = BinderUtils.generateRandom(this.ctx);

            try {
                binder.setId(this.entity.getId());
                if (this.entity.getBinders_cards_binder() != null) {
                    binder.getBinders_cards_binder().addAll(this.entity.getBinders_cards_binder());
                }

                ContentValues values = BinderContract.itemToContentValues(binder);
                result = this.provider.update(
                    Uri.parse(BinderProviderAdapter.BINDER_URI
                        + "/"
                        + binder.getId()),
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
            Binder binder = BinderUtils.generateRandom(this.ctx);

            try {
                ContentValues values = BinderContract.itemToContentValues(binder);
                values.remove(BinderContract.COL_ID);

                result = this.provider.update(BinderProviderAdapter.BINDER_URI, values, null, null);
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
                        Uri.parse(BinderProviderAdapter.BINDER_URI
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
                result = this.provider.delete(BinderProviderAdapter.BINDER_URI, null, null);

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
        Binder result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            BinderUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Binder> result = null;
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
            Binder binder = BinderUtils.generateRandom(this.ctx);

            binder.setId(this.entity.getId());
            if (this.entity.getBinders_cards_binder() != null) {
                for (Binder_Card binders_cards_binder : this.entity.getBinders_cards_binder()) {
                    boolean found = false;
                    for (Binder_Card binders_cards_binder2 : binder.getBinders_cards_binder()) {
                        if (binders_cards_binder.getId() == binders_cards_binder2.getId() ) {
                            found = true;
                            break;
                        }
                    }                    
                    if(!found) {
                        binder.getBinders_cards_binder().add(binders_cards_binder);
                    }
                }
            }
            result = this.providerUtils.update(binder);

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
