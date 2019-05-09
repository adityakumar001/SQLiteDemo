package com.emptyfruits.com.sqlitedemo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.emptyfruits.com.sqlitedemo.User;

public class UserSQLiteOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "users.db";
    public static final int DATABASE_VERSION = 1;

    public static UserSQLiteOpenHelper getUserSqliteOpenHelper(Context context) {
        if (mUserSqliteOpenHelper == null) {
            mUserSqliteOpenHelper = new UserSQLiteOpenHelper(context);
            return mUserSqliteOpenHelper;
        }
        return mUserSqliteOpenHelper;
    }

    private static UserSQLiteOpenHelper mUserSqliteOpenHelper;

    private UserSQLiteOpenHelper(Context context) {
        //For default cursor object returned null
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.CREATE_TABLE);
    }

    public Cursor getUserCursor() {
        return getReadableDatabase()
                .query(User.TABLE_NAME, User.ALL_FIELDS, null, null,
                        null, null, User._ID);

    }

    public long insertData(ContentValues contentValues) {
        return getWritableDatabase().insert(User.TABLE_NAME, null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public int deleteData(User user) {
        return getWritableDatabase()
                .delete(User.TABLE_NAME, User._ID + "=?",
                        new String[]{String.valueOf(user.getId())});
    }

    public int updateTable(ContentValues contentValues) {
        return getWritableDatabase().update(User.TABLE_NAME, contentValues, User._ID + "=?",
                new String[]{String.valueOf(contentValues.get(User._ID))});
    }
}

