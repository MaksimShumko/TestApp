package com.example.maksim.testapp.list.model;

import com.example.maksim.testapp.list.model.repository.LocalGetAllUsers;
import com.example.maksim.testapp.list.model.repository.LocalInsertUsers;
import com.example.maksim.testapp.list.model.repository.LocalListRepositoryListener;
import com.example.maksim.testapp.list.presenter.ModelListener;
import com.example.maksim.testapp.github_api.room.GitHubUserDao;
import com.example.maksim.testapp.github_api.room.RoomSqlDatabase;
import com.example.maksim.testapp.list.model.data.GitHubUser;
import com.example.maksim.testapp.list.model.data.GitHubUsers;
import com.example.maksim.testapp.github_api.ExecuteRequest;

import java.util.List;

/**
 * Created by Maksim on 2017-07-10.
 */

public class ListModel implements LocalListRepositoryListener {
    private ModelListener modelListener;
    private GitHubUserDao gitHubUserDao;
    private String prefSearchQuery;

    public ListModel(ModelListener modelListener, RoomSqlDatabase roomSqlDatabase, String prefSearchQuery) {
        this.modelListener = modelListener;
        this.gitHubUserDao = roomSqlDatabase.getUserDao();
        this.prefSearchQuery = prefSearchQuery;

        new LocalGetAllUsers(this, gitHubUserDao).execute();
    }

    public void executeSearchUsers(String searchQuery) {
        if (searchQuery != null && !searchQuery.isEmpty())
            new ExecuteRequest().searchUsers(searchQuery, gitHubUsersListener);
        else
            new ExecuteRequest().getUsers(listOfUsersListener);
    }

    private void onUserLoaderCompleted(List<GitHubUser> gitHubUsers) {
        new LocalInsertUsers(gitHubUsers, gitHubUserDao).execute();
        modelListener.onResponse(gitHubUsers);
    }

    private ExecuteRequest.OnUserLoaderCompleted<GitHubUsers> gitHubUsersListener =
            new ExecuteRequest.OnUserLoaderCompleted<GitHubUsers>() {
                @Override
                public void onUserLoaderCompleted(GitHubUsers gitHubUsers) {
                    ListModel.this.onUserLoaderCompleted(gitHubUsers.items);
                }
            };

    private ExecuteRequest.OnUserLoaderCompleted<List<GitHubUser>> listOfUsersListener =
            new ExecuteRequest.OnUserLoaderCompleted<List<GitHubUser>>() {
                @Override
                public void onUserLoaderCompleted(List<GitHubUser> gitHubUsers) {
                    ListModel.this.onUserLoaderCompleted(gitHubUsers);
                }
            };

    @Override
    public void onUsersLoaded(List<GitHubUser> users) {
        if (users == null || users.size() <= 0)
            executeSearchUsers(prefSearchQuery);
        else
            modelListener.onResponse(users);
    }
}
