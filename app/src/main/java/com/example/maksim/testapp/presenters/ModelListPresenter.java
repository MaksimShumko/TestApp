package com.example.maksim.testapp.presenters;

import android.util.Log;

import com.example.maksim.testapp.contracts.ModelListViewContract;
import com.example.maksim.testapp.models.GitHubUser;
import com.example.maksim.testapp.models.Model;

import java.util.List;

/**
 * Created by Maksim on 2017-07-10.
 */

public class ModelListPresenter implements ModelListViewContract.Presenter,
        ModelListViewContract.OnItemClickListener{
    private final String LOG_TAG = "ModelListPresenter";
    private ModelListViewContract.View view;
    private ModelListViewContract.Model model;

    public ModelListPresenter(ModelListViewContract.View view) {
        this.view = view;
        model = Model.getInstance(this);
    }

    @Override
    public void loadModels() {
        if(model != null) {
            model.getAllGitHubUsers();
        }
    }

    @Override
    public List<GitHubUser> getAllModels() {
        if(model != null) {
            return model.getAllGitHubUsers();
        }
        return null;
    }

    @Override
    public void notifyDataSetChanged() {
        view.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(GitHubUser gitHubUser) {
        Log.e(LOG_TAG, "OnItemClick");
        if(view != null)
            view.onItemClick(gitHubUser);
    }
}
