package cillian.android.studyapp.studyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subjects);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        subjects = intent.getStringArrayListExtra("subjectList");
        if(subjects == null)
            subjects = new ArrayList<>();

        subjectList = (ListView)findViewById(R.id.subject_list);
        currentSubject = (EditText)findViewById(R.id.subjectBar);
    }


    public void add(View view)
    {
        String newSubject = currentSubject.getText().toString();
        subjects.add(newSubject);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, subjects);
        subjectList.setAdapter(dataAdapter);
        currentSubject.setText("");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,SetupActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    public void finshed(View view)
    {
        DataHandler handler = new DataHandler(getBaseContext());
        handler.open();
        long id = handler.insertData(name);
        handler.close();

        SubjectHandler S_handler = new SubjectHandler(getBaseContext());
        S_handler.open();



        for(int i = 0; i < subjects.size(); i++)
        {
            long s_id = S_handler.insertData(subjects.get(i));
        }
        S_handler.close();
        Toast.makeText(getBaseContext(), "Profile Created!", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
