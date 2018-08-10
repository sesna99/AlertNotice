package trycatch.ex.alertnotice.service;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

import trycatch.ex.alertnotice.R;
import trycatch.ex.alertnotice.activity.SplashActivity;

import static android.support.v4.app.NotificationCompat.PRIORITY_HIGH;
import static android.support.v4.app.NotificationCompat.VISIBILITY_PUBLIC;

/**
 * Created by trycatch on 2018. 5. 11..
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");
        String id = remoteMessage.getData().get("id");
        String provider = remoteMessage.getData().get("provider");
        sendNotification(title, body, id, provider);
    }

    private void sendNotification(String title, String body, String id, String provider) {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("id", id);
        intent.putExtra("provider", provider);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notice_icon)
                .setContentTitle(title)
                .setContentText(body)
                .setVisibility(VISIBILITY_PUBLIC)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setPriority(PRIORITY_HIGH)
                .setVibrate(new long[]{1000, 1000})
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());

        String packageName;
            ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> rtList = am.getRunningTasks(1);
            ActivityManager.RunningTaskInfo rt = rtList.get(0);
            packageName = rt.topActivity.getClassName();

        if(packageName.contains(getPackageName()))
            notificationManager.cancel(0);
    }
}
