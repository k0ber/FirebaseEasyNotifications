package com.school.noveo.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.support.v4.content.ContextCompat;
import android.widget.RemoteViews;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;


class NotificationUtils {

    static final String KEY_TEXT_REPLY = "reply";
    private static final String KEY_PRESSED_ACTION = "reply_action";
    private static final String LABEL_REPLY = "Reply";

    private static final int SIMPLE_NOTIFICATION_ID = 550;
    private static final int BIG_TEXT_NOTIFICATION_ID = 551;
    private static final int BIG_PICTURE_NOTIFICATION_ID = 552;
    private static final int INBOX_NOTIFICATION_ID = 553;
    private static final int MEDIA_NOTIFICATION_ID = 554;
    private static final int REPLY_NOTIFICATION_ID = 555;
    private static final int PROGRESS_NOTIFICATION_ID = 556;
    private static final int CUSTOM_NOTIFICATION_ID = 557;


    static void sendNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(SIMPLE_NOTIFICATION_ID, buildSimpleNotification(context));
//        notificationManager.notify(BIG_TEXT_NOTIFICATION_ID, buildBigTextNotification(context));
//        notificationManager.notify(BIG_PICTURE_NOTIFICATION_ID, buildBigPictureNotification(context));
//        notificationManager.notify(INBOX_NOTIFICATION_ID, buildInboxNotification(context));
//        notificationManager.notify(MEDIA_NOTIFICATION_ID, buildMediaNotification(context));
//        notificationManager.notify(REPLY_NOTIFICATION_ID, buildRemoteInputNotification(context));
//        showProgressNotification(context);
//        showCustomNotification(context);
    }

    static void cancelReplyNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(REPLY_NOTIFICATION_ID);
    }

    private static Notification buildSimpleNotification(Context context) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        return new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_email)
//                .setContentTitle("Title")
//                .setContentText("Message")
//                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.image))
//                .setColor(ContextCompat.getColor(context, R.color.colorAccent))
//                .setSound(defaultSoundUri)
//                .setContentIntent(buildOpenAppPendingIntent(context))
//                .setAutoCancel(true)
//                .addAction(new NotificationCompat.Action(R.mipmap.ic_launcher, "Open", buildOpenAppPendingIntent(context)))
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setPriority(Notification.PRIORITY_HIGH)
                .build();
    }

//    private static Notification buildBigTextNotification(Context context) {
//        return new NotificationCompat.Builder(context)
//                .setSmallIcon(android.R.drawable.ic_dialog_email)
//                .setContentTitle("Title")
//                .setContentText("Message")
//                .setStyle(new NotificationCompat.BigTextStyle()
//                        .bigText(context.getString(R.string.bigText)))
//                .build();
//    }

    private static Notification buildBigPictureNotification(Context context) {
        return new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .setContentText("Message")
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(context.getResources(), R.drawable.image)))
                .build();
    }

    private static Notification buildInboxNotification(Context context) {
        return new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .setContentTitle("Title")
                .setContentText("Message")
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine("Hello")
                        .addLine("Where are you?")
                        .addLine("wtf?")
                        .setBigContentTitle("3 messages from Kate")
                        .setSummaryText("+1 more"))
                .build();
    }

    private static Notification buildMediaNotification(Context context) {
        return new android.support.v7.app.NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .setContentTitle("Title")
                .setContentText("Message")
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.image))
                .addAction(new NotificationCompat.Action(android.R.drawable.ic_input_add, "Add", buildOpenAppPendingIntent(context)))
                .addAction(new NotificationCompat.Action(android.R.drawable.ic_delete, "Delete", buildOpenAppPendingIntent(context)))
                .addAction(new NotificationCompat.Action(android.R.drawable.ic_dialog_info, "Info", buildOpenAppPendingIntent(context)))
                .addAction(new NotificationCompat.Action(android.R.drawable.ic_dialog_alert, "Alert", buildOpenAppPendingIntent(context)))
                .setStyle(new android.support.v7.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1))
                .build();
    }

    private static Notification buildRemoteInputNotification(Context context) {
        return new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .setContentTitle("Title")
                .setContentText("Message")
                .addAction(buildReplyAction(context))
                .build();
    }

    private static void showProgressNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setContentTitle("Download content")
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setAutoCancel(false)
                .setOngoing(true);

        Observable.interval(0, 300, TimeUnit.MILLISECONDS)
                .take(101)
                .doOnNext((value) -> {
                    int progress = value.intValue();
                    notificationBuilder.setContentText("Progress " + value + "%");
                    notificationBuilder.setProgress(100, progress, false);
                    notificationManager.notify(PROGRESS_NOTIFICATION_ID, notificationBuilder.build());
                })
                .doOnComplete(() -> notificationManager.cancel(PROGRESS_NOTIFICATION_ID))
                .subscribe();
    }

    private static void showCustomNotification(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.custom_notification);

            Notification customNotification = new Notification.Builder(context)
                    .setSmallIcon(android.R.drawable.ic_dialog_email)
                    .setCustomContentView(remoteViews)
                    .setStyle(new Notification.DecoratedCustomViewStyle())
                    .build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(CUSTOM_NOTIFICATION_ID, customNotification);
        }
    }

    private static PendingIntent buildOpenAppPendingIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
    }

    private static NotificationCompat.Action buildReplyAction(Context context) {
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel(context.getString(R.string.text_label_reply))
                .build();

        Intent replyIntent = new Intent(context, MainActivity.class)
                .putExtra(KEY_PRESSED_ACTION, LABEL_REPLY)
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 3,
                replyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Action.Builder(android.R.drawable.sym_def_app_icon, LABEL_REPLY, pendingIntent)
                .addRemoteInput(remoteInput)
                .build();
    }

}
