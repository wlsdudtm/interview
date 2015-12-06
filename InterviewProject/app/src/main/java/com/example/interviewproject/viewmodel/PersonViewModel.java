package com.example.interviewproject.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.util.Log;
import android.view.View;

import com.example.interviewproject.dbUtil.PersonDataSource;
import com.example.interviewproject.model.Person;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by jinyoung on 2015-12-04.
 */
public class PersonViewModel extends BaseObservable{

    private Context mContext;
    private DataListener mDataListener;

    private String personCountText;

    private int recyclerViewVisibility;
    private PersonDataSource personDataSource;
    private Subscription subscription;

    public PersonViewModel(Context context){
        mContext = context;

        personDataSource = new PersonDataSource(context);
        try {
            personDataSource.open();
        } catch (SQLException e) {
            Log.i("jinyoung", "Database open exception");
        }

        getPersonList();
    }

    public void setDataListener(DataListener DataListener) {
        mDataListener = DataListener;
    }


    public String getPersonCountText(){
        notifyChange();
        return "총 "+personDataSource.countPerson()+"명의 사용자가 있습니다.";
    }


    public int getRecyclerViewVisibility() {
        return recyclerViewVisibility;
    }

    public interface DataListener{
        void onPersonListChanged(List<Person> personList, int womanPosition);
    }

    public void getPersonList(){
        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
        subscription = personDataSource.getPersonList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Person>>() {

                    List<Person> personList = Collections.emptyList();
                    @Override
                    public void onCompleted() {
                        if(mDataListener != null){
                            mDataListener.onPersonListChanged(personList, personDataSource.countMan()+1);

                            if(!personList.isEmpty()){
                                recyclerViewVisibility = View.VISIBLE;
                            }else{
                                Log.i("jinyoung", "no User");
                            }
                        }
                        notifyChange();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("jinyoung", "onError");
                    }

                    @Override
                    public void onNext(List<Person> persons) {
                        personList = persons;
                    }
                });
    }
}
