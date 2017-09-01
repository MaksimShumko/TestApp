package com.example.maksim.testapp.models;

import com.example.maksim.testapp.contracts.ModelListViewContract;
import com.example.maksim.testapp.github.ExecuteRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maksim on 2017-07-10.
 */

public class ListModel implements ModelListViewContract.Model {
    private List<GitHubUsers.User> gitHubUserList = new ArrayList<>();
    private ModelListViewContract.Presenter presenter;

    public ListModel(ModelListViewContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public List<GitHubUsers.User> getAllGitHubUsers() {
        return gitHubUserList;
    }

    @Override
    public void executeSearchUsers(String userName) {
        new ExecuteRequest().searchUsers(userName, gitHubUsersListener);
    }

    @Override
    public void executeGetUsers() {
        new ExecuteRequest().getUsers(listOfUsersListener);
    }

    private void onUserLoaderCompleted(GitHubUsers gitHubUsers) {
        this.gitHubUserList = gitHubUsers.items;
        presenter.onExecuteResult(gitHubUserList);
    }

    private void onUserLoaderCompleted(List<GitHubUsers.User> gitHubUsers) {
        this.gitHubUserList = gitHubUsers;
        presenter.onExecuteResult(gitHubUserList);
    }

    private ExecuteRequest.OnUserLoaderCompleted<GitHubUsers> gitHubUsersListener =
            new ExecuteRequest.OnUserLoaderCompleted<GitHubUsers>() {
                @Override
                public void onUserLoaderCompleted(GitHubUsers gitHubUsers) {
                    ListModel.this.onUserLoaderCompleted(gitHubUsers);
                }
            };

    private ExecuteRequest.OnUserLoaderCompleted<List<GitHubUsers.User>> listOfUsersListener =
            new ExecuteRequest.OnUserLoaderCompleted<List<GitHubUsers.User>>() {
                @Override
                public void onUserLoaderCompleted(List<GitHubUsers.User> gitHubUsers) {
                    ListModel.this.onUserLoaderCompleted(gitHubUsers);
                }
            };
}
