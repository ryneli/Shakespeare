package com.zhenqiangli.shakespeare.data;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * DbHelper for Shakespeare works
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private Context mContext;

    private static String DB_NAME = "shakespeare_en.sqlite";
    private SQLiteDatabase mDataBase;
    private String mDatabasePath;

    public DatabaseHelper(Context context) {
        super(context,DB_NAME,null,1);
        this.mContext =context;
        mDatabasePath = mContext.getApplicationInfo().dataDir
            + "/databases/";
        boolean existed = checkDatabase();
        if (existed) {
            //System.out.println("Database exists");
            openDatabase();
        } else {
            System.out.println("Database doesn't exist");
            try {
                createDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: ");
    }

    public SQLiteDatabase getWritableDatabase() {
        return mDataBase;
    }

    public synchronized void close() {
        if(mDataBase != null) {
            mDataBase.close();
        }
        super.close();
    }

    private void createDatabase() throws IOException {
        Log.d(TAG, "createDatabase: ");
        boolean existed = checkDatabase();
        if(!existed) {
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkDatabase() {
        boolean checked = false;
        try {
            String filePath = mDatabasePath + DB_NAME;
            File file = new File(filePath);
            checked = file.exists();
        } catch(SQLiteException e) {
            Log.d(TAG, "checkDatabase: Database doesn't exist");
        }
        return checked;
    }

    private void copyDatabase() throws IOException {
        Log.d(TAG, "copyDatabase: ");
        //Open your local db as the input stream
        InputStream input = mContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outPath = mDatabasePath + DB_NAME;

        //Open the empty db as the output stream
        OutputStream output = new FileOutputStream(outPath);

        // transfer byte to input file to output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer))>0) {
            output.write(buffer,0,length);
        }

        //Close the streams
        output.flush();
        output.close();
        input.close();
    }

    private void openDatabase() throws SQLException {
        Log.d(TAG, "openDatabase: ");
        String path = mDatabasePath + DB_NAME;
        mDataBase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);

        long numOfRows = DatabaseUtils.queryNumEntries(mDataBase, "works");
        Log.d(TAG, "openDatabase: " + numOfRows);
    }
}