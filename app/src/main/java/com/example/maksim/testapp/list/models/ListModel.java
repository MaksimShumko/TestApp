package com.example.maksim.testapp.list.models;

import com.example.maksim.testapp.list.models.repository.LocalGetAllUsers;
import com.example.maksim.testapp.list.models.repository.LocalInsertUsers;
import com.example.maksim.testapp.list.models.repository.RemoteListStore;
import com.example.maksim.testapp.list.presenters.ListPresenterInterface;
import com.example.maksim.testapp.room.GitHubUserDao;
import com.example.maksim.testapp.room.RoomSqlDatabase;
import com.example.maksim.testapp.list.data.GitHubUser;
import com.example.maksim.testapp.list.data.GitHubUsers;
import com.example.maksim.testapp.github_api.ExecuteRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maksim on 2017-07-10.
 */

public class ListModel implements LocalRepositoryListener {
    private List<GitHubUser> gitHubUsers = new ArrayList<>();
    private ListPresenterInterface presenter;
    private LocalGetAllUsers localGetAllUsers;
    private RemoteListStore remoteListStore;
    private GitHubUserDao gitHubUserDao;

    public ListModel(ListPresenterInterface presenter, RoomSqlDatabase roomSqlDatabase) {
        this.presenter = presenter;
        this.gitHubUserDao = roomSqlDatabase.getUserDao();
        remoteListStore = new RemoteListStore();
    }

    public List<GitHubUser> getAllGitHubUsers() {
        if (gitHubUsers.size() > 0)
            new LocalGetAllUsers(this, gitHubUserDao).execute();
        return gitHubUsers;
    }

    public void executeSearchUsers(String userName) {
        if (userName != null && !userName.isEmpty())
            new ExecuteRequest().searchUsers(userName, gitHubUsersListener);
        else
            new ExecuteRequest().getUsers(listOfUsersListener);
    }

    private void onUserLoaderCompleted(List<GitHubUser> gitHubUsers) {
        this.gitHubUsers = gitHubUsers;
        new LocalInsertUsers(gitHubUsers, gitHubUserDao).execute();
        presenter.onResponse(gitHubUsers);
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
        onUserLoaderCompleted(users);
    }
}
