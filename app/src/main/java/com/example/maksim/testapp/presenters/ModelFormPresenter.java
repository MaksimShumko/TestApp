package com.example.maksim.testapp.presenters;

import com.example.maksim.testapp.contracts.ModelFormViewContract;
import com.example.maksim.testapp.contracts.ModelListViewContract;
import com.example.maksim.testapp.models.Model;
import com.example.maksim.testapp.models.Repository;

/**
 * Created by Maksim on 2017-07-16.
 */

public class ModelFormPresenter implements ModelFormViewContract.Presenter {
    private ModelFormViewContract.View view;
    private ModelListViewContract.Repository repository;

    public ModelFormPresenter(ModelFormViewContract.View view) {
        this.view = view;
        repository = new Repository();
    }

    @Override
    public Model getModel(int position) {
        if(repository != null)
            return repository.getModel(position);
        return null;
    }

    @Override
    public void onItemClick(int position) {

    }
}
