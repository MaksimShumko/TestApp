package com.example.maksim.testapp.contracts;

import com.example.maksim.testapp.models.GitHubUser;

import java.util.List;

/**
 * Created by Maksim on 2017-07-10.
 */

public class ModelListViewContract {
    public interface Model {
        GitHubUser getGitHubUser(int position);
        void addGitHubUser(GitHubUser object);
        boolean deleteGitHubUser(GitHubUser object);
        List<GitHubUser> getAllGitHubUsers();
        int getCount();
    }

    public interface View {
        void showView(List<GitHubUser> elements);
        void notifyDataSetChanged();
        void onItemClick(GitHubUser gitHubUser);
    }

    public interface Presenter extends OnItemClickListener {
        void loadModels();
        List<GitHubUser> getAllModels();
        void notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(GitHubUser gitHubUser);
    }
}
