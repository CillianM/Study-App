package cillian.android.studyapp.studyapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class CharacterSetup extends AppCompatActivity {

    ImageView character;
    ImageView eyes;
    ImageView shirt;
    ImageView pants;

    ImageButton bodyBack;
    ImageButton bodyForward;
    ImageButton eyesBack;
    ImageButton eyesForward;
    ImageButton shirtBack;
    ImageButton shirtForward;
    ImageButton pantsBack;
    ImageButton pantsForward;

    int currentCharacter = 1;
    int getCurrentShirt = 1;
    int currentPants= 1;
    int currentEyes = 1;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_setup);
        character = (ImageView) findViewById(R.id.profile_pic);
        eyes = (ImageView) findViewById(R.id.profile_eyes);
        shirt = (ImageView) findViewById(R.id.profile_shirt);
        pants = (ImageView) findViewById(R.id.profile_pants);
        buttonCreation();

        Intent intent = getIntent();
        name = intent.getStringExtra("name");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_character_setup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void buttonCreation()
    {
        bodyBack = (ImageButton) findViewById(R.id.bodyBack);
        bodyForward = (ImageButton) findViewById(R.id.bodyForward);
        bodyBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCharacter--;
                if(currentCharacter == 0) currentCharacter = 6;
                String characterPath = "profile_" + currentCharacter;
                character.setImageResource((getResources().getIdentifier(characterPath, "drawable", getPackageName())));

            }
        });

        bodyForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCharacter++;
                if(currentCharacter == 7) currentCharacter = 1;
                String characterPath = "profile_" + currentCharacter;
                character.setImageResource((getResources().getIdentifier(characterPath, "drawable", getPackageName())));

            }
        });


        eyesBack = (ImageButton) findViewById(R.id.eyesBack);
        eyesForward = (ImageButton) findViewById(R.id.eyesForward);

        eyesBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentEyes--;
                if(currentEyes == 0) currentEyes = 5;
                String characterPath = "eyes_" + currentEyes;
                eyes.setImageResource((getResources().getIdentifier(characterPath, "drawable", getPackageName())));

            }
        });

        eyesForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentEyes++;
                if(currentEyes == 6) currentEyes = 1;
                String characterPath = "eyes_" + currentEyes;
                eyes.setImageResource((getResources().getIdentifier(characterPath, "drawable", getPackageName())));

            }
        });

        shirtBack = (ImageButton) findViewById(R.id.shirtBack);
        shirtForward = (ImageButton) findViewById(R.id.shirtForward);

        shirtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentShirt--;
                if(getCurrentShirt == 0) getCurrentShirt = 6;
                String characterPath = "shirt_" + getCurrentShirt;
                shirt.setImageResource((getResources().getIdentifier(characterPath, "drawable", getPackageName())));

            }
        });

        shirtForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentShirt++;
                if(getCurrentShirt == 7) getCurrentShirt = 1;
                String characterPath = "shirt_" + getCurrentShirt;
                shirt.setImageResource((getResources().getIdentifier(characterPath, "drawable", getPackageName())));

            }
        });

        pantsBack = (ImageButton) findViewById(R.id.pantsBack);
        pantsForward = (ImageButton) findViewById(R.id.pantsForward);

        pantsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPants--;
                if(currentPants == 0) currentPants = 7;
                String characterPath = "pants_" + currentPants;
                pants.setImageResource((getResources().getIdentifier(characterPath, "drawable", getPackageName())));

            }
        });

        pantsForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPants++;
                if(currentPants == 8) currentPants = 1;
                String characterPath = "pants_" + currentPants;
                pants.setImageResource((getResources().getIdentifier(characterPath, "drawable", getPackageName())));

            }
        });
    }

    public void finish(View view)
    {
        Intent intent = new Intent(this,AddSubjects.class);
        intent.putExtra("name",name);
        intent.putExtra("character",currentCharacter+ "");
        intent.putExtra("eyes",currentEyes+ "");
        intent.putExtra("shirt",getCurrentShirt+ "");
        intent.putExtra("pants",currentPants+ "");
        startActivity(intent);
    }
}
