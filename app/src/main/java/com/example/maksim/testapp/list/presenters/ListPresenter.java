package com.example.maksim.testapp.list.presenters;

import android.util.Log;

import com.example.maksim.testapp.list.data.GitHubUser;
import com.example.maksim.testapp.list.fragments.ViewInterface;
import com.example.maksim.testapp.list.models.ListModel;
import com.example.maksim.testapp.room.RoomSqlDatabase;

import java.util.List;

/**
 * Created by Maksim on 2017-07-10.
 */

public class ListPresenter implements ListPresenterInterface {
    private final String LOG_TAG = "ListPresenter";
    private ViewInterface view;
    private ListModel model;

    public ListPresenter(ViewInterface view, RoomSqlDatabase roomSqlDatabase) {
        this.view = view;
        model = new ListModel(this, roomSqlDatabase);
    }

    @Override
    public void executeSearchRequest(String userLogin) {
        if(model != null) {
            model.executeSearchUsers(userLogin);
        }
    }

    @Override
    public List<GitHubUser> getUsers() {
        if(model != null) {
            return model.getAllGitHubUsers();
        }
        return null;
    }

    @Override
    public void onResponse(List<GitHubUser> gitHubUser) {
        view.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(String userLogin) {
        Log.e(LOG_TAG, "OnItemClick");
        if(view != null)
            view.onItemClick(userLogin);
    }
}
