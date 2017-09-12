package com.example.maksim.testapp.details.model.repository;

import android.os.AsyncTask;

import com.example.maksim.testapp.details.model.data.GitHubUserDetails;
import com.example.maksim.testapp.github_api.room.GitHubUserDetailsDao;

/**
 * Created by Maksim on 2017-09-10.
 */

public class LocalGetUserDetails extends AsyncTask<String, Void, GitHubUserDetails> {
    private GitHubUserDetailsDao gitHubUserDetailsDao;
    private LocalDetailsRepositoryListener listener;

    public LocalGetUserDetails(LocalDetailsRepositoryListener listener, GitHubUserDetailsDao gitHubUserDetailsDao) {
        this.listener = listener;
        this.gitHubUserDetailsDao = gitHubUserDetailsDao;
    }

    @Override
    protected GitHubUserDetails doInBackground(String... logins) {
        return gitHubUserDetailsDao.findUserDetailsByLogin(logins[0]);
    }

    @Override
    protected void onPostExecute(GitHubUserDetails gitHubUserDetails) {
        listener.onUserDetailsLoaded(gitHubUserDetails);
    }
}
