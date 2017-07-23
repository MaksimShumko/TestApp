package com.example.maksim.testapp.contracts;

import com.example.maksim.testapp.models.Model;

import java.util.List;

/**
 * Created by Maksim on 2017-07-10.
 */

public class ModelListViewContract {
    public interface Repository {
        Model getModel(int position);
        void addModel(Model object);
        boolean deleteModel(Model object);
        List<Model> getAllModels();
        int getCount();
    }

    public interface View {
        void showView(List<Model> elements);
        void notifyDataSetChanged();
        void onItemClick(Model model);
    }

    public interface Presenter extends OnItemClickListener {
        void loadModels();
        List<Model> getAllModels();
    }

    public interface OnItemClickListener {
        void onItemClick(Model model);
    }
}
