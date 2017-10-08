package com.example.maksim.testapp.details.model.repository

import android.os.AsyncTask

import com.example.maksim.testapp.details.model.data.GitHubUserDetails
import com.example.maksim.testapp.githubapi.room.GitHubUserDetailsDao

/**
 * Created by Maksim on 2017-09-10.
 */

class LocalGetUserDetails(private val listener: LocalDetailsRepositoryListener, private val gitHubUserDetailsDao: GitHubUserDetailsDao) : AsyncTask<String, Void, GitHubUserDetails?>() {

    override fun doInBackground(vararg logins: String): GitHubUserDetails {
        return gitHubUserDetailsDao.findUserDetailsByLogin(logins[0])
    }

    override fun onPostExecute(gitHubUserDetails: GitHubUserDetails?) {
        listener.onUserDetailsLoaded(gitHubUserDetails)
    }
}
