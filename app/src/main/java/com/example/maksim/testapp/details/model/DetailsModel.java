package com.example.maksim.testapp.details.model;

import com.example.maksim.testapp.details.model.repository.LocalGetUserDetails;
import com.example.maksim.testapp.details.model.repository.LocalInsertUserDetails;
import com.example.maksim.testapp.details.model.repository.LocalDetailsRepositoryListener;
import com.example.maksim.testapp.details.presenter.DetailsModelListener;
import com.example.maksim.testapp.details.model.data.GitHubUserDetails;
import com.example.maksim.testapp.githubapi.ExecuteRequest;
import com.example.maksim.testapp.githubapi.room.GitHubUserDetailsDao;
import com.example.maksim.testapp.githubapi.room.RoomSqlDatabase;

/**
 * Created by Maksim on 2017-08-30.
 */

public class DetailsModel implements ExecuteRequest.OnUserLoaderCompleted<GitHubUserDetails>,
        LocalDetailsRepositoryListener {
    private DetailsModelListener listener;
    private GitHubUserDetailsDao gitHubUserDetailsDao;
    private String userLogin;

    public DetailsModel(DetailsModelListener listener, RoomSqlDatabase roomSqlDatabase,
                        String userLogin) {
        this.listener = listener;
        this.gitHubUserDetailsDao = roomSqlDatabase.getUserDetailsDao();
        this.userLogin = userLogin;

        new LocalGetUserDetails(this, gitHubUserDetailsDao).execute(userLogin);
    }

    private void executeGetUserDescription(String userLogin) {
        if (userLogin != null && !userLogin.isEmpty())
            new ExecuteRequest().getUserDescription(userLogin, this);
    }

    public void loadUserDetails(String userLogin) {
        this.userLogin = userLogin;
        new LocalGetUserDetails(this, gitHubUserDetailsDao).execute(userLogin);
    }

    @Override
    public void onUserLoaderCompleted(GitHubUserDetails gitHubUserDetails) {
        new LocalInsertUserDetails(gitHubUserDetails, gitHubUserDetailsDao).execute();
        listener.onResponse(gitHubUserDetails);
    }

    @Override
    public void onFailure(String message) {
        listener.onFailureRequest(message);
    }

    @Override
    public void onUserDetailsLoaded(GitHubUserDetails userDetails) {
        if (userDetails == null)
            executeGetUserDescription(userLogin);
        else
            listener.onResponse(userDetails);
    }
}
