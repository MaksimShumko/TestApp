package com.example.maksim.testapp.list.model.repository;

import android.os.AsyncTask;

import com.example.maksim.testapp.list.data.GitHubUser;
import com.example.maksim.testapp.room.GitHubUserDao;

import java.util.List;

/**
 * Created by Maksim on 2017-09-11.
 */

public class LocalInsertUsers extends AsyncTask<String, Void, Void> {
    private List<GitHubUser> gitHubUsers;
    private GitHubUserDao gitHubUserDao;

    public LocalInsertUsers(List<GitHubUser> gitHubUsers, GitHubUserDao gitHubUserDao) {
        this.gitHubUsers = gitHubUsers;
        this.gitHubUserDao = gitHubUserDao;
    }

    @Override
    protected Void doInBackground(String... commands) {
        gitHubUserDao.deleteAll();
        gitHubUserDao.insertUsers(gitHubUsers);
        return null;
    }
}
