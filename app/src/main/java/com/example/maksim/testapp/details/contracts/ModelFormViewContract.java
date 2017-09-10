package com.example.maksim.testapp.details.contracts;

import com.example.maksim.testapp.details.models.GitHubUserDescription;


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
