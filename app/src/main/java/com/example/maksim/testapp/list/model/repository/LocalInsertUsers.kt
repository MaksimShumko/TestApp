package com.example.maksim.testapp.list.model.repository

import android.os.AsyncTask

import com.example.maksim.testapp.list.model.data.GitHubUser
import com.example.maksim.testapp.githubapi.room.GitHubUserDao

/**
 * Created by Maksim on 2017-09-11.
 */

class LocalInsertUsers(private val gitHubUsers: MutableList<GitHubUser>, private val gitHubUserDao: GitHubUserDao, private val addElements: Boolean) : AsyncTask<String, Void, Void>() {

    override fun doInBackground(vararg commands: String): Void? {
        if (!addElements)
            gitHubUserDao.deleteUserList()
        gitHubUserDao.insertUsers(gitHubUsers)
        return null
    }
}
