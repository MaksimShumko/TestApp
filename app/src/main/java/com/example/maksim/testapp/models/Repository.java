package com.example.maksim.testapp.models;

import com.example.maksim.testapp.contracts.ModelListViewContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maksim on 2017-07-10.
 */

public class Repository implements ModelListViewContract.Repository{
    private List<ModelListViewContract.Model> models;

    public Repository() {
        init();
    }

    private void init() {
        models = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            final String title = "Title " + String.valueOf(i);
            final String description = "Description " + String.valueOf(i);;
            models.add(new ModelListViewContract.Model() {
                @Override
                public String getTitle() {
                    return title;
                }

                @Override
                public String getDescription() {
                    return description;
                }
            });
        }
    }

    @Override
    public ModelListViewContract.Model getModel(int position) {
        if(models != null)
            return models.get(position);
        return null;
    }

    @Override
    public void addModel(ModelListViewContract.Model object) {
        if(models == null)
            models = new ArrayList<>();
        models.add(object);
    }

    @Override
    public boolean deleteModel(ModelListViewContract.Model object) {
        return models != null && models.remove(object);
    }

    @Override
    public List<ModelListViewContract.Model> getAllModels() {
        return models;
    }

    @Override
    public int getCount() {
        if(models != null)
            return models.size();
        return 0;
    }
}
