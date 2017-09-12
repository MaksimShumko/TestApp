package com.example.maksim.testapp.list.model.repository;

import android.os.AsyncTask;

import com.example.maksim.testapp.list.model.data.GitHubUser;
import com.example.maksim.testapp.github_api.room.GitHubUserDao;

import java.util.List;

/**
 * Created by Maksim on 2017-09-10.
 */

public class LocalGetAllUsers extends AsyncTask<String, Void, List<GitHubUser>> {
    private GitHubUserDao gitHubUserDao;
    private LocalListRepositoryListener listener;

    public LocalGetAllUsers(LocalListRepositoryListener listener, GitHubUserDao gitHubUserDao) {
        this.listener = listener;
        this.gitHubUserDao = gitHubUserDao;
    }

    @Override
    protected List<GitHubUser> doInBackground(String... commands) {
        return gitHubUserDao.getAllUsers();
    }

    @Override
    protected void onPostExecute(List<GitHubUser> gitHubUsers) {
        listener.onUsersLoaded(gitHubUsers);
    }
}
