package com.example.maksim.testapp.details.presenter

import com.example.maksim.testapp.details.fragment.DetailsViewInterface
import com.example.maksim.testapp.details.model.data.GitHubUserDetails
import com.example.maksim.testapp.details.model.DetailsModel
import com.example.maksim.testapp.githubapi.room.RoomSqlDatabase

/**
 * Created by Maksim on 2017-07-16.
 */

class DetailsPresenter(private val view: DetailsViewInterface, roomSqlDatabase: RoomSqlDatabase) : DetailsViewPresenterInterface, DetailsModelListener {
    private val model: DetailsModel

    init {
        view.startSwipeRefresh()
        model = DetailsModel(this, roomSqlDatabase, view.selectedUserLogin)
    }

    override fun onResponse(userDetails: GitHubUserDetails) {
        view.updateView(userDetails)
        view.stopSwipeRefresh()
    }

    override fun onFailureRequest(message: String) {
        view.showRequestErrorToast(message)
        view.stopSwipeRefresh()
    }

    override fun onUserChanged() {
        view.startSwipeRefresh()
        model.loadUserDetails(view.selectedUserLogin)
    }
}
