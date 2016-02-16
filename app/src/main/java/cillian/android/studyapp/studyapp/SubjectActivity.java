package cillian.android.studyapp.studyapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SubjectActivity extends AppCompatActivity {

    int totalTime;
    String bestSubject;
    String worstSubject;
    int low = 0;
    int high = 0;

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
        ArrayList<String> subjects = new ArrayList<>();
        ArrayList<Integer> times = new ArrayList<>();
        s_handler.open();
        Cursor c2 = s_handler.returnData();
        if(c2.moveToFirst())
        {
            do
            {
                subjectlist.add(c2.getString(0)+" - Longest Streak: "+ c2.getInt(1) + " Total Time: " + c2.getInt(2));

                subjects.add(c2.getString(0));
                times.add(c2.getInt(1));

                totalTime = totalTime + c2.getInt(2);

            }
            while(c2.moveToNext());
        }

        s_handler.close();
        low = times.get(0);
        high = times.get(0);
        bestSubject = subjects.get(0);
        worstSubject = subjects.get(0);
        for(int i = 0; i < subjects.size(); i++)
        {
            if(times.get(i) < low)
            {
                worstSubject = subjects.get(i);
                high = times.get(i);
            }

            if(times.get(i) > high)
            {
                bestSubject = subjects.get(i);
                low = times.get(i);
            }
        }
        String stringTotal = totalTime + "";
        total.setText(stringTotal);
        String subjectHeader = "Worst subject is " + worstSubject;
        worst.setText(subjectHeader);
        subjectHeader = "Best subject is " + bestSubject;
        best.setText(subjectHeader);
        createList(subjectlist);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    public void createList(ArrayList<String> subjectlist)
    {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, subjectlist);
        subjects.setAdapter(dataAdapter);

        subjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //on item click create a url and open it in the browser
            public void onItemClick(AdapterView<?> l, View v, int position, long id) {

                String chosenSubject = (String) l.getItemAtPosition(position);
                Intent intent = new Intent(SubjectActivity.this, RemoveSubjectPopup.class);


                int index = chosenSubject.indexOf("-");
                String tmp = chosenSubject.substring(0, index -1);
                intent.putExtra("clickedSubject", tmp);
                chosenSubject = chosenSubject.substring(chosenSubject.indexOf(":"), chosenSubject.length());
                tmp = chosenSubject.substring(2, 3);
                intent.putExtra("bestTime", tmp);
                chosenSubject = chosenSubject.substring(3, chosenSubject.length());
                chosenSubject = chosenSubject.substring(chosenSubject.indexOf(":"), chosenSubject.length());
                tmp = chosenSubject.substring(2, 3);
                intent.putExtra("totalTime", tmp);
                startActivity(intent);

            }
        });
    }

}
