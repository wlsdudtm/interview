package com.example.interviewproject.dbUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.interviewproject.model.Person;
import com.example.interviewproject.util.RxUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by jinyoung on 2015-12-04.
 */
public class PersonDataSource {

    private MySQLiteHelper dbHelper;
    private SQLiteDatabase database;

    public PersonDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        if(!isDBOpen()){
            database = dbHelper.getWritableDatabase();
        }
    }

    public boolean isDBOpen(){
        return database != null ? true:false;
    }

    public void close(){
        dbHelper.close();
    }

    public boolean insertPerson(Person person){
        ContentValues values = new ContentValues();
        values.put("name", person.getName());
        values.put("age", person.getAge());
        values.put("gender", person.getGender());

        return database.insert("person", null, values)>0?true:false;
    }

    public boolean deletePerson(Person person){
        long id = person.getId();
        Log.i("jinyoung", "delete : " + id);
        return database.delete("person", "_id = " + id, null)>0?true:false;
    }

    public int countPerson(){
        Cursor mCount= database.rawQuery("select count(*) from person", null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();

        return count;
    }

    public int countMan(){
        Cursor mCount= database.rawQuery("select count(*) from person where gender = ?", new String[]{"0"});
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();

        return count;
    }

    public Observable<List<Person>> getPersonList(){
        return RxUtil.makeObservable(getCallablePersonList()).subscribeOn(Schedulers.computation());
    }

    private Callable<List<Person>> getCallablePersonList(){
        return new Callable<List<Person>>() {
            @Override
            public List<Person> call() throws Exception {
                return getAllPersons();
            }
        };
    }

    private List<Person> getAllPersons(){
        List<Person> personList = new ArrayList<Person>();

        Cursor cursor = database.query("person", null, null, null, null, null, "gender asc");

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Person person = cursorToPerson(cursor);
            personList.add(person);
            cursor.moveToNext();
        }
        cursor.close();
        return personList;
    }

    private Person cursorToPerson(Cursor cursor){
        Person person = new Person();
        person.setId(cursor.getLong(0));
        person.setName(cursor.getString(1));
        person.setAge(cursor.getInt(2));
        person.setGender(cursor.getInt(3));
        return person;
    }

}
