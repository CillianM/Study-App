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

public class SetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        EditText editText = (EditText) findViewById(R.id.nameBar);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    setup();
                    return true;
                }
                return false;
            }
        });

    }

    //setup basic database
    public void setup()
    {
        EditText editText = (EditText) findViewById(R.id.nameBar);
        String name = editText.getText().toString();
        //table requires name, base level, best subject, worst subject

        SQLiteDatabase myDB = this.openOrCreateDatabase("user", MODE_PRIVATE, null);

         //Create a Table in the Database.
        myDB.execSQL("CREATE TABLE IF NOT EXISTS"
                + "user"
                + " (Name VARCHAR, Level INT,BSubject VARCHAR,WSubject VARCHAR);");

        //setup default user table
        myDB.execSQL("INSERT INTO "
                + "user"
                + " (Name, Level,BSubject,WSubject)"
                + " VALUES ('name', 1,null,null);");

        //setup beginnings of subject table
        myDB.execSQL("CREATE TABLE IF NOT EXISTS"
                + "subjects"
                + " (Name VARCHAR, Best_Time INT);");


        finish();

    }


}
