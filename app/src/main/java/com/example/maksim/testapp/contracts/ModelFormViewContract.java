package com.example.maksim.testapp.contracts;

import java.util.List;

/**
 * Created by Maksim on 2017-07-16.
 */

public class ModelFormViewContract {
    public interface View {
        void onItemClick(int position);
    }

    public interface Actions {
        String getTitle(int position);
        String getDescription(int position);
        void onItemClick(int position);
    }
}
