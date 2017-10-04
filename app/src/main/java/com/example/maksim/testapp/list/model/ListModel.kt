package com.example.maksim.testapp.list.model

import com.example.maksim.testapp.list.model.repository.LocalGetAllUsers
import com.example.maksim.testapp.list.model.repository.LocalInsertUsers
import com.example.maksim.testapp.list.model.repository.LocalListRepositoryListener
import com.example.maksim.testapp.list.presenter.ModelListener
import com.example.maksim.testapp.githubapi.room.GitHubUserDao
import com.example.maksim.testapp.githubapi.room.RoomSqlDatabase
import com.example.maksim.testapp.list.model.data.GitHubUser
import com.example.maksim.testapp.list.model.data.GitHubUsers
import com.example.maksim.testapp.githubapi.ExecuteRequest

/**
 * Created by Maksim on 2017-07-10.
 */

class ListModel(private val modelListener: ModelListener, roomSqlDatabase: RoomSqlDatabase, private val prefSearchQuery: String) : LocalListRepositoryListener {
    private val gitHubUserDao: GitHubUserDao = roomSqlDatabase.userDao
    private var totalCountOfElements: Int = 0

    private val gitHubUsersListener = object : ExecuteRequest.OnUserLoaderCompleted<GitHubUsers> {
        override fun onUserLoaderCompleted(gitHubUsers: GitHubUsers) {
            totalCountOfElements = gitHubUsers.totalCount!!
            this@ListModel.onUserLoaderCompleted(gitHubUsers.items!!, currentPage != 1)
        }

        override fun onFailure(message: String) {
            onFailureRequest(message)
        }
    }

    private val listOfUsersListener = object : ExecuteRequest.OnUserLoaderCompleted<MutableList<GitHubUser>> {
        override fun onUserLoaderCompleted(gitHubUsers: MutableList<GitHubUser>) {
            this@ListModel.onUserLoaderCompleted(gitHubUsers, false)
        }

        override fun onFailure(message: String) {
            onFailureRequest(message)
        }
    }

    init {
        LocalGetAllUsers(this, gitHubUserDao).execute()
    }

    fun executeSearchUsers(searchQuery: String?) {
        if (modelListener.isNetworkAvailable()) {
            currentPage = 1
            if (searchQuery != null && !searchQuery.isEmpty())
                ExecuteRequest().searchUsers(searchQuery, currentPage, gitHubUsersListener)
            else
                ExecuteRequest().getUsers(listOfUsersListener)
        }
    }

    fun executeGetNextPage(searchQuery: String?) {
        if (modelListener.isNetworkAvailable())
            if (currentPage * COUNT_OF_ELEMENTS_ON_PAGE <= totalCountOfElements && searchQuery != null && !searchQuery.isEmpty())
                ExecuteRequest().searchUsers(searchQuery, ++currentPage, gitHubUsersListener)
            else
                onFailureRequest("Where is no more pages")
    }

    private fun onUserLoaderCompleted(gitHubUsers: MutableList<GitHubUser>, addElements: Boolean) {
        LocalInsertUsers(gitHubUsers, gitHubUserDao, addElements).execute()
        modelListener.onResponse(gitHubUsers, addElements)
    }

    private fun onFailureRequest(message: String) {
        modelListener.onFailureRequest(message)
    }

    override fun onUsersLoaded(users: MutableList<GitHubUser>?) {
        if (users == null || users.isEmpty())
            executeSearchUsers(prefSearchQuery)
        else
            modelListener.onResponse(users, false)
    }

    companion object {
        val COUNT_OF_ELEMENTS_ON_PAGE = 30
        private var currentPage: Int = 0
    }
}
