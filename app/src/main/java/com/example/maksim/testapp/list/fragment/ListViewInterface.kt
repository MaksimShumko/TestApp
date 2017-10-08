package com.example.maksim.testapp.list.fragment

import com.example.maksim.testapp.list.model.data.GitHubUser

/**
 * Created by Maksim on 2017-09-10.
 */

interface ListViewInterface {
    val prefSearchQuery: String
    val isNetworkAvailable: Boolean
    fun onDataChanged(gitHubUser: MutableList<GitHubUser>, addElements: Boolean)
    fun showNetworkErrorToast()
    fun showRequestErrorToast(message: String)
    fun startSwipeRefresh()
    fun stopSwipeRefresh()
}
