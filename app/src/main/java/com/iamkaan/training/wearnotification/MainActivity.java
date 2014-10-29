package com.iamkaan.training.wearnotification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.iamkaan.training.wearnotification.fragments.LikeFragment;
import com.iamkaan.training.wearnotification.fragments.MainFragment;
import com.iamkaan.training.wearnotification.fragments.WearFragment;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        we are getting clickedAction string from extra bundle
        and if there is no bundle, it means activity created with action "ACTION_MAIN"
        */
        String clickedAction;
        if (getIntent().getExtras() == null)
            clickedAction = getIntent().getAction();
        else
            clickedAction = getIntent().getExtras().getString(getString(R.string.clicked_action));

        if (clickedAction.equals(Intent.ACTION_MAIN)) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new MainFragment())
                    .commit();
        } else {
            //replace fragment by considering clicked action
            if (clickedAction.equals(getString(R.string.action_content))) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new MainFragment())
                        .commit();
            } else if (clickedAction.equals(getString(R.string.action_like))) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new LikeFragment())
                        .commit();
            } else if (clickedAction.equals(getString(R.string.action_wear))) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new WearFragment())
                        .commit();
            }
        }
    }
}
