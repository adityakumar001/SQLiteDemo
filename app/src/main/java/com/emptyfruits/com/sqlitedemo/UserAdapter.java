package com.emptyfruits.com.sqlitedemo;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.emptyfruits.com.sqlitedemo.database.UserSQLiteOpenHelper;
import com.emptyfruits.com.sqlitedemo.databinding.UserBinding;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context mContext;
    private ArrayList<User> mUsers;


    public UserAdapter(Context context, ArrayList<User> users) {
        this.mContext = context;
        this.mUsers = users;
    }

    public UserAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View userView = LayoutInflater.from(mContext).inflate(R.layout.user, viewGroup,
                false);
        return new UserViewHolder(userView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i) {
        userViewHolder.userBinding.setUser(mUsers.get(i));
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private static final int DELETE_ID = 122, EDIT_ID = 123;
        public UserBinding userBinding;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnCreateContextMenuListener(this);
            userBinding = DataBindingUtil.bind(itemView);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(getAdapterPosition(), DELETE_ID, 0, mContext.getString(R.string.delete_user))
                    .setOnMenuItemClickListener(this);
            menu.add(getAdapterPosition(), EDIT_ID, 0, mContext.getString(R.string.edit_user))
                    .setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case DELETE_ID:
                    if (UserSQLiteOpenHelper
                            .getUserSqliteOpenHelper(mContext)
                            .deleteData(mUsers.remove(getAdapterPosition())) > 0) {
                        UserAdapter.this.notifyDataSetChanged();
                    }
                    break;
                case EDIT_ID:
                    Intent intent = new Intent(mContext, NewUserActivity.class);
                    intent.putExtra(NewUserActivity.EDIT_MODE, true);
                    intent.putExtra(User.TABLE_NAME, mUsers.get(getAdapterPosition()));
                    mContext.startActivity(intent);
                    break;
            }
            return false;
        }
    }

}
