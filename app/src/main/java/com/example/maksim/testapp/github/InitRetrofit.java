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

    ApiInterface apiInterface;

    public InitRetrofit() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);


        /**
         GET List Resources
         **/
        Call call = apiInterface.getUsers();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                Log.e("InitRetrofit", response.body()+"");
                Log.e("InitRetrofit", response.errorBody()+"");
                Log.e("InitRetrofit", response.headers()+"");
                Log.e("InitRetrofit", response.code()+"");

                String displayResponse = "";

                MultipleResource resource = (MultipleResource) response.body();
                boolean text = resource.incompleteResults;
                int total = resource.totalCount;
                List datumList = resource.items;

                displayResponse += text + " incompleteResults\n" + total + " totalCount\n";

                for(int i = 0; i < datumList.size(); i++) {
                    MultipleResource.Datum datum = (MultipleResource.Datum) datumList.get(i);
                    displayResponse += datum.id + " " + datum.login + " " + datum.type + " " + datum.score + "\n";
                }

                Log.e("InitRetrofit", displayResponse);

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                call.cancel();
            }
        });
    }
}
