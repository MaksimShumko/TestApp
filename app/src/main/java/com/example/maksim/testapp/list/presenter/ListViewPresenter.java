package com.example.maksim.testapp.list.presenter;

import com.example.maksim.testapp.githubapi.room.RoomSqlDatabase;
import com.example.maksim.testapp.list.fragment.ListViewInterface;
import com.example.maksim.testapp.list.model.ListModel;
import com.example.maksim.testapp.list.model.data.GitHubUser;

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
        view.startSwipeRefresh();
        model = new ListModel(this, roomSqlDatabase, view.getPrefSearchQuery());
    }

    @Override
    public void onSearchQueryChanged() {
        if(model != null) {
            view.startSwipeRefresh();
            model.executeSearchUsers(view.getPrefSearchQuery());
        }
    }

    @Override
    public void isLastElement() {
        if(model != null) {
            view.startSwipeRefresh();
            model.executeGetNextPage(view.getPrefSearchQuery());
        }
    }

    @Override
    public void onSwipeRefresh() {
        if(model != null)
            model.executeSearchUsers(view.getPrefSearchQuery());
    }

    @Override
    public void onResponse(List<GitHubUser> gitHubUser, boolean addElements) {
        view.onDataChanged(gitHubUser, addElements);
        view.stopSwipeRefresh();
    }

    @Override
    public boolean isNetworkAvailable() {
        boolean isNetworkAvailable = view.isNetworkAvailable();
        if (!isNetworkAvailable) {
            view.showNetworkErrorToast();
            view.stopSwipeRefresh();
        }
        return isNetworkAvailable;
    }

    @Override
    public void onFailureRequest(String message) {
        view.showRequestErrorToast(message);
        view.stopSwipeRefresh();
    }
}
