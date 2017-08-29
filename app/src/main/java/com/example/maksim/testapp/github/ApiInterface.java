package com.example.maksim.testapp.github;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Maksim on 2017-08-28.
 */

public interface ApiInterface {
    @GET("/search/users")
    Call<GitHubUsers> getUsers(@Query("q") String userName);
}
