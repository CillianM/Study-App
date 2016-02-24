package cillian.android.studyapp.studyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TimeHandler {

        public static final String START_TIME = "startTime";
        public static final String TIME_BUFF = "timeBuff";
        public static final String ID ="time_id";
        public static final String CURRENT_SUBJECT ="current_subject";
        public static final String TABLE_NAME = "time";
        public static final String DATA_BASE_NAME = "timeDB";

        public static final int DATABASE_VERSION = 1;
        public static final String TABLE_CREATE = "create table time (time_id text not null,current_subject text not null, startTime int not null, timeBuff int not null);";

        DataBaseHelper dbhelper;
        Context ctx;
        SQLiteDatabase db;


        public TimeHandler(Context ctx)
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
                db.execSQL("DROP TABLE IF EXIST time");
                onCreate(db);
            }
        }

        public TimeHandler open()
        {
            db = dbhelper.getWritableDatabase();
            return this;
        }

        public void close()
        {
            dbhelper.close();
        }

        public long insertData()
        {
            ContentValues content = new ContentValues();
            content.put(CURRENT_SUBJECT,"nothing");
            content.put(START_TIME,0L);
            content.put(TIME_BUFF,0L);
            content.put(ID, 1);
            return db.insert(TABLE_NAME, null, content);
        }

        public boolean updateTime(String id,String newSubject, long newStartTime, long newTimeBuff)
        {
            ContentValues content = new ContentValues();
            content.put(CURRENT_SUBJECT,newSubject);
            content.put(START_TIME,newStartTime);
            content.put(TIME_BUFF,newTimeBuff);
            db.update(TABLE_NAME, content, ID + " = ?", new String[]{id});
            return true;
        }

        public Cursor returnData()
        {
            return db.query(TABLE_NAME, new String[]{ID,CURRENT_SUBJECT,START_TIME,TIME_BUFF}, null, null, null, null, null);
        }


        public boolean deleteTable()
        {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            return true;
        }
}
