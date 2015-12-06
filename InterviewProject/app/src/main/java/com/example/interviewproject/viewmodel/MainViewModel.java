package com.example.interviewproject.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.interviewproject.dbUtil.PersonDataSource;
import com.example.interviewproject.model.Person;
import com.example.interviewproject.view.activity.PersonActivity;
import com.example.interviewproject.view.dialog.DownloadDialog;

import java.sql.SQLException;

/**
 * Created by jinyoung on 2015-12-04.
 */
public class MainViewModel {

    private Context mContext;
    private Activity mActivity;
    private PersonDataSource personDataSource;


    public MainViewModel(Context context, Activity activity){
        mContext = context;
        mActivity = activity;

        personDataSource = new PersonDataSource(context);
        try {
            personDataSource.open();
        } catch (SQLException e) {
            Log.i("jinyoung", "Database open exception");
        }
    }

    public void onClickDownloadButton(View view){
        DownloadDialog dialog = new DownloadDialog();
        dialog.show(mActivity.getFragmentManager(), "donwloading");

        for(int i=0;i<10;i++){
            Person person = new Person();
            personDataSource.insertPerson(person);
        }

        dialog.dismiss();
        Toast.makeText(mContext, "Download 완료", Toast.LENGTH_SHORT).show();
    }

    public void onClickShowButton(View view){
        mContext.startActivity(new Intent(mContext, PersonActivity.class));
    }

}
