package com.VijayAssayCenter.puchnotifications;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
//import android.support.v4.app.NotificationCompat;
//import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.activity.MainActivity;
import com.VijayAssayCenter.activity.UpdateNews;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String ACTION_TYPE_NEWS_UPDATE = "0";
    public static final String ACTION_TYPE_TRADING_RATE_UP_DOWN = "1";
    public static final String ACTION_TYPE_ALERT = "2";
    public static final String ACTION_TYPE_DEFAULT_NOTIFICATION = "3";
    public static final String KEY_NOTIFICATION_TYPE = "notification_type";

    private final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private Map<String, String> data;
    private String notificationMSg = "";
    private String notificationTitle = "";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
            data = remoteMessage.getData();
            Log.e("dasda", data.toString());

//          { "bit": "12" , "title": "Welcome", "body": "After Noon", "newpatten": "true" }
            try {
                if (data.get("bit").equals("1")) {
                    UpdateNotificationType(data.get("body"),data.get("title"),data.get("bit"));
                } else {
                    LiverateNotificationType(data.get("body"), data.get("title"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Log.i(TAG, "Message Notification Body: NULL");
        }
    }

    @Override
    public void onNewToken(String s) {

        super.onNewToken(s);


    }

    private void UpdateNotificationType(String notificationMSg, String notificationTitle,String Bit) {
        this.notificationMSg = notificationMSg;
        this.notificationTitle = notificationTitle;
        if (this.notificationTitle == null) {
            this.notificationTitle = getString(R.string.app_name);
        }
        handelNewsUpdateNotification(Bit);
    }

    private void LiverateNotificationType(String notificationMSg, String notificationTitle) {
        this.notificationMSg = notificationMSg;
        this.notificationTitle = notificationTitle;
        if (this.notificationTitle == null) {
            this.notificationTitle = getString(R.string.app_name);
        }
        LiverateUpdateNotification();
    }

    private void handelNewsUpdateNotification(String Bit) {

        if(Bit.equals("1")){
            Intent intent;
            intent = new Intent(this, UpdateNews.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            sendNotificationNew(intent);
        }else {
            Intent intent;
            intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            sendNotificationNew(intent);
        }


    }

    private void LiverateUpdateNotification() {
        Intent intent;
        intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        sendNotificationNew(intent);
    }

    public void sendNotificationNew(Intent intent) {
        Bitmap rawBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        final int NOTIFY_ID = new Random().nextInt(100000000);
        // ID of notification
        String id = getString(R.string.default_notification_channel_id); // default_channel_id
        String title = getString(R.string.default_notification_channel_title); // default_channel_title
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/mysound.mp3");
        NotificationCompat.Builder builder;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            assert notificationManager != null;
            NotificationChannel mChannel = notificationManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .build();
                mChannel.setSound(uri, audioAttributes);
                notificationManager.createNotificationChannel(mChannel);

            }
            builder = new NotificationCompat.Builder(this, id);

        } else {
            builder = new NotificationCompat.Builder(this);
            builder.setPriority(Notification.PRIORITY_HIGH);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_IMMUTABLE);
        builder.setSmallIcon(R.mipmap.ic_launcher) // required
                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark))
                .setContentTitle(notificationTitle)
                .setSubText(getString(R.string.app_name))
                .setContentText(Html.fromHtml(notificationMSg))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(Html.fromHtml(notificationMSg)))
                .setLargeIcon(rawBitmap)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.setSound(uri);
        }
        Notification notification = builder.build();
        assert notificationManager != null;
        notificationManager.notify(NOTIFY_ID, notification);
        wakeUPDevice();
    }

    private void wakeUPDevice() {
        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        assert pm != null;
        @SuppressLint("InvalidWakeLockTag")
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        wl.acquire(15000);
        wl.release();
    }
}
