package com.iamkaan.training.wearnotification.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

import com.iamkaan.training.wearnotification.MainActivity;
import com.iamkaan.training.wearnotification.R;

public class NotificationActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String clickedAction = intent.getAction();

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);

        //We remove our notification after user clicks any action
        notificationManager.cancel(2952);

        Intent intentMain = new Intent(context, MainActivity.class);

        /*
        everything we do in "if"s and "else"s are same here, but you could do something else
        so, i'm using one using one activity and 3 fragments but there could be services,
        other activities or just actions..
        */
        if (clickedAction.equals(context.getString(R.string.action_content))) {
            intentMain.putExtra(context.getString(R.string.clicked_action), clickedAction);
        } else if (clickedAction.equals(context.getString(R.string.action_like))) {
            intentMain.putExtra(context.getString(R.string.clicked_action), clickedAction);
        } else if (clickedAction.equals(context.getString(R.string.action_wear))) {
            intentMain.putExtra(context.getString(R.string.clicked_action), clickedAction);
        }

        //FLAG_ACTIVITY_NEW_TASK
        intentMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intentMain);

    }
}
