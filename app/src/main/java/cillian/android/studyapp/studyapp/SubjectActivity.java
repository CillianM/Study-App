package cillian.android.studyapp.studyapp;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SubjectActivity extends AppCompatActivity {

    int totalTime;
    String bestSubject;
    String worstSubject;
    int low;
    int high;

    TextView worst;
    TextView best;
    TextView total;

    ListView subjects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        subjects = (ListView) findViewById(R.id.subject_list);
        total = (TextView) findViewById(R.id.time);
        best = (TextView) findViewById(R.id.best);
        worst = (TextView) findViewById(R.id.worst);
        getSubjects();
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

                if(c2.getInt(1) >= high)
                {
                    bestSubject = c2.getString(0);
                    high = c2.getInt(1);
                }

                if(c2.getInt(1) <= low)
                {
                    worstSubject = c2.getString(0);
                    low = c2.getInt(1);
                }

                totalTime = totalTime + c2.getInt(2);

            }
            while(c2.moveToNext());
        }

        s_handler.close();
        String stringTotal = totalTime + "";
        total.setText(stringTotal);
        worst.setText("Worst subject is " + worstSubject);
        best.setText("Best subject is " +bestSubject);
        createList(subjectlist);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    public void createList(ArrayList<String> subjectlist)
    {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, subjectlist);


        subjects.setAdapter(dataAdapter);
    }
}
