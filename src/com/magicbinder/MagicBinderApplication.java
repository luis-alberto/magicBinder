/**************************************************************************
 * MagicBinderApplication.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.magicbinder.data.BinderSQLiteAdapter;
import com.magicbinder.data.ColorSQLiteAdapter;
import com.magicbinder.data.QualitySQLiteAdapter;
import com.magicbinder.entity.Binder;
import com.magicbinder.entity.Color;
import com.magicbinder.entity.Quality;
/** 
 * Custom MagicBinder Application context. 
 *
 * Feel free to modify this class.
 */
public class MagicBinderApplication extends MagicBinderApplicationBase {
    // Override or Create your custom method for your application
    // this file is just generate at first time, and never override...

    // on release mode use BuildConfig.DEBUG;
    /**
     * DEBUG.
     */
    public static final boolean DEBUG = true;
    //tags
    private static final String PREFS_NAME = "MagicBinderPrefsFile";
    private static final String FIRST_TIME = "firstTime";
    private static final String BINDER_NB = "BINDER%d";
    private static final String[] COLORS = {"None","blue","red","green",
                                            "black","white"};
    private static final String[] QUALITYS = {"M","NM","EX","F","PL","G","P"};

    private static MagicBinderApplication magicBinderInstance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
 
    @Override
    public void onCreate() {
        super.onCreate();
        initSettings();
        magicBinderInstance = this;
        requestQueue = Volley.newRequestQueue(this);
        imageLoader = new ImageLoader(this.requestQueue, new ImageLoader.ImageCache() {
             
            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(10);
     
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
     
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }
        });
    }
     
    public static MagicBinderApplication getInstance(){
        return magicBinderInstance;
    }
    
    public RequestQueue getRequestQueue(){
        return requestQueue;
    }
  
    public ImageLoader getImageLoader(){
        return imageLoader;
    } 
    /**
     * Init Settings for application.
     */
    private void initSettings(){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if(settings.getBoolean(FIRST_TIME, true)){
            initBinders();
            initColors();
            initQualitys();
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(FIRST_TIME, false);
            editor.commit();
        }
    }
    /**
     * Init Binders
     */
    private void initBinders(){
        BinderSQLiteAdapter binderAdapter = new BinderSQLiteAdapter(this);
        binderAdapter.open();
        Binder binder = new Binder();
        for (int i = 1; i <= 3; i++) {
            binder.setName(String.format(BINDER_NB, i));
            binderAdapter.insert(binder);
        }
        binderAdapter.close();
    }
    /**
     * Init Colors
     */
    private void initColors(){
        ColorSQLiteAdapter colorAdapter = new ColorSQLiteAdapter(this);
        colorAdapter.open();
        Color color = new Color();
        for (int i = 0; i < COLORS.length; i++) {
            color.setLabel(COLORS[i]);
            colorAdapter.insert(color);
        }
        colorAdapter.close();
    }
    /**
     * Init Qualitys
     */
    private void initQualitys(){
        QualitySQLiteAdapter qualityAdapter = new QualitySQLiteAdapter(this);
        qualityAdapter.open();
        Quality quality = new Quality();
        for (int i = 0; i < QUALITYS.length; i++) {
            quality.setLabel(QUALITYS[i]);
            qualityAdapter.insert(quality);
        }
        qualityAdapter.close();
    }
}
