package com.example.interviewproject.view.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.interviewproject.R;

/**
 * Created by jinyoung on 2015-12-04.
 */
public class DownloadDialog extends DialogFragment {

    public DownloadDialog() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.download_dialog, container);

        return view;
    }

}
