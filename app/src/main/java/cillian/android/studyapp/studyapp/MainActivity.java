package cillian.android.studyapp.studyapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView name_text;
    TextView level_text;
    ProgressBar experienceBar;
    Spinner subjects;
    String name = "";
    String bestSubject = "";
    String worstSubject = "";
    int level = 0;
    int bestTime = 0;
    int experience = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name_text = (TextView) findViewById(R.id.name);
        level_text = (TextView) findViewById(R.id.level_text);
        experienceBar = (ProgressBar) findViewById(R.id.xpBar);
        subjects = (Spinner) findViewById(R.id.subject_list);
        experienceBar.setMax(100);


        if(!doesDatabaseExist(this,"myDB"))
        {
            Intent intent = new Intent(this,SetupActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        }

        else
        {
            getUserInfo();
            getSubjects();
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

    public void getUserInfo()
    {
        DataHandler handler = new DataHandler(getBaseContext());
        handler.open();
        Cursor c1 = handler.returnData();
        if(c1.moveToFirst())
        {
            do
            {
                name = c1.getString(0);
                level = c1.getInt(1);
                experience = c1.getInt(2);
                bestTime = c1.getInt(3);
                bestSubject = c1.getString(4);
                worstSubject = c1.getString(5);

            }
            while(c1.moveToNext());
        }

        handler.close();
    }

    public void getSubjects()
    {
        SubjectHandler s_handler = new SubjectHandler(getBaseContext());
        ArrayList<String> subjectlist = new ArrayList<>();
        s_handler.open();
        Cursor c2 = s_handler.returnData();
        subjectlist.add("Create new...");
        if(c2.moveToFirst())
        {
            do
            {
                subjectlist.add(c2.getString(0));
            }
            while(c2.moveToNext());
        }

        s_handler.close();
        createSpinner(subjectlist);
    }

    public void createSpinner(ArrayList<String> subjectlist)
    {
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, subjectlist);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        subjects.setAdapter(dataAdapter);
    }

    public void subjects(View view) {

        Intent intent = new Intent(this, SubjectActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

}


