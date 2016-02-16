package cillian.android.studyapp.studyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cillian on 10/02/2016.
 */
public class SubjectHandler {

    public static final String SUBJECT = "subject";
    public static final String BEST_TIME = "best_time";
    public static final String TOTAL_TIME = "total_time";
    public static final String TABLE_NAME = "subjects";
    public static final String DATA_BASE_NAME = "subjectDB";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_CREATE = "create table subjects (subject text not null, " +
                                              "best_time int not null," +
                                              "total_time int not null);";

    DataBaseHelper dbhelper;
    Context ctx;
    SQLiteDatabase db;


    public SubjectHandler(Context ctx)
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
            db.execSQL("DROP TABLE IF EXIST subjects");
            onCreate(db);
        }
    }

    public SubjectHandler open()
    {
        db = dbhelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        dbhelper.close();
    }

    public long insertData(String subject)
    {
        ContentValues content = new ContentValues();
        content.put(SUBJECT,subject);
        content.put(BEST_TIME,0);
        content.put(TOTAL_TIME, 0);
        return db.insert(TABLE_NAME, null, content);
    }

    public Cursor returnData()
    {
        return db.query(TABLE_NAME, new String[]{SUBJECT, BEST_TIME, TOTAL_TIME}, null, null, null, null, null);
    }

    public void removeData(String name)
    {
        db.delete(TABLE_NAME, SUBJECT + " = ?", new String[] { name });
    }

    public boolean updateData(String oldname,String newname,int bestTime,int totalTime)
    {
        ContentValues content = new ContentValues();
        content.put(SUBJECT,newname);
        content.put(BEST_TIME, bestTime);
        content.put(TOTAL_TIME, totalTime);
        db.update(TABLE_NAME, content, SUBJECT + " = ?", new String[]{oldname});
        return true;
    }

    public boolean updateTime(String name,int bestTime,int totalTime)
    {
        ContentValues content = new ContentValues();
        content.put(SUBJECT,name);
        content.put(BEST_TIME, bestTime);
        content.put(TOTAL_TIME, totalTime);
        db.update(TABLE_NAME, content, SUBJECT + " = ?", new String[]{name});
        return true;
    }

    public boolean updateSubject(String subject, int time)
    {
        Cursor cursor = null;
        int oldbest = -1;
        int total = 0;
        cursor = db.rawQuery("SELECT best_time,total_time FROM subjects WHERE subject=?", new String[] {subject + ""});

        if(cursor.getCount() > 0) {

            cursor.moveToFirst();
            oldbest= cursor.getInt(cursor.getColumnIndex("best_time"));
            total = cursor.getInt(cursor.getColumnIndex("total_time"));
        }
        cursor.close();

        total = total + time;
        if(oldbest < time && oldbest != -1)
            updateTime(subject,time,total);
        else
            updateTime(subject,oldbest,total);
        return true;
    }

}
