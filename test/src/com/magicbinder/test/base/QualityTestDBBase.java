/**************************************************************************
 * QualityTestDBBase.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.test.base;

import java.util.ArrayList;

import android.test.suitebuilder.annotation.SmallTest;

import com.magicbinder.data.QualitySQLiteAdapter;
import com.magicbinder.entity.Quality;


import com.magicbinder.test.utils.*;

import junit.framework.Assert;

/** Quality database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit QualityTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class QualityTestDBBase extends TestDBBase {
    protected android.content.Context ctx;

    protected QualitySQLiteAdapter adapter;

    protected Quality entity;
    protected ArrayList<Quality> entities;
    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new QualitySQLiteAdapter(this.ctx);
        this.adapter.open();

    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        this.adapter.close();

        super.tearDown();
    }

    /** Test case Create Entity */
    @SmallTest
    public void testCreate() {
        int result = -1;
        if (this.entity != null) {
            Quality quality = QualityUtils.generateRandom(this.ctx);

            result = (int)this.adapter.insert(quality);

            Assert.assertTrue(result >= 0);
        }
    }

    /** Test case Read Entity */
    @SmallTest
    public void testRead() {
        Quality result = null;
        if (this.entity != null) {
            result = this.adapter.getByID(this.entity.getId());

            QualityUtils.equals(this.entity, result);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testUpdate() {
        int result = -1;
        if (this.entity != null) {
            Quality quality = QualityUtils.generateRandom(this.ctx);
            quality.setId(this.entity.getId());

            result = (int) this.adapter.update(quality);

            Assert.assertTrue(result >= 0);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testDelete() {
        int result = -1;
        if (this.entity != null) {
            result = (int) this.adapter.remove(this.entity.getId());
            Assert.assertTrue(result >= 0);
        }
    }
    
    /** Test the get all method. */
    @SmallTest
    public void testAll() {
        int result = this.adapter.getAll().size();
        int expectedSize = this.nbEntities;
        Assert.assertEquals(expectedSize, result);
    }
}
