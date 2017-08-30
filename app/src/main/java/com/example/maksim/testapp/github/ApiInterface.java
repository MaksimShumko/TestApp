package com.example.maksim.testapp.github;

import com.example.maksim.testapp.models.GitHubUserDescription;
import com.example.maksim.testapp.models.GitHubUsers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Maksim on 2017-08-28.
 */

public interface ApiInterface {
    @GET("/search/users")
    Call<GitHubUsers> getUsers(@Query("q") String userName);

    @GET("/users/{userLogin}")
    Call<GitHubUserDescription> getUserDescription(@Path("userLogin") String userLogin);
}
