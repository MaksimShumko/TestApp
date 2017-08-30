package com.example.maksim.testapp.contracts;

import com.example.maksim.testapp.models.GitHubUserDescription;


/**
 * Created by Maksim on 2017-07-16.
 */

public class ModelFormViewContract {
    public interface Model {
        void executeGetUserDescription(String userLogin);
    }

    public interface View {
        void updateView(GitHubUserDescription gitHubUserDescription);
    }

    public interface Presenter {
        void executeRequest(String userLogin);
        void onExecuteResult(GitHubUserDescription gitHubUserDescription);
    }
}
