package cillian.android.studyapp.studyapp;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {

    ImageButton startButton;
    ImageButton pauseButton;
    ImageButton stopButton;
    TextView timerValue;
    ProgressBar circle;
    int secs= 0;
    int hours = 0;

    long startTime = 0L;

    Handler handler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Intent intent = getIntent();
        TextView title = (TextView) findViewById(R.id.timerHeader);
        title.setText(intent.getStringExtra("subject"));
        timerValue = (TextView) findViewById(R.id.timerValue);

        circle = (ProgressBar) findViewById(R.id.progressBar);
        circle.setMax(999);
        circle.setProgress(0);


        startButton = (ImageButton) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startButton.setVisibility(View.GONE);
                pauseButton.setVisibility(View.VISIBLE);
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(updateTimerThread, 0);

            }
        });

        pauseButton = (ImageButton) findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                pauseButton.setVisibility(View.GONE);
                startButton.setVisibility(View.VISIBLE);
                timeSwapBuff += timeInMilliseconds;

                handler.removeCallbacks(updateTimerThread);
            }

        });

        stopButton = (ImageButton) findViewById(R.id.stopButton);

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimerActivity.this,MainActivity.class);
                String tmp = secs + "";
                intent.putExtra("time",tmp);
                startActivity(intent);
            }
        });

    }

    private Runnable updateTimerThread = new Runnable() {



        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            int hours = mins % 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            circle.setProgress(milliseconds);
            timerValue.setText("" + String.format("%02d",hours) + ":" + String.format("%02d",mins) + ":" + String.format("%02d", secs));
            handler.postDelayed(this, 0);
        }

    };



}
