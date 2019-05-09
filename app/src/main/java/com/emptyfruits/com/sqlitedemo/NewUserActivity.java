package com.emptyfruits.com.sqlitedemo;

import android.content.ContentValues;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.emptyfruits.com.sqlitedemo.database.UserSQLiteOpenHelper;
import com.emptyfruits.com.sqlitedemo.databinding.ActivityNewUserBinding;

import java.lang.ref.WeakReference;

public class NewUserActivity extends AppCompatActivity {
    ActivityNewUserBinding newUserBinding;
    private boolean editMode;
    private User userToEdit;
    public static final String EDIT_MODE = "edit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newUserBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_new_user);

        if (editMode = getIntent().getBooleanExtra(EDIT_MODE, false)) {
            userToEdit = (User) getIntent().getExtras().get(User.TABLE_NAME);
            newUserBinding.saveBtn.setText("Save Edit".toUpperCase());
            if (userToEdit.getGender().equals("Male"))
                newUserBinding.maleRadioBtn.setChecked(true);
            else newUserBinding.femaleRadioBtn.setChecked(true);
            newUserBinding.newAge.setText(userToEdit.getAge());
            newUserBinding.newName.setText(userToEdit.getName());
            newUserBinding.newEmail.setText(userToEdit.getEmail());
            newUserBinding.newMobile.setText(userToEdit.getMobile());
        }
    }

    public void saveUser(View view) {
        String name = newUserBinding.newName.getText().toString();
        String email = newUserBinding.newEmail.getText().toString();
        String age = newUserBinding.newAge.getText().toString();
        String mobile = newUserBinding.newMobile.getText().toString();
        String gender = (newUserBinding.genderGroup.getCheckedRadioButtonId()
                == R.id.male_radio_btn ? "Male" : "Female");
        ContentValues contentValues = new ContentValues();
        if (editMode) {
            contentValues.put(User._ID, userToEdit.getId());
        }
        contentValues.put(User.NAME, name);
        contentValues.put(User.EMAIL, email);
        contentValues.put(User.AGE, age);
        contentValues.put(User.MOBILE, mobile);
        contentValues.put(User.GENDER, gender);
        NewUserAsyncTask newUserAsyncTask = new NewUserAsyncTask(new WeakReference<>(this));
        newUserAsyncTask.execute(contentValues);
    }

    private static class NewUserAsyncTask extends AsyncTask<ContentValues, Void, Long> {
        private WeakReference<NewUserActivity> newUserActivityWeakReference;

        public NewUserAsyncTask(WeakReference<NewUserActivity> newUserActivityWeakReference) {
            this.newUserActivityWeakReference = newUserActivityWeakReference;
        }

        @Override
        protected void onPostExecute(Long insertionResult) {
            super.onPostExecute(insertionResult);
            NewUserActivity newUserActivity = newUserActivityWeakReference.get();
            if (insertionResult > 0)
                Toast.makeText(newUserActivity, "Insertion Successful!!!!"
                        , Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(newUserActivity, "Insertion Unsuccessful!!!!"
                        , Toast.LENGTH_SHORT).show();

            newUserActivity.startActivity(new Intent(newUserActivity, MainActivity.class));
            newUserActivity.finish();
        }

        @Override
        protected Long doInBackground(ContentValues... values) {
            NewUserActivity newUserActivity = newUserActivityWeakReference.get();
            if (newUserActivity.editMode)
                return (long) (UserSQLiteOpenHelper
                        .getUserSqliteOpenHelper(newUserActivityWeakReference.get())
                        .updateTable(values[0]));
            else
                return UserSQLiteOpenHelper
                        .getUserSqliteOpenHelper(newUserActivityWeakReference.get())
                        .insertData(values[0]);
        }

    }
}
