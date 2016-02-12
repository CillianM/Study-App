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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;


import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView name_text;
    TextView level_text;
    ProgressBar experienceBar;
    Spinner subjects;
    PopupWindow addSubjectWindow;
    ImageButton startButton;
    String name = "";
    String bestSubject = "";
    String worstSubject = "";
    String subjectForTimer;
    int level = 0;
    int bestTime = 0;
    int experience = 0;
    int totalTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name_text = (TextView) findViewById(R.id.name);
        level_text = (TextView) findViewById(R.id.level_text);
        experienceBar = (ProgressBar) findViewById(R.id.xpBar);
        subjects = (Spinner) findViewById(R.id.subject_list);

        addSubjectWindow = new PopupWindow(this);
        experienceBar.setMax(100);

        Intent timeIntent = getIntent();
        String tmp = timeIntent.getStringExtra("time");
        if(tmp != null)
            totalTime = Integer.parseInt(tmp);

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

            experienceBar.setMax(level * 60);
            if(totalTime == 0)
                experienceBar.setProgress(experience);

            else
                experienceBar.setProgress(totalTime / 60);

        }
        startButton = (ImageButton) findViewById(R.id.startButton);
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

        if(c2.moveToFirst())
        {
            do
            {
                subjectlist.add(c2.getString(0));
            }
            while(c2.moveToNext());
        }
        subjectlist.add("Create new...");
        s_handler.close();
        createSpinner(subjectlist);
    }

    public void createSpinner(ArrayList<String> subjectlist)
    {
        // Creating adapter for spinner
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, subjectlist);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        subjects.setAdapter(dataAdapter);

        subjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selected = subjects.getSelectedItem().toString();

                if (selected.equals("Create new...")) {
                    startActivity(new Intent(MainActivity.this, NewSubjectPopup.class));
                } else {
                    subjectForTimer = selected;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //Nothing necessary here
            }

        });
    }

    public void subjects(View view) {

        Intent intent = new Intent(this, SubjectActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    public void startTimer(View view)
    {
        Intent intent = new Intent(this, TimerActivity.class);
        intent.putExtra("subject",subjectForTimer);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

}


