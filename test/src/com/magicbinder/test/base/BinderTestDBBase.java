/**************************************************************************
 * BinderTestDBBase.java, MagicBinder Android
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

import com.magicbinder.data.BinderSQLiteAdapter;
import com.magicbinder.entity.Binder;


import com.magicbinder.test.utils.*;

import junit.framework.Assert;

/** Binder database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit BinderTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class BinderTestDBBase extends TestDBBase {
    protected android.content.Context ctx;

    protected BinderSQLiteAdapter adapter;

    protected Binder entity;
    protected ArrayList<Binder> entities;
    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new BinderSQLiteAdapter(this.ctx);
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
            Binder binder = BinderUtils.generateRandom(this.ctx);

            result = (int)this.adapter.insert(binder);

            Assert.assertTrue(result >= 0);
        }
    }

    /** Test case Read Entity */
    @SmallTest
    public void testRead() {
        Binder result = null;
        if (this.entity != null) {
            result = this.adapter.getByID(this.entity.getId());

            BinderUtils.equals(this.entity, result);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testUpdate() {
        int result = -1;
        if (this.entity != null) {
            Binder binder = BinderUtils.generateRandom(this.ctx);
            binder.setId(this.entity.getId());

            result = (int) this.adapter.update(binder);

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
