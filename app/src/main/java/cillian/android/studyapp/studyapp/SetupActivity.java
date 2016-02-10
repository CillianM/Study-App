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

    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        editText = (EditText) findViewById(R.id.nameBar);
    }

    //setup basic database
    public void setup(View view)
    {
        String name = editText.getText().toString();

        DataHandler handler = new DataHandler(getBaseContext());
        handler.open();
        long id = handler.insertData(name);
        handler.close();
        Toast.makeText(getBaseContext(),"Profile Created!",Toast.LENGTH_LONG).show();


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }


}
