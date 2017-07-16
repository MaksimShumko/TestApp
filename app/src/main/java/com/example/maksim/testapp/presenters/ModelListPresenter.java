package com.example.maksim.testapp.presenters;

import android.util.Log;

import com.example.maksim.testapp.contracts.ModelListViewContract;
import com.example.maksim.testapp.models.Repository;

/**
 * Created by Maksim on 2017-07-10.
 */

public class ModelListPresenter implements ModelListViewContract.Actions,
        ModelListViewContract.OnItemClickListener, ModelListViewContract.ListItem {
    private final String LOG_TAG = "ModelListPresenter";
    private ModelListViewContract.View view;
    private ModelListViewContract.Repository repository;

    public ModelListPresenter(ModelListViewContract.View view) {
        this.view = view;
        repository = new Repository();
    }

    @Override
    public void loadModels() {
        if(repository != null) {
            repository.getAllModels();
        }
    }

    @Override
    public int getCount() {
        if(repository != null)
            return repository.getCount();
        return 0;
    }

    @Override
    public void onItemClick(int position) {
        Log.e(LOG_TAG, "OnItemClick");
        if(view != null)
            view.onItemClick(position);
    }

    @Override
    public String getTitle(int position) {
        if(repository != null && repository.getModel(position) != null)
            return repository.getModel(position).getTitle();
        return "Empty title";
    }

    @Override
    public String getDescription(int position) {
        if(repository != null && repository.getModel(position) != null)
            return repository.getModel(position).getDescription();
        return "Empty description";
    }
}
