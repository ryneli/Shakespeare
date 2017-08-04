package com.zhenqiangli.shakespeare.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.zhenqiangli.shakespeare.data.model.DatabaseSchema.Works;
import com.zhenqiangli.shakespeare.data.model.DatabaseSchema.Works.Cols;
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

    private static String DATABASE_NAME = "shakespeare_en.sqlite";
    private static int DATABASE_VERSION_BASE = 1;
    private static int DATABASE_VERSION = 1;
    private SQLiteDatabase mDataBase;
    private String mDatabasePath;

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext =context;
        mDatabasePath = mContext.getApplicationInfo().dataDir
            + "/databases/";
        boolean existed = checkDatabase();
        if (!existed) {
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
        Log.d(TAG, "onCreate: " + db.getVersion());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: " + String.format("%s/%s", oldVersion, newVersion));
    }

    private void createDatabase() throws IOException {
        boolean existed = checkDatabase();
        if(!existed) {
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        SQLiteDatabase db = getWritableDatabase();
        String sql = "ALTER TABLE " + Works.NAME +
            " ADD COLUMN " + Cols.LAST_ACCESS + " INTEGER";
        Log.d(TAG, "createDatabase: " + sql);
        db.execSQL(sql);
        db.setVersion(DATABASE_VERSION_BASE);
        db.close();
    }

    private boolean checkDatabase() {
        boolean checked = false;
        try {
            String filePath = mDatabasePath + DATABASE_NAME;
            Log.d(TAG, "checkDatabase: " + filePath);
            File file = new File(filePath);
            checked = file.exists();

            /*
            if (checked) {
                file.delete();
            }
            checked = false;
            */
        } catch(SQLiteException e) {
            Log.d(TAG, "checkDatabase: Database doesn't exist");
        }
        Log.d(TAG, "checkDatabase: " + checked);
        return checked;
    }

    private void copyDatabase() throws IOException {
        Log.d(TAG, "copyDatabase: ");
        //Open your local db as the input stream
        InputStream input = mContext.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outPath = mDatabasePath + DATABASE_NAME;

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
}