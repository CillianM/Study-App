package cillian.android.studyapp.studyapp;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.security.auth.Subject;

public class DisplayNotification implements Runnable {

    Context mContext;
    NotificationManager mNotificationManager;
    int NOTIFICATION_ID = 1;

    String timerValue;
    int secs= 0;
    String subject;
    long startTime = 0L;
    long timeInMilliseconds = 0L;
    long updatedTime = 0L;
    long timeSwapBuff = 0L;

    public DisplayNotification(Context mContext) {
        this.mContext = mContext;
        mNotificationManager = (NotificationManager)
                mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public void run() {
        makeNotification(mContext);
    }

    private void makeNotification(Context context) {
        Intent intent = new Intent(context, TimerActivity.class);
        //subject = intent.getStringExtra("subject");
        //startTime = Long.parseLong(intent.getStringExtra("startTime"));

        TimeHandler t = new TimeHandler(context);
        t.open();
        Cursor c2 = t.returnData();
        if(c2.moveToFirst())
        {
            do
            {
                startTime = c2.getLong(1);
                timeSwapBuff = c2.getLong(2);

            }
            while(c2.moveToNext());
        }
        t.close();

        getTime();
        intent.putExtra("subject", subject);
        intent.putExtra("startTime", startTime);
        intent.putExtra("buff", timeSwapBuff);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);





        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle(subject)
                .setContentText(timerValue)
                .setSmallIcon(R.drawable.clock_icon)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);


        NotificationManager notificationManager;
        Notification n;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            n = builder.build();
        } else {
            n = builder.getNotification();
        }

        n.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;

        notificationManager.notify(NOTIFICATION_ID, n);


    }

    public void getTime()
    {
        timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
        updatedTime = timeSwapBuff + timeInMilliseconds;
        secs = (int) (updatedTime / 1000);
        int mins = secs / 60 % 60;
        int hours = mins / 60;
        secs = secs % 60;
        timerValue = "" + String.format("%02d",hours) + ":" + String.format("%02d",mins) + ":" + String.format("%02d", secs);
    }
}
