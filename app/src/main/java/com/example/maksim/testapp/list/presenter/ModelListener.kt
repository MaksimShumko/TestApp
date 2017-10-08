package com.example.maksim.testapp.list.presenter

import com.example.maksim.testapp.list.model.data.GitHubUser

/**
 * Created by Maksim on 2017-09-11.
 */

interface ModelListener {
    fun isNetworkAvailable() : Boolean
    fun onResponse(user: MutableList<GitHubUser>, addElements: Boolean)
    fun onFailureRequest(message: String)
}
