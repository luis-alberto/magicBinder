/**************************************************************************
 * TestContextMock.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.test.base;



import com.magicbinder.provider.MagicBinderProvider;



import com.magicbinder.data.MagicBinderSQLiteOpenHelper;


import android.content.BroadcastReceiver;
import android.content.ContentProvider;

import android.content.Intent;
import android.content.IntentFilter;

import android.test.AndroidTestCase;
import android.test.IsolatedContext;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContentResolver;


/** android.content.Context mock for tests.<br/>
 * <b><i>This class will be overwrited whenever
 * you regenerate the project with Harmony.</i></b>
 */
public class TestContextMock {
    private final static String CONTEXT_PREFIX = "test.";
    private final static String PROVIDER_AUTHORITY =
                    "com.magicbinder.provider";
    private final static Class<? extends ContentProvider> PROVIDER_CLASS =
                    MagicBinderProvider.class;

    private static android.content.Context context = null;
    private AndroidTestCase androidTestCase;
    private android.content.Context baseContext;
    
    public TestContextMock(AndroidTestCase androidTestCase) {
        this.androidTestCase = androidTestCase;
    }

    /**
     * Get the original context
     * @return unmocked android.content.Context
     */
    protected android.content.Context getBaseContext() {
        return this.baseContext;
    }

    /**
     * Get the mock for ContentResolver
     * @return MockContentResolver
     */
    protected MockContentResolver getMockContentResolver() {
        return new MockContentResolver();
    }

    /**
     * Get the mock for android.content.Context
     * @return MockContext
     */
    protected android.content.Context getMockContext() {
            return this.androidTestCase.getContext();
    }

    /**
     * Initialize the mock android.content.Context
     * @throws Exception
     */
    protected void setMockContext() throws Exception {
        if (this.baseContext == null) {
            this.baseContext = this.androidTestCase.getContext();
        }

        if (context == null) {
            ContentProvider provider = PROVIDER_CLASS.newInstance();
            MockContentResolver resolver = this.getMockContentResolver();
    
            RenamingDelegatingContext targetContextWrapper
                = new RenamingDelegatingContext(
                    // The context that most methods are delegated to:
                    this.getMockContext(),
                    // The context that file methods are delegated to:
                    this.baseContext,
                    // Prefix database
                    CONTEXT_PREFIX);
    
            context = new IsolatedContext(
                    resolver,
                    targetContextWrapper) {
                        @Override
                        public Object getSystemService(String name) {
                            return TestContextMock.this
                                    .baseContext.getSystemService(name);
                        }
                        
                        @Override
                        public void sendOrderedBroadcast(
                                Intent intent, String receiverPermission) {
                            TestContextMock.this.baseContext
                                    .sendOrderedBroadcast(
                                            intent, receiverPermission);
                        }
                        
                        @Override
                        public Intent registerReceiver(
                                BroadcastReceiver receiver,
                                IntentFilter filter) {
                            return TestContextMock.this.baseContext
                                    .registerReceiver(receiver, filter);
                        }
                    };
    
            provider.attachInfo(context, null);
            resolver.addProvider(PROVIDER_AUTHORITY, provider);
        }

        this.androidTestCase.setContext(context);
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        MagicBinderSQLiteOpenHelper.isJUnit = true;
        this.setMockContext();

    }
}
