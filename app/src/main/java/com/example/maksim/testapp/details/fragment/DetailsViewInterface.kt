package com.example.maksim.testapp.details.fragment

import com.example.maksim.testapp.details.model.data.GitHubUserDetails

/**
 * Created by Maksim on 2017-09-12.
 */

interface DetailsViewInterface {
    val selectedUserLogin: String
    fun updateView(gitHubUserDetails: GitHubUserDetails)
    fun showRequestErrorToast(message: String)
    fun startSwipeRefresh()
    fun stopSwipeRefresh()
}
