package com.iamkaan.training.wearnotification.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iamkaan.training.wearnotification.R;

public class WearFragment extends Fragment {

    public WearFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_text, container, false);

        TextView contentText = (TextView) rootView.findViewById(R.id.content_text);
        contentText.setText(getString(R.string.wear_fragment_text));

        return rootView;
    }
}
