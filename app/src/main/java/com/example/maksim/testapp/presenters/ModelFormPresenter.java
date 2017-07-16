package com.example.maksim.testapp.presenters;

import com.example.maksim.testapp.contracts.ModelFormViewContract;
import com.example.maksim.testapp.contracts.ModelListViewContract;
import com.example.maksim.testapp.models.Repository;

/**
 * Created by Maksim on 2017-07-16.
 */

public class ModelFormPresenter implements ModelFormViewContract.Actions {
    private ModelFormViewContract.View view;
    private ModelListViewContract.Repository repository;

    public ModelFormPresenter(ModelFormViewContract.View view) {
        this.view = view;
        repository = new Repository();
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

    @Override
    public void onItemClick(int position) {

    }
}
