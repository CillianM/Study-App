package cillian.android.studyapp.studyapp;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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
    int TotalTime = 0;
    String subject;

    long startTime = 0L;

    Handler handler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    long pausedTime = 0L;
    long resumedTime = 0L;
    boolean skipInitialGet = false;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Intent intent = getIntent();
        TextView title = (TextView) findViewById(R.id.timerHeader);
        subject = intent.getStringExtra("subject");
        if(subject.length() > 9)
            subject = subject.substring(0,7) + "..";
        title.setText(subject);

        String tmp = intent.getStringExtra("startTime");
        if(tmp != null) {
            startTime = Long.parseLong(intent.getStringExtra("startTime"));
            timeSwapBuff = Long.parseLong(intent.getStringExtra("buff"));
            skipInitialGet = true;
        }

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

                if(!skipInitialGet)
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
                SubjectHandler handler = new SubjectHandler(getBaseContext());
                handler.open();
                handler.updateSubject(subject,TotalTime);
                handler.close();
                String tmp = TotalTime + "";
                intent.putExtra("time",tmp);
                startActivity(intent);
            }
        });

    }

    protected void onPause()
    {
        super.onPause();
        TimeHandler t = new TimeHandler(getBaseContext());
        t.open();
        t.updateTime(1+"",startTime,timeSwapBuff);
        t.close();
        Handler mHandler = new Handler();
        Context appContext = getApplicationContext();
        mHandler.post(new DisplayNotification(appContext));
    }

    public Runnable updateTimerThread = new Runnable() {



        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            secs = (int) (updatedTime / 1000);
            int mins = secs / 60 % 60;
            int hours = mins / 60;
            secs = secs % 60;
            TotalTime = mins + (hours * 60);
            int milliseconds = (int) (updatedTime % 1000);
            circle.setProgress(milliseconds);
            String clockText = "" + String.format("%02d",hours) + ":" + String.format("%02d",mins) + ":" + String.format("%02d", secs);
            timerValue.setText(clockText);
            handler.postDelayed(this, 0);
        }

    };


}


