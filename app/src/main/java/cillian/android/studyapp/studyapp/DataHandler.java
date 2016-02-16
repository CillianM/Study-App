package cillian.android.studyapp.studyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Cillian on 10/02/2016.
 */
public class DataHandler {

    public static final String NAME = "name";
    public static final String LEVEL = "level";
    public static final String EXPERIENCE = "experience_points";
    public static final String TABLE_NAME = "user";
    public static final String DATA_BASE_NAME = "myDB";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_CREATE = "create table user (name text not null, " +
                                                                    "level int not null," +
                                                                    "experience_points int not null);";

    DataBaseHelper dbhelper;
    Context ctx;
    SQLiteDatabase db;


    public DataHandler(Context ctx)
    {
        this.ctx = ctx;
        dbhelper = new DataBaseHelper(ctx);
    }

    private static class DataBaseHelper extends SQLiteOpenHelper
    {

        DataBaseHelper(Context ctx)
        {
            super(ctx,DATA_BASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try
            {
                db.execSQL(TABLE_CREATE);
            }

            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("DROP TABLE IF EXIST user");
            onCreate(db);
        }
    }

    public DataHandler open()
    {
        db = dbhelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        dbhelper.close();
    }

    public long insertData(String name)
    {
        ContentValues content = new ContentValues();
        content.put(NAME,name);
        content.put(LEVEL,1);
        content.put(EXPERIENCE,0);
        return db.insert(TABLE_NAME,null,content);
    }

    public Cursor returnData()
    {
        return db.query(TABLE_NAME, new String[]{NAME, LEVEL, EXPERIENCE}, null, null, null, null, null);
    }
}
