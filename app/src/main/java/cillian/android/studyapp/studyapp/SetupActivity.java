package cillian.android.studyapp.studyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
        if(name.length() > 0) {
            Intent intent = new Intent(this, CharacterSetup.class);
            intent.putExtra("name", name);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        }
    }


}
