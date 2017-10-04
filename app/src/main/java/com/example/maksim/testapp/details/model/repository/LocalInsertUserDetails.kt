package com.example.maksim.testapp.details.model.repository

import android.os.AsyncTask

import com.example.maksim.testapp.details.model.data.GitHubUserDetails
import com.example.maksim.testapp.githubapi.room.GitHubUserDetailsDao

/**
 * Created by Maksim on 2017-09-11.
 */

class LocalInsertUserDetails(private val userDetails: GitHubUserDetails, private val gitHubUserDetailsDao: GitHubUserDetailsDao) : AsyncTask<String, Void, Void>() {

    override fun doInBackground(vararg commands: String): Void? {
        gitHubUserDetailsDao.insertUserDetails(userDetails)
        return null
    }
}
