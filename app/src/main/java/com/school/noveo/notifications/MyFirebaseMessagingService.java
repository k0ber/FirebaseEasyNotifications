package com.school.noveo.notifications;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options

        Log.d(TAG, "From: " + ((remoteMessage == null) ? "null" : remoteMessage.getFrom()));
        final String body = remoteMessage == null ? null : remoteMessage.getNotification() == null ? null :
                remoteMessage.getNotification().getBody();
        Log.d(TAG, "Notification Message Body: " + ((body == null) ? "null" : body));

        NotificationUtils.sendNotification(this);
    }

}