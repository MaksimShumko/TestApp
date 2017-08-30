package com.example.maksim.testapp.github;

import com.example.maksim.testapp.models.GitHubUserDescription;
import com.example.maksim.testapp.models.GitHubUsers;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by Maksim on 2017-08-28.
 */

public class ExecuteRequest {
    private ApiInterface apiInterface;

    public ExecuteRequest() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getUsers(String userName, final OnUserLoaderCompleted<GitHubUsers> listener) {
        Call<GitHubUsers> call = apiInterface.getUsers(userName);
        call.enqueue(new Callback<GitHubUsers>() {
            @Override
            public void onResponse(Call<GitHubUsers> call, Response<GitHubUsers> response) {
                GitHubUsers resource = response.body();
                listener.onUserLoaderCompleted(resource);
            }

            @Override
            public void onFailure(Call<GitHubUsers> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public void getUserDescription(String userLogin, final OnUserLoaderCompleted<GitHubUserDescription> listener) {
        Call<GitHubUserDescription> call = apiInterface.getUserDescription(userLogin);
        call.enqueue(new Callback<GitHubUserDescription>() {
            @Override
            public void onResponse(Call<GitHubUserDescription> call, Response<GitHubUserDescription> response) {
                GitHubUserDescription resource = response.body();
                listener.onUserLoaderCompleted(resource);
            }

            @Override
            public void onFailure(Call<GitHubUserDescription> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public interface OnUserLoaderCompleted<T> {
        void onUserLoaderCompleted(T gitHubUsers);
    }
}
