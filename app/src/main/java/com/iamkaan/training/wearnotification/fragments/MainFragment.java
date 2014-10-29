package com.iamkaan.training.wearnotification.fragments;

import android.app.Fragment;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.iamkaan.training.wearnotification.R;
import com.iamkaan.training.wearnotification.receiver.NotificationActionReceiver;

public class MainFragment extends Fragment implements View.OnClickListener {

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ImageView notifyButton = (ImageView) rootView.findViewById(R.id.watchButton);
        notifyButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        //we will use this ID to cancel notification when it's needed
        int notificationId = 2952;

        //this NotificationManagerCompat object will be used to send the notification
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(getActivity());

        //notification will be built by using this Builder object
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getActivity())
                        //setting small icon which will be shown on notification bar
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(getString(R.string.title_text))
                        /*
                        this text will be shown when notification is collapsed
                        and won't be shown on wear because we'll put big text later
                         */
                        .setContentText(getString(R.string.short_description));

        //intent for notification content click
        Intent viewIntent = new Intent(getActivity(), NotificationActionReceiver.class);

        //setting action which will be used to understand which action user clicked
        viewIntent.setAction(getString(R.string.action_content))
                //this flag finishes previous activities to not open same activity several times
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        /*
        PendingIntent object will be used by notificationBuilder to add action to the notification
        one important thing here is FLAG_ONE_SHOT flag. It lets us have several PendingIntents
        in one notification
        */
        PendingIntent viewPendingIntent =
                PendingIntent.getBroadcast(getActivity(), 0, viewIntent, PendingIntent.FLAG_ONE_SHOT);

        /*
        setContentIntent method adds action for notification click.
        it also will be the "Open on phone" action on Android Wear notification
        */
        notificationBuilder.setContentIntent(viewPendingIntent);

        //intent for like action
        Intent likeIntent = new Intent(getActivity(), NotificationActionReceiver.class);
        likeIntent.setAction(getString(R.string.action_like))
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent likePendingIntent =
                PendingIntent.getBroadcast(getActivity(), 0, likeIntent, PendingIntent.FLAG_ONE_SHOT);

        //adding like action with icon, title and PendingIntent
        notificationBuilder.addAction(R.drawable.heart,
                getString(R.string.like_action_text), likePendingIntent);

        /*
        intent for like action (this will be shown only on Android Wear notification)
        and after this will be added, like action won't be shown on Android Wear notification
        */
        Intent wearOnlyIntent = new Intent(getActivity(), NotificationActionReceiver.class);
        wearOnlyIntent.setAction(getString(R.string.action_wear))
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent wearOnlyPendingIntent =
                PendingIntent.getBroadcast(getActivity(), 0, wearOnlyIntent, PendingIntent.FLAG_ONE_SHOT);

        //create a separate action for Android Wear with icon, title and PendingIntent
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.bell128,
                        getString(R.string.wear_action_text), wearOnlyPendingIntent)
                        .build();

        //create second page for wear
        NotificationCompat.BigTextStyle secondPageStyle = new NotificationCompat.BigTextStyle();
        secondPageStyle.setBigContentTitle(getString(R.string.secon_page_title))
                .bigText(getString(R.string.second_page_big_text));

        //create notification to add second page
        Notification secondPageNotification =
                new NotificationCompat.Builder(getActivity())
                        .setStyle(secondPageStyle)
                        .build();

        //WearableExtender object will be used for wearable only action
        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender()
                        .addAction(action)
                        //if you set this true, your notification icon won't be shown on Wear
                        .setHintHideIcon(false)
                        //adding one more page for Android Wear notification to add more details
                        .addPage(secondPageNotification)
                        //setting a background for Android Wear notification
                        .setBackground(BitmapFactory
                                .decodeResource(getResources(), R.drawable.wear_background));

        //extending notificationBuilder from WearableExtender object to customize it for Android Wear
        notificationBuilder.extend(wearableExtender);

        //creating the BigTextStyle for the first Android Wear notification page and expanded notification
        NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
        bigStyle.bigText(getString(R.string.long_description));

        //and setting BigTextStyle as notificationBuilder's style
        notificationBuilder.setStyle(bigStyle);

        //finally, sending the notification with it's ID and built notification!
        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}
