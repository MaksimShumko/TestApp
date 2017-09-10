package com.example.maksim.testapp.details.presenters;

import com.example.maksim.testapp.details.contracts.ModelFormViewContract;
import com.example.maksim.testapp.details.models.GitHubUserDescription;
import com.example.maksim.testapp.details.DescriptionModel;

/**
 * Created by Maksim on 2017-07-16.
 */

public class DetailsPresenter implements ModelFormViewContract.Presenter {
    private ModelFormViewContract.View view;
    private ModelFormViewContract.Model model;

    public DetailsPresenter(ModelFormViewContract.View view) {
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
