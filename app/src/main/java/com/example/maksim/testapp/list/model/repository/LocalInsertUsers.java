package com.example.maksim.testapp.list.model.repository;

import android.os.AsyncTask;

import com.example.maksim.testapp.list.model.data.GitHubUser;
import com.example.maksim.testapp.githubapi.room.GitHubUserDao;

import java.util.List;

/**
 * Created by Maksim on 2017-09-11.
 */

public class LocalInsertUsers extends AsyncTask<String, Void, Void> {
    private List<GitHubUser> gitHubUsers;
    private GitHubUserDao gitHubUserDao;
    private boolean addElements;

    public LocalInsertUsers(List<GitHubUser> gitHubUsers, GitHubUserDao gitHubUserDao, boolean addElements) {
        this.gitHubUsers = gitHubUsers;
        this.gitHubUserDao = gitHubUserDao;
        this.addElements = addElements;
    }

    @Override
    protected Void doInBackground(String... commands) {
        if (!addElements)
            gitHubUserDao.deleteUserList();
        gitHubUserDao.insertUsers(gitHubUsers);
        return null;
    }
}
