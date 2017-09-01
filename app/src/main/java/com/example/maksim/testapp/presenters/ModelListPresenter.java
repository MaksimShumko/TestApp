package com.example.maksim.testapp.presenters;

import android.util.Log;

import com.example.maksim.testapp.contracts.ModelListViewContract;
import com.example.maksim.testapp.models.GitHubUsers;
import com.example.maksim.testapp.models.ListModel;

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
        model = new ListModel(this);
    }

    @Override
    public void executeSearchRequest(String userLogin) {
        if(model != null) {
            model.executeSearchUsers(userLogin);
        }
    }

    @Override
    public void executeGetUsersRequest() {
        if(model != null) {
            model.executeGetUsers();
        }
    }

    @Override
    public List<GitHubUsers.User> getUsers() {
        if(model != null) {
            return model.getAllGitHubUsers();
        }
        return null;
    }

    @Override
    public void onExecuteResult(List<GitHubUsers.User> user) {
        view.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(String userLogin) {
        Log.e(LOG_TAG, "OnItemClick");
        if(view != null)
            view.onItemClick(userLogin);
    }
}
