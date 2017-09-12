package com.example.maksim.testapp.details.model.repository;

import android.os.AsyncTask;

import com.example.maksim.testapp.details.model.data.GitHubUserDetails;
import com.example.maksim.testapp.github_api.room.GitHubUserDetailsDao;

/**
 * Created by Maksim on 2017-09-11.
 */

public class LocalInsertUserDetails extends AsyncTask<String, Void, Void> {
    private GitHubUserDetails userDetails;
    private GitHubUserDetailsDao gitHubUserDetailsDao;

    public LocalInsertUserDetails(GitHubUserDetails userDetails, GitHubUserDetailsDao gitHubUserDetailsDao) {
        this.userDetails = userDetails;
        this.gitHubUserDetailsDao = gitHubUserDetailsDao;
    }

    @Override
    protected Void doInBackground(String... commands) {
        gitHubUserDetailsDao.insertUserDetails(userDetails);
        return null;
    }
}
