package cillian.android.studyapp.studyapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView name_text = (TextView) findViewById(R.id.name);
        TextView level_text = (TextView) findViewById(R.id.level_text);
        ProgressBar experienceBar = (ProgressBar) findViewById(R.id.xpBar);
        experienceBar.setMax(100);
        String name = "";
        String bestSubject = "";
        String worstSubject = "";
        int level = 0;
        int bestTime = 0;
        int experience = 0;

        if(!doesDatabaseExist(this,"myDB"))
        {
            Intent intent = new Intent(this,SetupActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        }

        else
        {
            DataHandler handler = new DataHandler(getBaseContext());
            handler.open();
            Cursor c = handler.returnData();
            if(c.moveToFirst())
            {
                do
                {
                    name = c.getString(0);
                    level = c.getInt(1);
                    experience = c.getInt(2);
                    bestTime = c.getInt(3);
                    bestSubject = c.getString(4);
                    worstSubject = c.getString(5);

                }
                while(c.moveToNext());
            }

            handler.close();
            name_text.setText(name);
            String levelText= level + "";
            level_text.setText(levelText);

            experienceBar.setMax(level * 10);
            experienceBar.setProgress(experience);

            Toast.makeText(getBaseContext(), "best time is " + bestTime + " best subject is " + bestSubject + " worst subject is " + worstSubject, Toast.LENGTH_LONG).show();

        }
    }

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }
}
