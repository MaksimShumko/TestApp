package com.example.maksim.testapp.contracts;

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
        void notifyDataSetChanged();
        void onItemClick(Model model);
    }

    public interface Actions {
        void loadModels();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface ListItem {
        public String getTitle(int position);
        public String getDescription(int position);
        public int getCount();
    }

    public interface Model {
        public String getTitle();
        public String getDescription();
    }
}
