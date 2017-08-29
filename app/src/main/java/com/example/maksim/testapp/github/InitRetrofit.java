package com.example.maksim.testapp.github;

import android.util.Log;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by Maksim on 2017-08-28.
 */

public class InitRetrofit {

    private ApiInterface apiInterface;
    private OnUserLoaderCompleted listener;

    public InitRetrofit(final OnUserLoaderCompleted listener) {
        this.listener = listener;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        /**
         GET List Resources
         **/
        Call<GitHubUsers> call = apiInterface.getUsers("Maksim");
        call.enqueue(new Callback<GitHubUsers>() {
            @Override
            public void onResponse(Call<GitHubUsers> call, Response<GitHubUsers> response) {

                Log.e("InitRetrofit", response.body()+"");
                Log.e("InitRetrofit", response.errorBody()+"");
                Log.e("InitRetrofit", response.headers()+"");
                Log.e("InitRetrofit", response.code()+"");

                //String displayResponse = "";

                GitHubUsers resource = response.body();
                boolean text = resource.incompleteResults;
                int total = resource.totalCount;
                List datumList = resource.items;

                //displayResponse += text + " incompleteResults\n" + total + " totalCount\n";

                /*for(int i = 0; i < datumList.size(); i++) {
                    GitHubUsers.Datum datum = (GitHubUsers.Datum) datumList.get(i);
                    //displayResponse += datum.id + " " + datum.login + " " + datum.type + " " + datum.score + "\n";
                }*/

                //Log.e("InitRetrofit", displayResponse);

                listener.onUserLoaderCompleted(resource);
            }

            @Override
            public void onFailure(Call<GitHubUsers> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public interface OnUserLoaderCompleted {
        void onUserLoaderCompleted(GitHubUsers gitHubUsers);
    }
}
