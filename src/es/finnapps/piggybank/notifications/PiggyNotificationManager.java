package es.finnapps.piggybank.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.inject.Inject;

import es.finnapps.piggybank.R;
import es.finnapps.piggybank.contacts.ContactsProvider;

public class PiggyNotificationManager {

    private static final String TAG = PiggyNotificationManager.class.getName();

    private static final String RAW_FILES_PATH = "android.resource://es.finnapps.piggybank/";
    private static final int NOTIFICATION_INCALL = 0;
    private static final int NOTIFICATION_MESSAGES = 1;
    private static final int NOTIFICATION_LOGOUT = 2;
    private static final int LED_ON_TIME = 300;
    private static final int LED_OFF_TIME = 1000;

    private int notificationCount = 1;

    private NotificationManager notificationManager;
    private Context context;
    @Inject private ContactsProvider contactsProvider;

    public PiggyNotificationManager(Context context) {
        this.notificationManager = ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE));
        this.context = context;
    }

    public void cancelAll() {
        Log.d(TAG, "cancelAll");
        notificationManager.cancelAll();
        clearPendingNotifications();
    }

    public void cancelInCallNotifications() {
        Log.d(TAG, "cancelInCallNotifications");
        notificationManager.cancel(NOTIFICATION_INCALL);
    }

    public void cancelMessagesNotifications() {
        Log.d(TAG, "cancelMessagesNotifications");
        notificationManager.cancel(NOTIFICATION_MESSAGES);
        clearPendingNotifications();
    }

    private void clearPendingNotifications() {
        notificationCount = 1;
    }

    public void showNotificationMessage(String phoneNumber, String message) {
        Log.d(TAG, "showNotificationMessage");

        String name = contactsProvider.getContactNameWihtNumber(phoneNumber);
        Notification notification = createNotificationWithSound(name, message, R.drawable.ic_launcher);
        setContentView(notification, name, message);

        Intent intent = null;
//        = new Intent(context, x.class)

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        notification.contentIntent = contentIntent;
        notificationManager.notify(NOTIFICATION_MESSAGES, notification);
    }

    private Notification createNotificationWithSound(String title, String message, int icon) {
        Notification notification = createNotification(title, message, icon);

//        notification.sound = Uri.parse(RAW_FILES_PATH + R.raw.messages_in);
        notification.ledARGB = Color.RED;
        notification.ledOnMS = LED_ON_TIME;
        notification.ledOffMS = LED_OFF_TIME;
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.number = (notificationCount == 1) ? 0 : notificationCount;
        notificationCount++;

        return notification;
    }

    private Notification createNotification(String title, String message, int icon) {
        Notification notification = new Notification();
        notification.icon = icon;
        notification.tickerText = title;
        notification.when = System.currentTimeMillis();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        return notification;
    }

    private void setContentView(Notification notification, String title, String message) {
//        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification);
//        contentView.setImageViewResource(R.id.image, R.drawable.ic_launcher);
//        contentView.setTextViewText(R.id.title, title);
//        contentView.setTextViewText(R.id.text, message);

//        notification.contentView = contentView;
    }
}
