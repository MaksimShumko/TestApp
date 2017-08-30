package com.example.maksim.testapp.models;

import com.example.maksim.testapp.contracts.ModelListViewContract;
import com.example.maksim.testapp.github.ExecuteRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maksim on 2017-07-10.
 */

public class ListModel implements ModelListViewContract.Model, ExecuteRequest.OnUserLoaderCompleted<GitHubUsers> {
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
    public void executeGetUsers(String userName) {
        new ExecuteRequest().getUsers(userName, this);
    }

    @Override
    public void onUserLoaderCompleted(GitHubUsers gitHubUsers) {
        this.gitHubUserList = gitHubUsers.items;
        presenter.onExecuteResult(gitHubUserList);
    }
}
