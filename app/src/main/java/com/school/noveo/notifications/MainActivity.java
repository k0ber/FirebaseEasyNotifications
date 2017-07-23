package com.school.noveo.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String FCM_ALL_TOPIC = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View localNotificationButton = findViewById(R.id.send_button);
        localNotificationButton.setOnClickListener((v) -> NotificationUtils.sendNotification(this));

        showFirebaseLogs();
        FirebaseMessaging.getInstance().subscribeToTopic(FCM_ALL_TOPIC);
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        CharSequence messageText = null;
        if (remoteInput != null) {
            messageText = remoteInput.getCharSequence(NotificationUtils.KEY_TEXT_REPLY);
        }
        if (messageText != null) {
            Toast.makeText(this, messageText, Toast.LENGTH_SHORT).show();
            NotificationUtils.cancelReplyNotification(this);
        }
    }

    private void showFirebaseLogs() {
        Log.d(TAG, "Token: " + FirebaseInstanceId.getInstance().getToken());
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
    }

}
