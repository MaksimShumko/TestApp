package com.example.maksim.testapp.github_api;

import com.example.maksim.testapp.details.model.data.GitHubUserDetails;
import com.example.maksim.testapp.list.model.data.GitHubUser;
import com.example.maksim.testapp.list.model.data.GitHubUsers;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Maksim on 2017-08-28.
 */

public interface ApiInterface {
    @GET("/search/users")
    Call<GitHubUsers> searchUsers(@Query("q") String userName);

    @GET("/users")
    Call<List<GitHubUser>> getUsers();

    @GET("/users/{userLogin}")
    Call<GitHubUserDetails> getUserDescription(@Path("userLogin") String userLogin);
}
