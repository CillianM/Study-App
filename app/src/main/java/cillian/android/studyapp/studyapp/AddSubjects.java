package cillian.android.studyapp.studyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class AddSubjects extends AppCompatActivity {

    ArrayList<String> subjects;
    ListView subjectList;
    EditText currentSubject;
    String name;
    String skin;
    String eyes;
    String shirt;
    String pants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subjects);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        skin = intent.getStringExtra("character");
        eyes = intent.getStringExtra("eyes");
        shirt = intent.getStringExtra("shirt");
        pants = intent.getStringExtra("pants");
        subjects = intent.getStringArrayListExtra("subjectList");
        if(subjects == null)
            subjects = new ArrayList<>();

        subjectList = (ListView)findViewById(R.id.subject_list);
        currentSubject = (EditText)findViewById(R.id.subjectBar);
    }


    public void add(View view)
    {
        String newSubject = currentSubject.getText().toString();
        if(newSubject.length() > 0)
        {
            subjects.add(newSubject);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, subjects);
            subjectList.setAdapter(dataAdapter);
            currentSubject.setText("");
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,CharacterSetup.class);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    public void finshed(View view)
    {
        DataHandler handler = new DataHandler(getBaseContext());
        handler.open();
        handler.insertData(name,skin,eyes,shirt,pants);
        handler.close();

        SubjectHandler S_handler = new SubjectHandler(getBaseContext());
        S_handler.open();



        for(int i = 0; i < subjects.size(); i++)
        {
            S_handler.insertData(subjects.get(i));
        }
        S_handler.close();
        Toast.makeText(getBaseContext(), "Profile Created!", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
