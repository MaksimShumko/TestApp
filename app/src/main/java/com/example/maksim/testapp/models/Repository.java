package com.example.maksim.testapp.models;

import com.example.maksim.testapp.contracts.ModelListViewContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maksim on 2017-07-10.
 */

public class Repository implements ModelListViewContract.Repository{
    private List<Model> models;

    public Repository() {
        init();
    }

    private void init() {
        models = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            String title = "Title " + String.valueOf(i);
            String description = "Description " + String.valueOf(i);;
            models.add(new Model(title, description));
        }
    }

    @Override
    public Model getModel(int position) {
        if(models != null)
            return models.get(position);
        return null;
    }

    @Override
    public void addModel(Model object) {
        if(models == null)
            models = new ArrayList<>();
        models.add(object);
    }

    @Override
    public boolean deleteModel(Model object) {
        return models != null && models.remove(object);
    }

    @Override
    public List<Model> getAllModels() {
        return models;
    }

    @Override
    public int getCount() {
        if(models != null)
            return models.size();
        return 0;
    }
}
