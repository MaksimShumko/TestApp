package com.example.maksim.testapp.list.model.repository

import android.os.AsyncTask

import com.example.maksim.testapp.list.model.data.GitHubUser
import com.example.maksim.testapp.githubapi.room.GitHubUserDao

/**
 * Created by Maksim on 2017-09-10.
 */

class LocalGetAllUsers(private val listener: LocalListRepositoryListener, private val gitHubUserDao: GitHubUserDao) : AsyncTask<String, Void, MutableList<GitHubUser>>() {

    override fun doInBackground(vararg commands: String): MutableList<GitHubUser> {
        return gitHubUserDao.allUsers
    }

    override fun onPostExecute(gitHubUsers: MutableList<GitHubUser>) {
        listener.onUsersLoaded(gitHubUsers)
    }
}
