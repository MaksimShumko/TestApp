package com.example.maksim.testapp.models;

import com.example.maksim.testapp.contracts.ModelListViewContract;
import com.example.maksim.testapp.github.UserDataLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maksim on 2017-07-10.
 */

public class Model implements ModelListViewContract.Model, UserDataLoader.OnUserLoaderCompleted {
    private List<GitHubUser> gitHubUsers;
    private ModelListViewContract.Presenter presenter;

    private static Model model;

    public static Model getInstance() {
        if(model == null)
            model = new Model();
        return model;
    }

    public static Model getInstance(ModelListViewContract.Presenter presenter) {
        if(model == null)
            model = new Model(presenter);
        return model;
    }

    private Model() {

    }

    private Model(ModelListViewContract.Presenter presenter) {
        this.presenter = presenter;
        init();
    }

    private void init() {
        gitHubUsers = new ArrayList<>();
        /*for(int i = 0; i < 100; i++) {
            String title = "Title " + String.valueOf(i);
            String description = "Description " + String.valueOf(i);
            gitHubUsers.add(new GitHubUser(title, description, "", "", 0));
        }*/
        String query = "q=Maksim&order=desc";

        new UserDataLoader(this).execute(query);
    }

    @Override
    public GitHubUser getGitHubUser(int position) {
        if(gitHubUsers != null && gitHubUsers.size() >= 0)
            return gitHubUsers.get(position);
        return null;
    }

    @Override
    public void addGitHubUser(GitHubUser object) {
        if(gitHubUsers == null)
            gitHubUsers = new ArrayList<>();
        gitHubUsers.add(object);
    }

    @Override
    public boolean deleteGitHubUser(GitHubUser object) {
        return gitHubUsers != null && gitHubUsers.remove(object);
    }

    @Override
    public List<GitHubUser> getAllGitHubUsers() {
        return gitHubUsers;
    }

    @Override
    public int getCount() {
        if(gitHubUsers != null)
            return gitHubUsers.size();
        return 0;
    }

    @Override
    public void onUserLoaderCompleted(List<GitHubUser> gitHubUsers) {
        this.gitHubUsers = gitHubUsers;
        presenter.notifyDataSetChanged();
    }
}
