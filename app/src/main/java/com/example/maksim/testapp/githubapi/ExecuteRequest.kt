package com.example.maksim.testapp.githubapi

import android.util.Log

import com.example.maksim.testapp.details.model.data.GitHubUserDetails
import com.example.maksim.testapp.list.model.data.GitHubUser
import com.example.maksim.testapp.list.model.data.GitHubUsers

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Maksim on 2017-08-28.
 */

class ExecuteRequest {
    private val apiInterface: ApiInterface = ApiClient.client.create(ApiInterface::class.java)

    fun searchUsers(userName: String, page: Int, listener: OnUserLoaderCompleted<GitHubUsers>) {
        val call = apiInterface.searchUsersPagination(userName, page)
        call.enqueue(object : Callback<GitHubUsers> {
            override fun onResponse(call: Call<GitHubUsers>, response: Response<GitHubUsers>) {
                Log.e("onResponse", "response")
                val resource = response.body()
                if (resource != null)
                    listener.onUserLoaderCompleted(resource)
                else
                    listener.onFailure("There is a problem with remote data")
            }

            override fun onFailure(call: Call<GitHubUsers>, t: Throwable) {
                Log.e("onFailureRequest", "failure")
                call.cancel()
                listener.onFailure(t.message!!)
            }
        })
    }

    fun getUsers(listener: OnUserLoaderCompleted<MutableList<GitHubUser>>) {
        val call = apiInterface.users
        call.enqueue(object : Callback<MutableList<GitHubUser>> {
            override fun onResponse(call: Call<MutableList<GitHubUser>>, response: Response<MutableList<GitHubUser>>) {
                Log.e("onResponse", "response")
                val resource = response.body()
                if (resource != null)
                    listener.onUserLoaderCompleted(resource)
                else
                    listener.onFailure("There is a problem with remote data")
            }

            override fun onFailure(call: Call<MutableList<GitHubUser>>, t: Throwable) {
                Log.e("onFailureRequest", "failure")
                call.cancel()
                listener.onFailure(t.message!!)
            }
        })
    }

    fun getUserDescription(userLogin: String, listener: OnUserLoaderCompleted<GitHubUserDetails>) {
        val call = apiInterface.getUserDescription(userLogin)
        call.enqueue(object : Callback<GitHubUserDetails> {
            override fun onResponse(call: Call<GitHubUserDetails>, response: Response<GitHubUserDetails>) {
                Log.e("onResponse", "response")
                val resource = response.body()
                if (resource != null)
                    listener.onUserLoaderCompleted(resource)
                else
                    listener.onFailure("There is a problem with remote data")
            }

            override fun onFailure(call: Call<GitHubUserDetails>, t: Throwable) {
                Log.e("onFailureRequest", "failure")
                call.cancel()
                listener.onFailure(t.message!!)
            }
        })
    }

    interface OnUserLoaderCompleted<in T> {
        fun onUserLoaderCompleted(gitHubUsers: T)
        fun onFailure(message: String)
    }
}
