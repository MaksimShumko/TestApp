package com.example.maksim.testapp.details.model

import com.example.maksim.testapp.details.model.repository.LocalGetUserDetails
import com.example.maksim.testapp.details.model.repository.LocalInsertUserDetails
import com.example.maksim.testapp.details.model.repository.LocalDetailsRepositoryListener
import com.example.maksim.testapp.details.presenter.DetailsModelListener
import com.example.maksim.testapp.details.model.data.GitHubUserDetails
import com.example.maksim.testapp.githubapi.ExecuteRequest
import com.example.maksim.testapp.githubapi.room.GitHubUserDetailsDao
import com.example.maksim.testapp.githubapi.room.RoomSqlDatabase

/**
 * Created by Maksim on 2017-08-30.
 */

class DetailsModel(private val listener: DetailsModelListener, roomSqlDatabase: RoomSqlDatabase,
                   private var userLogin: String?) : ExecuteRequest.OnUserLoaderCompleted<GitHubUserDetails>, LocalDetailsRepositoryListener {

    private val gitHubUserDetailsDao: GitHubUserDetailsDao = roomSqlDatabase.userDetailsDao

    init {
        LocalGetUserDetails(this, gitHubUserDetailsDao).execute(userLogin)
    }

    private fun executeGetUserDescription(userLogin: String?) {
        if (userLogin != null && !userLogin.isEmpty())
            ExecuteRequest().getUserDescription(userLogin, this)
    }

    fun loadUserDetails(userLogin: String) {
        this.userLogin = userLogin
        LocalGetUserDetails(this, gitHubUserDetailsDao).execute(userLogin)
    }

    override fun onUserLoaderCompleted(gitHubUsers: GitHubUserDetails) {
        LocalInsertUserDetails(gitHubUsers, gitHubUserDetailsDao).execute()
        listener.onResponse(gitHubUsers)
    }

    override fun onFailure(message: String) {
        listener.onFailureRequest(message)
    }

    override fun onUserDetailsLoaded(userDetails: GitHubUserDetails?) {
        if (userDetails == null)
            executeGetUserDescription(userLogin)
        else
            listener.onResponse(userDetails)
    }
}
