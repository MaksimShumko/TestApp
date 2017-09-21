package com.example.maksim.testapp.details.presenter;

import com.example.maksim.testapp.details.fragment.DetailsViewInterface;
import com.example.maksim.testapp.details.model.data.GitHubUserDetails;
import com.example.maksim.testapp.details.model.DetailsModel;
import com.example.maksim.testapp.githubapi.room.RoomSqlDatabase;

/**
 * Created by Maksim on 2017-07-16.
 */

public class DetailsPresenter implements DetailsViewPresenterInterface, DetailsModelListener {
    private DetailsViewInterface view;
    private DetailsModel model;

    public DetailsPresenter(DetailsViewInterface view, RoomSqlDatabase roomSqlDatabase) {
        this.view = view;
        model = new DetailsModel(this, roomSqlDatabase, view.getSelectedUserLogin());
        view.startSwipeRefresh();
    }

    @Override
    public void onResponse(GitHubUserDetails userDetails) {
        if(view != null) {
            view.updateView(userDetails);
            view.stopSwipeRefresh();
        }
    }

    @Override
    public void onFailureRequest(String message) {
        view.showRequestErrorToast(message);
        view.stopSwipeRefresh();
    }

    @Override
    public void onUserChanged() {
        if(model != null && view != null) {
            model.loadUserDetails(view.getSelectedUserLogin());
            view.startSwipeRefresh();
        }
    }
}
