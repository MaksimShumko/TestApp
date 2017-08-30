package com.example.maksim.testapp.presenters;

import com.example.maksim.testapp.contracts.ModelFormViewContract;
import com.example.maksim.testapp.models.GitHubUserDescription;
import com.example.maksim.testapp.models.DescriptionModel;

/**
 * Created by Maksim on 2017-07-16.
 */

public class ModelDescriptionPresenter implements ModelFormViewContract.Presenter {
    private ModelFormViewContract.View view;
    private ModelFormViewContract.Model model;

    public ModelDescriptionPresenter(ModelFormViewContract.View view) {
        this.view = view;
        model = new DescriptionModel(this);
    }

    @Override
    public void executeRequest(String userLogin) {
        if(model != null)
            model.executeGetUserDescription(userLogin);
    }

    @Override
    public void onExecuteResult(GitHubUserDescription gitHubUserDescription) {
        if(view != null)
            view.updateView(gitHubUserDescription);
    }
}
