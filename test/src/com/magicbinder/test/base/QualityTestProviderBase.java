/**************************************************************************
 * QualityTestProviderBase.java, MagicBinder Android
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

import com.magicbinder.provider.QualityProviderAdapter;
import com.magicbinder.provider.utils.QualityProviderUtils;
import com.magicbinder.provider.contract.QualityContract;

import com.magicbinder.data.QualitySQLiteAdapter;

import com.magicbinder.entity.Quality;
import com.magicbinder.entity.Binder_Card;


import java.util.ArrayList;
import com.magicbinder.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Quality database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit QualityTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class QualityTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected QualitySQLiteAdapter adapter;

    protected Quality entity;
    protected ContentResolver provider;
    protected QualityProviderUtils providerUtils;

    protected ArrayList<Quality> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new QualitySQLiteAdapter(this.ctx);

        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new QualityProviderUtils(this.getContext());
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
            Quality quality = QualityUtils.generateRandom(this.ctx);

            try {
                ContentValues values = QualityContract.itemToContentValues(quality);
                values.remove(QualityContract.COL_ID);
                result = this.provider.insert(QualityProviderAdapter.QUALITY_URI, values);

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
        Quality result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        QualityProviderAdapter.QUALITY_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = QualityContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            QualityUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Quality> result = null;
        try {
            android.database.Cursor c = this.provider.query(QualityProviderAdapter.QUALITY_URI, this.adapter.getCols(), null, null, null);
            result = QualityContract.cursorToItems(c);
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
            Quality quality = QualityUtils.generateRandom(this.ctx);

            try {
                quality.setId(this.entity.getId());
                if (this.entity.getBinders_cards_quality() != null) {
                    quality.getBinders_cards_quality().addAll(this.entity.getBinders_cards_quality());
                }

                ContentValues values = QualityContract.itemToContentValues(quality);
                result = this.provider.update(
                    Uri.parse(QualityProviderAdapter.QUALITY_URI
                        + "/"
                        + quality.getId()),
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
            Quality quality = QualityUtils.generateRandom(this.ctx);

            try {
                ContentValues values = QualityContract.itemToContentValues(quality);
                values.remove(QualityContract.COL_ID);

                result = this.provider.update(QualityProviderAdapter.QUALITY_URI, values, null, null);
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
                        Uri.parse(QualityProviderAdapter.QUALITY_URI
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
                result = this.provider.delete(QualityProviderAdapter.QUALITY_URI, null, null);

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
        Quality result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            QualityUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Quality> result = null;
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
            Quality quality = QualityUtils.generateRandom(this.ctx);

            quality.setId(this.entity.getId());
            if (this.entity.getBinders_cards_quality() != null) {
                for (Binder_Card binders_cards_quality : this.entity.getBinders_cards_quality()) {
                    boolean found = false;
                    for (Binder_Card binders_cards_quality2 : quality.getBinders_cards_quality()) {
                        if (binders_cards_quality.getId() == binders_cards_quality2.getId() ) {
                            found = true;
                            break;
                        }
                    }                    
                    if(!found) {
                        quality.getBinders_cards_quality().add(binders_cards_quality);
                    }
                }
            }
            result = this.providerUtils.update(quality);

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
