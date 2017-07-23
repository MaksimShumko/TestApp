package com.example.maksim.testapp.presenters;

import android.util.Log;

import com.example.maksim.testapp.contracts.ModelListViewContract;
import com.example.maksim.testapp.models.Model;
import com.example.maksim.testapp.models.Repository;

import java.util.List;

/**
 * Created by Maksim on 2017-07-10.
 */

public class ModelListPresenter implements ModelListViewContract.Presenter,
        ModelListViewContract.OnItemClickListener{
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
    public List<Model> getAllModels() {
        if(repository != null) {
            return repository.getAllModels();
        }
        return null;
    }

    @Override
    public void onItemClick(Model model) {
        Log.e(LOG_TAG, "OnItemClick");
        if(view != null)
            view.onItemClick(model);
    }
}
