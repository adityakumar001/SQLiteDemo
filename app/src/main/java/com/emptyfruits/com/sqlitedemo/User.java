package com.emptyfruits.com.sqlitedemo;

import android.provider.BaseColumns;

import java.io.Serializable;

public class User implements BaseColumns, Serializable {

    static final String NAME = "name";
    static final String EMAIL = "email";
    static final String AGE = "age";
    static final String MOBILE = "mobile";
    static final String GENDER = "gender";
    static final String PROFILE_PHOTO = "profile";
    public static final String TABLE_NAME = "user";
    public static final String _ID = BaseColumns._ID;
    public static final String[] ALL_FIELDS = new String[]{NAME, EMAIL, AGE, MOBILE, GENDER, BaseColumns._ID};
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " ( " + NAME + " TEXT NOT NULL DEFAULT 'Your Name' , "
            + EMAIL + " TEXT NOT NULL DEFAULT 'your_email@email.com' , "
            + MOBILE + " TEXT NOT NULL DEFAULT  '89896434245' , "
            + AGE + " INTEGER NOT NULL DEFAULT  'Your Name' , "
            + GENDER + " TEXT NOT NULL DEFAULT  'Your Name' , "
            + _ID + " INTEGER  NOT NULL DEFAULT  'Your Name' PRIMARY KEY  AUTOINCREMENT )";


    private String name, email, mobile, gender;
    private String age;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = String.valueOf(age);
    }

    public User(String name, String email, String mobile, String gender, int age, int id) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.mobile = mobile;
        this.gender = gender;
        this.age = String.valueOf(age);
    }
}
