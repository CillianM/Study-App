package cillian.android.studyapp.studyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ProfileSettings extends AppCompatActivity {

    EditText userName;

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
    String tmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        Intent intent = getIntent();
        name = intent.getStringExtra("username");



        userName = (EditText) findViewById(R.id.currentName);
        userName.setText(name);
        currentCharacter = Integer.parseInt(intent.getStringExtra("character"));
        tmp = "profile_" + intent.getStringExtra("character");
        character = (ImageView) findViewById(R.id.profile_pic);
        character.setImageResource((getResources().getIdentifier(tmp, "drawable", getPackageName())));

        currentEyes = Integer.parseInt(intent.getStringExtra("eyes"));
        tmp = "eyes_" + intent.getStringExtra("eyes");
        eyes = (ImageView) findViewById(R.id.profile_eyes);
        eyes.setImageResource((getResources().getIdentifier(tmp, "drawable", getPackageName())));

        getCurrentShirt = Integer.parseInt(intent.getStringExtra("shirt"));
        tmp = "shirt_" + intent.getStringExtra("shirt");
        shirt = (ImageView) findViewById(R.id.profile_shirt);
        shirt.setImageResource((getResources().getIdentifier(tmp, "drawable", getPackageName())));

        currentPants = Integer.parseInt(intent.getStringExtra("pants"));
        tmp = "pants_" + intent.getStringExtra("pants");
        pants = (ImageView) findViewById(R.id.profile_pants);
        pants.setImageResource((getResources().getIdentifier(tmp, "drawable", getPackageName())));





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
        String newName = userName.getText().toString();
        if(newName.length() > 0) {
            DataHandler handler = new DataHandler(getBaseContext());
            handler.open();
            handler.changeProfile(name, newName, currentCharacter + "", currentEyes + "", getCurrentShirt + "", currentPants + "");
            handler.close();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }
}
