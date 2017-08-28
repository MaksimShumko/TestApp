package com.example.maksim.testapp.presenters;

import com.example.maksim.testapp.contracts.ModelFormViewContract;
import com.example.maksim.testapp.contracts.ModelListViewContract;
import com.example.maksim.testapp.models.GitHubUser;
import com.example.maksim.testapp.models.Model;

/**
 * Created by Maksim on 2017-07-16.
 */

public class ModelFormPresenter implements ModelFormViewContract.Presenter {
    private ModelFormViewContract.View view;
    private ModelListViewContract.Model model;

    public ModelFormPresenter(ModelFormViewContract.View view) {
        this.view = view;
        model = Model.getInstance();
    }

    @Override
    public GitHubUser getModel(int position) {
        if(model != null)
            return model.getGitHubUser(position);
        return null;
    }

    @Override
    public void onItemClick(int position) {

    }

}
