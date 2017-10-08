package com.example.maksim.testapp.githubapi

import com.example.maksim.testapp.details.model.data.GitHubUserDetails
import com.example.maksim.testapp.list.model.data.GitHubUser
import com.example.maksim.testapp.list.model.data.GitHubUsers

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Maksim on 2017-08-28.
 */

interface ApiInterface {

    @get:GET("/users")
    val users: Call<MutableList<GitHubUser>>

    @GET("/search/users")
    fun searchUsers(@Query("q") userName: String): Call<GitHubUsers>

    @GET("/search/users")
    fun searchUsersPagination(@Query("q") userName: String, @Query("page") page: Int): Call<GitHubUsers>

    @GET("/users/{userLogin}")
    fun getUserDescription(@Path("userLogin") userLogin: String): Call<GitHubUserDetails>
}
