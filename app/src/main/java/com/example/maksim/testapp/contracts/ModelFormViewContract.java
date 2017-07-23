package com.example.maksim.testapp.contracts;

import com.example.maksim.testapp.models.Model;

import java.util.List;

/**
 * Created by Maksim on 2017-07-16.
 */

public class ModelFormViewContract {
    public interface View {
        void onItemClick(int position);
    }

    public interface Presenter {
        Model getModel(int position);
        void onItemClick(int position);
    }
}
