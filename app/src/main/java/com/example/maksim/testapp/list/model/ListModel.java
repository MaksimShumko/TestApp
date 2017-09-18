package com.example.maksim.testapp.list.model;

import com.example.maksim.testapp.list.model.repository.LocalGetAllUsers;
import com.example.maksim.testapp.list.model.repository.LocalInsertUsers;
import com.example.maksim.testapp.list.model.repository.LocalListRepositoryListener;
import com.example.maksim.testapp.list.presenter.ModelListener;
import com.example.maksim.testapp.githubapi.room.GitHubUserDao;
import com.example.maksim.testapp.githubapi.room.RoomSqlDatabase;
import com.example.maksim.testapp.list.model.data.GitHubUser;
import com.example.maksim.testapp.list.model.data.GitHubUsers;
import com.example.maksim.testapp.githubapi.ExecuteRequest;

import java.util.List;

/**
 * Created by Maksim on 2017-07-10.
 */

public class ListModel implements LocalListRepositoryListener {
    private final int countOfElementsOnPage = 30;
    private ModelListener modelListener;
    private GitHubUserDao gitHubUserDao;
    private String prefSearchQuery;
    private int totalCountOfElements;
    private static int currentPage;

    public ListModel(ModelListener modelListener, RoomSqlDatabase roomSqlDatabase, String prefSearchQuery) {
        this.modelListener = modelListener;
        this.gitHubUserDao = roomSqlDatabase.getUserDao();
        this.prefSearchQuery = prefSearchQuery;

        new LocalGetAllUsers(this, gitHubUserDao).execute();
    }

    public void executeSearchUsers(String searchQuery) {
        if (modelListener.isNetworkAvailable()) {
            currentPage = 1;
            if (searchQuery != null && !searchQuery.isEmpty())
                new ExecuteRequest().searchUsers(searchQuery, currentPage, gitHubUsersListener);
            else
                new ExecuteRequest().getUsers(listOfUsersListener);
        }
    }

    public void executeGetNextPage(String searchQuery) {
        if (modelListener.isNetworkAvailable())
            if (currentPage * countOfElementsOnPage <= totalCountOfElements)
                if (searchQuery != null && !searchQuery.isEmpty())
                    new ExecuteRequest().searchUsers(searchQuery, ++currentPage, gitHubUsersListener);
    }

    private void onUserLoaderCompleted(List<GitHubUser> gitHubUsers, boolean addElements) {
        new LocalInsertUsers(gitHubUsers, gitHubUserDao, addElements).execute();
        modelListener.onResponse(gitHubUsers, addElements);
    }

    private ExecuteRequest.OnUserLoaderCompleted<GitHubUsers> gitHubUsersListener =
            new ExecuteRequest.OnUserLoaderCompleted<GitHubUsers>() {
                @Override
                public void onUserLoaderCompleted(GitHubUsers gitHubUsers) {
                    if (gitHubUsers != null) {
                        if (gitHubUsers.totalCount != null)
                            totalCountOfElements = gitHubUsers.totalCount;
                        ListModel.this.onUserLoaderCompleted(gitHubUsers.items, currentPage != 1);
                    }
                }
            };

    private ExecuteRequest.OnUserLoaderCompleted<List<GitHubUser>> listOfUsersListener =
            new ExecuteRequest.OnUserLoaderCompleted<List<GitHubUser>>() {
                @Override
                public void onUserLoaderCompleted(List<GitHubUser> gitHubUsers) {
                    ListModel.this.onUserLoaderCompleted(gitHubUsers, false);
                }
            };

    @Override
    public void onUsersLoaded(List<GitHubUser> users) {
        if (users == null || users.size() <= 0)
            executeSearchUsers(prefSearchQuery);
        else
            modelListener.onResponse(users, false);
    }
}
