/**************************************************************************
 * MagicBinderSQLiteOpenHelperBase.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.data.base;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import com.magicbinder.data.BinderSQLiteAdapter;
import com.magicbinder.provider.contract.BinderContract;
import com.magicbinder.data.QualitySQLiteAdapter;
import com.magicbinder.provider.contract.QualityContract;
import com.magicbinder.data.ColortoCardSQLiteAdapter;
import com.magicbinder.provider.contract.ColortoCardContract;
import com.magicbinder.data.ColorSQLiteAdapter;
import com.magicbinder.provider.contract.ColorContract;
import com.magicbinder.data.Binder_CardSQLiteAdapter;
import com.magicbinder.provider.contract.Binder_CardContract;
import com.magicbinder.data.CardSQLiteAdapter;
import com.magicbinder.provider.contract.CardContract;
import com.magicbinder.MagicBinderApplication;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;




/**
 * This class makes it easy for ContentProvider implementations to defer <br />
 * opening and upgrading the database until first use, to avoid blocking <br />
 * application startup with long-running database upgrades.
 * @see android.database.sqlite.SQLiteOpenHelper
 */
public class MagicBinderSQLiteOpenHelperBase
                        extends SQLiteOpenHelper {
    /** TAG for debug purpose. */
    protected static final String TAG = "DatabaseHelper";
    /** Context. */
    protected android.content.Context ctx;

    /** Android's default system path of the database.
     *
     */
    private static String DB_PATH;
    /** database name. */
    private static String DB_NAME;
    /** is assets exist.*/
    private static boolean assetsExist;
    /** Are we in a JUnit context ?*/
    public static boolean isJUnit = false;

    /**
     * Constructor.
     * @param ctx Context
     * @param name name
     * @param factory factory
     * @param version version
     */
    public MagicBinderSQLiteOpenHelperBase(final android.content.Context ctx,
           final String name, final CursorFactory factory, final int version) {
        super(ctx, name, factory, version);
        this.ctx = ctx;
        DB_NAME = name;
        DB_PATH = ctx.getDatabasePath(DB_NAME).getAbsolutePath();

        try {
            this.ctx.getAssets().open(DB_NAME);
            assetsExist = true;
        } catch (IOException e) {
            assetsExist = false;
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        // Activation of SQLiteConstraints
        //db.execSQL("PRAGMA foreign_keys = ON;");
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        android.util.Log.i(TAG, "Create database..");

        if (!assetsExist) {
            /// Create Schema

            if (MagicBinderApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : Binder");
            }
            db.execSQL(BinderSQLiteAdapter.getSchema());

            if (MagicBinderApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : Quality");
            }
            db.execSQL(QualitySQLiteAdapter.getSchema());

            if (MagicBinderApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : ColortoCard");
            }
            db.execSQL(ColortoCardSQLiteAdapter.getSchema());

            if (MagicBinderApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : Color");
            }
            db.execSQL(ColorSQLiteAdapter.getSchema());

            if (MagicBinderApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : Binder_Card");
            }
            db.execSQL(Binder_CardSQLiteAdapter.getSchema());

            if (MagicBinderApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : Card");
            }
            db.execSQL(CardSQLiteAdapter.getSchema());
            db.execSQL("PRAGMA foreign_keys = ON;");
        }

    }

    /**
     * Clear the database given in parameters.
     * @param db The database to clear
     */
    public static void clearDatabase(final SQLiteDatabase db) {
        android.util.Log.i(TAG, "Clearing database...");

        db.delete(BinderContract.TABLE_NAME,
                null,
                null);
        db.delete(QualityContract.TABLE_NAME,
                null,
                null);
        db.delete(ColortoCardContract.TABLE_NAME,
                null,
                null);
        db.delete(ColorContract.TABLE_NAME,
                null,
                null);
        db.delete(Binder_CardContract.TABLE_NAME,
                null,
                null);
        db.delete(CardContract.TABLE_NAME,
                null,
                null);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion,
            final int newVersion) {
        android.util.Log.i(TAG, "Update database..");

        if (MagicBinderApplication.DEBUG) {
            android.util.Log.d(TAG, "Upgrading database from version " + oldVersion
                       + " to " + newVersion);
        }

        // TODO : Upgrade your tables !
    }


    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     * @throws IOException if error has occured while copying files
     */
    public void createDataBase() throws IOException {
        if (assetsExist && !checkDataBase()) {
            // By calling this method and empty database will be created into
            // the default system path
            // so we're gonna be able to overwrite that database with ours
            this.getReadableDatabase();

            try {
                copyDataBase();

            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        boolean result;

        SQLiteDatabase checkDB = null;
        try {
            final String myPath = DB_PATH + DB_NAME;
            // NOTE : the system throw error message : "Database is locked"
            // when the Database is not found (incorrect path)
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);
            result = true;
        } catch (SQLiteException e) {
            // database doesn't exist yet.
            result = false;
        }

        if (checkDB != null) {
            checkDB.close();
        }

        return result;
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     * @throws IOException if error has occured while copying files
     * */
    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        final InputStream myInput = this.ctx.getAssets().open(DB_NAME);

        // Path to the just created empty db
        final String outFileName = DB_PATH + DB_NAME;

        // Open the empty db as the output stream
        final OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        final byte[] buffer = new byte[1024];
        int length = myInput.read(buffer);
        while (length > 0) {
            myOutput.write(buffer, 0, length);
            length = myInput.read(buffer);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
}
