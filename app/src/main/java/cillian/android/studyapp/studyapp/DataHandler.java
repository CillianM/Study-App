package cillian.android.studyapp.studyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHandler {

    public static final String NAME = "name";
    public static final String LEVEL = "level";
    public static final String EXPERIENCE = "experience_points";
    public static final String TABLE_NAME = "user";
    public static final String EYES = "eyes";
    public static final String SHIRT = "shirt";
    public static final String SKIN = "skin";
    public static final String PANTS = "pants";
    public static final String DATA_BASE_NAME = "myDB";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_CREATE = "create table user (name text not null, " +
                                                                    "skin text not null, " +
                                                                    "eyes text not null, " +
                                                                    "shirt text not null, " +
                                                                    "pants text not null, " +
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

    public long insertData(String name, String skin,String eyes, String shirt, String pants)
    {
        ContentValues content = new ContentValues();
        content.put(NAME,name);
        content.put(SKIN,skin);
        content.put(EYES,eyes);
        content.put(SHIRT,shirt);
        content.put(PANTS,pants);
        content.put(LEVEL,1);
        content.put(EXPERIENCE, 0);
        return db.insert(TABLE_NAME, null, content);
    }

    public Cursor returnData()
    {
        return db.query(TABLE_NAME, new String[]{NAME, SKIN, EYES, SHIRT, PANTS, LEVEL, EXPERIENCE}, null, null, null, null, null);
    }

    public boolean levelUp(String name,String newLevel, String experienceGained)
    {
        ContentValues content = new ContentValues();
        content.put(LEVEL, newLevel);
        content.put(EXPERIENCE, experienceGained);
        db.update(TABLE_NAME, content, NAME + " = ?", new String[]{name});
        return true;
    }

    public boolean changeProfile(String oldname,String newName,String skin, String eyes,String shirt,String pants)
    {
        ContentValues content = new ContentValues();
        content.put(NAME, newName);
        content.put(SKIN, skin);
        content.put(EYES, eyes);
        content.put(SHIRT, shirt);
        content.put(PANTS, pants);
        db.update(TABLE_NAME, content, NAME + " = ?", new String[]{oldname});
        return true;
    }
}
