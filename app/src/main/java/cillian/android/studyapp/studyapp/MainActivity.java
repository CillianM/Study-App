package cillian.android.studyapp.studyapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView name_text;
    TextView level_text;
    TextView experience_text;
    ImageView skin_pic;
    ImageView eye_pic;
    ImageView shirt_pic;
    ImageView pants_pic;
    ProgressBar experienceBar;
    Spinner subjects;
    PopupWindow addSubjectWindow;
    ImageButton startButton;
    String name = "";
    String subjectForTimer;
    String skin;
    String eyes;
    String shirt;
    String pants;
    int level = 0;
    int experience = 0;
    int experienceObtained = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!doesDatabaseExist(this,"myDB"))
        {
            Intent intent = new Intent(this,SetupActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        }
        else
        {
        name_text = (TextView) findViewById(R.id.name);
        level_text = (TextView) findViewById(R.id.level_text);
        experienceBar = (ProgressBar) findViewById(R.id.xpBar);
        subjects = (Spinner) findViewById(R.id.subject_list);
        experience_text = (TextView) findViewById(R.id.experience);
        skin_pic = (ImageView) findViewById(R.id.profile_pic);
        eye_pic = (ImageView) findViewById(R.id.profile_eyes);
        shirt_pic = (ImageView) findViewById(R.id.profile_shirt);
        pants_pic = (ImageView) findViewById(R.id.profile_pants);

        addSubjectWindow = new PopupWindow(this);
        experienceBar.setMax(100);

        Intent timeIntent = getIntent();
        String tmp = timeIntent.getStringExtra("time");
        if(tmp != null)
            experienceObtained = Integer.parseInt(tmp);




            getUserInfo();
            getSubjects();
            setPic();
            name_text.setText(name);
            String levelText= level + "";
            level_text.setText(levelText);
            int xpCap = level * 60;
            String progress = experience + " / " + xpCap;
            experience_text.setText(progress);

            experienceBar.setMax(xpCap);
            if(experienceObtained == 0)
                experienceBar.setProgress(experience);

            else
                calculateExperience();

        }
        startButton = (ImageButton) findViewById(R.id.startButton);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"User Settings");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == 1) {
            Intent intent = new Intent(this,ProfileSettings.class);
            intent.putExtra("username",name);
            intent.putExtra("character",skin);
            intent.putExtra("eyes",eyes);
            intent.putExtra("shirt",shirt);
            intent.putExtra("pants",pants);
            startActivity(intent);
        }

        return true;
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
                skin = c1.getString(1);
                eyes = c1.getString(2);
                shirt = c1.getString(3);
                pants = c1.getString(4);
                level = c1.getInt(5);
                experience = c1.getInt(6);
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

    public void setPic()
    {
        skin_pic.setImageResource((getResources().getIdentifier("profile_" +skin, "drawable", getPackageName())));
        eye_pic.setImageResource((getResources().getIdentifier("eyes_" +eyes, "drawable", getPackageName())));
        shirt_pic.setImageResource((getResources().getIdentifier("shirt_" +shirt, "drawable", getPackageName())));
        pants_pic.setImageResource((getResources().getIdentifier("pants_" +pants, "drawable", getPackageName())));

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

    public void calculateExperience()
    {
        int newLevel = level;
        int totalExperience = experience + experienceObtained;
        if(totalExperience > level * 60)
        {
            for (int i = 1; i < level; i++) {
                totalExperience = totalExperience + (i * 60);
            }
            newLevel = 1;
            while(totalExperience - (newLevel * 60) > 0)
            {
                if (totalExperience - (newLevel * 60) < 0)
                    break;
                else {
                    totalExperience = totalExperience - (newLevel * 60);
                    newLevel++;
                }
            }
            experienceBar.setMax(newLevel * 60);
        }

        experienceBar.setProgress(totalExperience);
        DataHandler handler = new DataHandler(getBaseContext());
        handler.open();
        handler.levelUp(name, newLevel + "", totalExperience + "");
        handler.close();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }

}

//get the current level the user is at and calculate that experience that it would have taken
/*to get there then add on the rest of the experince and claculate what level that would be and whats left over
this can be done with division and modulus of the expereince gained in total*/

