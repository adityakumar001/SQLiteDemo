package com.emptyfruits.com.sqlitedemo;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;

import com.emptyfruits.com.sqlitedemo.database.UserSQLiteOpenHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private UserAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new UserAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void addUser(View view) {
        startActivity(new Intent(this, NewUserActivity.class));
    }


    private static class GetUsersAsyncTask extends AsyncTask<Void, Void, ArrayList<User>> {

        private Cursor mCursor;
        private WeakReference<MainActivity> contextWeakReference;

        GetUsersAsyncTask(WeakReference<MainActivity> contextWeakReference) {
            this.contextWeakReference = contextWeakReference;
        }

        @Override
        protected ArrayList<User> doInBackground(Void... voids) {
            UserSQLiteOpenHelper userSQLiteOpenHelper =
                    UserSQLiteOpenHelper
                            .getUserSqliteOpenHelper(contextWeakReference.get());
            mCursor = userSQLiteOpenHelper.getUserCursor();
            ArrayList<User> users = new ArrayList<>();
            while (mCursor.moveToNext()) {
                String name = mCursor.getString(mCursor.getColumnIndex(User.NAME));
                String email = mCursor.getString(mCursor.getColumnIndex(User.EMAIL));
                int age = mCursor.getInt(mCursor.getColumnIndex(User.AGE));
                String mobile = mCursor.getString(mCursor.getColumnIndex(User.MOBILE));
                String gender = mCursor.getString(mCursor.getColumnIndex(User.GENDER));
                int id = mCursor.getInt(mCursor.getColumnIndex(User._ID));
                users.add(new User(name, email, mobile, gender, age, id));
            }
            userSQLiteOpenHelper.close();
            return users;
        }

        @Override
        protected void onPostExecute(ArrayList<User> users) {
            MainActivity mainActivity = contextWeakReference.get();
            mainActivity.mAdapter = new UserAdapter(mainActivity, users);
            mainActivity.mRecyclerView.setAdapter(mainActivity.mAdapter);
            super.onPostExecute(users);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new GetUsersAsyncTask(new WeakReference<>(this)).execute();
    }
}

