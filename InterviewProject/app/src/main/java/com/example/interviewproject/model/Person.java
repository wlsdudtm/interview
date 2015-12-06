package com.example.interviewproject.model;

import android.util.Log;

import java.util.Random;

/**
 * Created by jinyoung on 2015-12-04.
 */
public class Person {

    private long _id;
    private String name;
    private int age;
    private int gender; // 0: man, 1:woman

    public Person(){
        name = getRandomName();
        age = getRandomAge();
        gender = getRandomGender();
    }

    private int getRandomAge(){
        Random random = new Random();
        return random.nextInt(52)+14;
    }

    private int getRandomGender(){
        Random random = new Random();
        return random.nextInt(2);
    }

    private String getRandomName(){
        Random random = new Random();
        int length = random.nextInt(6)+3;
        String name = "";
        for(int i=0;i<length;i++){
            name += (char)(random.nextInt(26)+97);
        }

        return name;
    }

    public long getId() {
        return _id;
    }

    public void setId(long _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return name+"("+(gender==0?'남':'여')+") "+age+"세";
    }
}
