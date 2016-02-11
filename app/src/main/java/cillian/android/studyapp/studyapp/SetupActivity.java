package cillian.android.studyapp.studyapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SetupActivity extends AppCompatActivity {

    EditText nameText;
    EditText subjectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        nameText = (EditText) findViewById(R.id.nameBar);
        subjectText = (EditText) findViewById(R.id.subjectBar);
    }

    //setup basic database
    public void setup(View view)
    {
        String name = nameText.getText().toString();
        String subject = subjectText.getText().toString();

        DataHandler handler = new DataHandler(getBaseContext());
        handler.open();
        long id = handler.insertData(name);
        handler.close();

        SubjectHandler S_handler = new SubjectHandler(getBaseContext());
        S_handler.open();
        long s_id = S_handler.insertData(subject);
        S_handler.close();
        Toast.makeText(getBaseContext(),"Profile Created!",Toast.LENGTH_LONG).show();


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }


}
