package com.example.maksim.testapp.details.presenter

import com.example.maksim.testapp.details.model.data.GitHubUserDetails

/**
 * Created by Maksim on 2017-09-12.
 */

interface DetailsModelListener {
    fun onResponse(userDetails: GitHubUserDetails)
    fun onFailureRequest(message: String)
}
