package com.example.maksim.testapp.github_api;

import com.example.maksim.testapp.details.models.GitHubUserDescription;
import com.example.maksim.testapp.list.data.GitHubUser;
import com.example.maksim.testapp.list.data.GitHubUsers;

import java.util.List;

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

    public void searchUsers(String userName, final OnUserLoaderCompleted<GitHubUsers> listener) {
        Call<GitHubUsers> call = apiInterface.searchUsers(userName);
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

    public void getUsers(final OnUserLoaderCompleted<List<GitHubUser>> listener) {
        Call<List<GitHubUser>> call = apiInterface.getUsers();
        call.enqueue(new Callback<List<GitHubUser>>() {
            @Override
            public void onResponse(Call<List<GitHubUser>> call, Response<List<GitHubUser>> response) {
                List<GitHubUser> resource = response.body();
                listener.onUserLoaderCompleted(resource);
            }

            @Override
            public void onFailure(Call<List<GitHubUser>> call, Throwable t) {
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