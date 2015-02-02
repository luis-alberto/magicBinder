/**************************************************************************
 * ColorTestProviderBase.java, MagicBinder Android
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

import com.magicbinder.provider.ColorProviderAdapter;
import com.magicbinder.provider.utils.ColorProviderUtils;
import com.magicbinder.provider.contract.ColorContract;

import com.magicbinder.data.ColorSQLiteAdapter;

import com.magicbinder.entity.Color;
import com.magicbinder.entity.Card;


import java.util.ArrayList;
import com.magicbinder.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Color database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit ColorTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ColorTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected ColorSQLiteAdapter adapter;

    protected Color entity;
    protected ContentResolver provider;
    protected ColorProviderUtils providerUtils;

    protected ArrayList<Color> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new ColorSQLiteAdapter(this.ctx);

        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new ColorProviderUtils(this.getContext());
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
            Color color = ColorUtils.generateRandom(this.ctx);

            try {
                ContentValues values = ColorContract.itemToContentValues(color);
                values.remove(ColorContract.COL_ID);
                result = this.provider.insert(ColorProviderAdapter.COLOR_URI, values);

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
        Color result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        ColorProviderAdapter.COLOR_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = ColorContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            ColorUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Color> result = null;
        try {
            android.database.Cursor c = this.provider.query(ColorProviderAdapter.COLOR_URI, this.adapter.getCols(), null, null, null);
            result = ColorContract.cursorToItems(c);
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
            Color color = ColorUtils.generateRandom(this.ctx);

            try {
                color.setId(this.entity.getId());
                if (this.entity.getCards() != null) {
                    color.getCards().addAll(this.entity.getCards());
                }

                ContentValues values = ColorContract.itemToContentValues(color);
                result = this.provider.update(
                    Uri.parse(ColorProviderAdapter.COLOR_URI
                        + "/"
                        + color.getId()),
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
            Color color = ColorUtils.generateRandom(this.ctx);

            try {
                ContentValues values = ColorContract.itemToContentValues(color);
                values.remove(ColorContract.COL_ID);

                result = this.provider.update(ColorProviderAdapter.COLOR_URI, values, null, null);
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
                        Uri.parse(ColorProviderAdapter.COLOR_URI
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
                result = this.provider.delete(ColorProviderAdapter.COLOR_URI, null, null);

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
        Color result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            ColorUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Color> result = null;
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
            Color color = ColorUtils.generateRandom(this.ctx);

            color.setId(this.entity.getId());
            if (this.entity.getCards() != null) {
                for (Card cards : this.entity.getCards()) {
                    boolean found = false;
                    for (Card cards2 : color.getCards()) {
                        if (cards.getId() == cards2.getId() ) {
                            found = true;
                            break;
                        }
                    }                    
                    if(!found) {
                        color.getCards().add(cards);
                    }
                }
            }
            result = this.providerUtils.update(color);

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
