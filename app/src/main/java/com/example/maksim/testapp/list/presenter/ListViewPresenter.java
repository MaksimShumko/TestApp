package com.example.maksim.testapp.list.presenter;

import com.example.maksim.testapp.list.model.data.GitHubUser;
import com.example.maksim.testapp.list.fragment.ListViewInterface;
import com.example.maksim.testapp.list.model.ListModel;
import com.example.maksim.testapp.github_api.room.RoomSqlDatabase;

import java.util.List;

/**
 * Created by Maksim on 2017-07-10.
 */

public class ListViewPresenter implements ListViewPresenterInterface, ModelListener {
    private final String LOG_TAG = "ListViewPresenter";
    private ListViewInterface view;
    private ListModel model;

    public ListViewPresenter(ListViewInterface view, RoomSqlDatabase roomSqlDatabase) {
        this.view = view;
        model = new ListModel(this, roomSqlDatabase, view.getPrefSearchQuery());
    }

    @Override
    public void onSearchQueryChanged() {
        if(model != null) {
            model.executeSearchUsers(view.getPrefSearchQuery());
        }
    }

    @Override
    public void onResponse(List<GitHubUser> gitHubUser) {
        view.onDataChanged(gitHubUser);
    }
}
