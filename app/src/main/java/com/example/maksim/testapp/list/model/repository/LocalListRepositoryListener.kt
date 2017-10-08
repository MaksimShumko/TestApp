package com.example.maksim.testapp.list.model.repository

import com.example.maksim.testapp.list.model.data.GitHubUser

/**
 * Created by Maksim on 2017-09-10.
 */

interface LocalListRepositoryListener {
    fun onUsersLoaded(users: MutableList<GitHubUser>?)
}
