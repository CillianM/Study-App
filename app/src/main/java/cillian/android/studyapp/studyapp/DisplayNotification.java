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

        TimeHandler t = new TimeHandler(context);
        t.open();
        Cursor c2 = t.returnData();
        if(c2.moveToFirst())
        {
            do
            {
                subject = c2.getString(1);
                startTime = c2.getLong(2);
                timeSwapBuff = c2.getLong(3);

            }
            while(c2.moveToNext());
        }
        t.close();

        getTime();
        Intent intent = new Intent().setClass(context, TimerActivity.class);
        intent.setAction("foo");
        intent.putExtra("subject", subject);
        intent.putExtra("startTime", startTime+"");
        intent.putExtra("buff", timeSwapBuff+"");
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0, intent, PendingIntent.FLAG_CANCEL_CURRENT);


        int notifyID = 1;
        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle(subject)
                .setContentText(timerValue)
                .setSmallIcon(R.drawable.clock_icon)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setOngoing(true);



        Notification n;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            n = builder.build();
        } else {
            n = builder.getNotification();
        }

        n.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;

        mNotificationManager.notify(notifyID, n);

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
