package com.example.maksim.testapp.contracts;

import com.example.maksim.testapp.models.GitHubUsers;

import java.util.List;

/**
 * Created by Maksim on 2017-07-10.
 */

public class ModelListViewContract {
    public interface Model {
        List<GitHubUsers.User> getAllGitHubUsers();
        void executeSearchUsers(String userLogin);
        void executeGetUsers();
    }

    public interface View {
        void notifyDataSetChanged();
        void onItemClick(String userLogin);
    }

    public interface Presenter extends OnItemClickListener {
        void executeSearchRequest(String userLogin);
        void executeGetUsersRequest();
        List<GitHubUsers.User> getUsers();
        void onExecuteResult(List<GitHubUsers.User> user);
    }

    public interface OnItemClickListener {
        void onItemClick(String userLogin);
    }
}
