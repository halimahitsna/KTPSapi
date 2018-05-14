package id.sapi.ktp.aplikasiktpsapi.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import id.sapi.ktp.aplikasiktpsapi.R;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }else{
            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");
            JSONObject payload = data.getJSONObject("payload");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);
            Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
                RemoteViews expandedView = new RemoteViews(getApplicationContext().getPackageName(), R.layout.custom_notifications);
                expandedView.setTextViewText(R.id.timestamp, DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
                expandedView.setTextViewText(R.id.notification_message,message);
                // adding action to left button
                Intent leftIntent = new Intent(getApplicationContext(), NotificationIntentService.class);
                leftIntent.setAction("left");
                expandedView.setOnClickPendingIntent(R.id.left_button, PendingIntent.getService(getApplicationContext(), 0, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT));
                // adding action to right button
                Intent rightIntent = new Intent(getApplicationContext(), NotificationIntentService.class);
                rightIntent.setAction("right");
                expandedView.setOnClickPendingIntent(R.id.right_button, PendingIntent.getService(getApplicationContext(), 1, rightIntent, PendingIntent.FLAG_UPDATE_CURRENT));

                RemoteViews collapsedView = new RemoteViews(getApplicationContext().getPackageName(), R.layout.view_collapsed_notification);
                collapsedView.setTextViewText(R.id.timestamp, DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                        // these are the three things a NotificationCompat.Builder object requires at a minimum
                        .setSmallIcon(R.drawable.sapi2)
                        .setContentTitle(title)
                        .setContentText(message)
                        // notification will be dismissed when tapped
                        .setAutoCancel(true)
                        // tapping notification will open MainActivity
                        .setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), MainActivity.class), 0))
                        // setting the custom collapsed and expanded views
                        .setCustomContentView(collapsedView)
                        .setCustomBigContentView(expandedView)
                        // setting style to DecoratedCustomViewStyle() is necessary for custom views to display
                        .setStyle(new android.support.v7.app.NotificationCompat.DecoratedCustomViewStyle());

                // retrieves android.app.NotificationManager
                NotificationManager notificationManager = (android.app.NotificationManager)getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(90, builder.build());

            } else {
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                resultIntent.putExtra("message", message);

                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
                showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
       notificationUtils.showNotificationMessage(title, message, timeStamp, intent);

    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}
