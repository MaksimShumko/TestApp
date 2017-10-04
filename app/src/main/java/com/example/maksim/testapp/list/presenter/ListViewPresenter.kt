package com.example.maksim.testapp.list.presenter

import com.example.maksim.testapp.githubapi.room.RoomSqlDatabase
import com.example.maksim.testapp.list.fragment.ListViewInterface
import com.example.maksim.testapp.list.model.ListModel
import com.example.maksim.testapp.list.model.data.GitHubUser

/**
 * Created by Maksim on 2017-07-10.
 */

class ListViewPresenter(private val view: ListViewInterface, roomSqlDatabase: RoomSqlDatabase) : ListViewPresenterInterface, ModelListener {
    private val LOG_TAG = "ListViewPresenter"
    private val model: ListModel?

    init {
        view.startSwipeRefresh()
        model = ListModel(this, roomSqlDatabase, view.prefSearchQuery)
    }

    override fun onSearchQueryChanged() {
        if (model != null) {
            view.startSwipeRefresh()
            model.executeSearchUsers(view.prefSearchQuery)
        }
    }

    override fun isLastElement() {
        if (model != null) {
            view.startSwipeRefresh()
            model.executeGetNextPage(view.prefSearchQuery)
        }
    }

    override fun onSwipeRefresh() {
        model?.executeSearchUsers(view.prefSearchQuery)
    }

    override fun onResponse(user: MutableList<GitHubUser>, addElements: Boolean) {
        view.onDataChanged(user, addElements)
        view.stopSwipeRefresh()
    }

    override fun isNetworkAvailable(): Boolean {
        val isNetworkAvailable = view.isNetworkAvailable
        if (!isNetworkAvailable) {
            view.showNetworkErrorToast()
            view.stopSwipeRefresh()
        }
        return isNetworkAvailable
    }

    override fun onFailureRequest(message: String) {
        view.showRequestErrorToast(message)
        view.stopSwipeRefresh()
    }
}
