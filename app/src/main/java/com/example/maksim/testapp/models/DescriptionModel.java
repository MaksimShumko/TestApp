package com.example.maksim.testapp.models;

import com.example.maksim.testapp.contracts.ModelFormViewContract;
import com.example.maksim.testapp.github.ExecuteRequest;

/**
 * Created by Maksim on 2017-08-30.
 */

public class DescriptionModel implements ModelFormViewContract.Model, ExecuteRequest.OnUserLoaderCompleted<GitHubUserDescription> {
    private ModelFormViewContract.Presenter presenter;

    public DescriptionModel(ModelFormViewContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void executeGetUserDescription(String userLogin) {
        new ExecuteRequest().getUserDescription(userLogin, this);
    }

    @Override
    public void onUserLoaderCompleted(GitHubUserDescription gitHubUserDescription) {
        presenter.onExecuteResult(gitHubUserDescription);
    }
}
