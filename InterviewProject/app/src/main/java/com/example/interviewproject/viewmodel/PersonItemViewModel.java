package com.example.interviewproject.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;

import com.example.interviewproject.dbUtil.PersonDataSource;
import com.example.interviewproject.model.Person;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jinyoung on 2015-12-04.
 */
public class PersonItemViewModel extends BaseObservable{

    private Context mContext;
    private Person mPerson;
    private String personInformation;
    private PersonDataSource dataSource;
    private ItemDataListener mDataListener;

    public PersonItemViewModel(Context context, Person person, ItemDataListener dataListener){
        mContext = context;
        mPerson = person;
        mDataListener = dataListener;
        dataSource = new PersonDataSource(context);
        try {
            dataSource.open();
        } catch (SQLException e) {
            Log.i("jinyoung", "database open exception");
        }
    }

    public void setPerson(Person person){
        mPerson = person;
    }

    public String getPersonInformation() {
        notifyChange();
        return mPerson.toString();
    }

    public void onClickDeleteButton(View view){
        Log.i("jinyoung", "onClickDeleteButton : "+mPerson);
        dataSource.deletePerson(mPerson);
        mDataListener.onPersonDeleted(mPerson);
    }

    public interface ItemDataListener{
        void onPersonDeleted(Person person);
    }
}
