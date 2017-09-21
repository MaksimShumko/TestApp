package com.example.maksim.testapp.list.presenter;

import com.example.maksim.testapp.list.model.data.GitHubUser;

import java.util.List;

/**
 * Created by Maksim on 2017-09-11.
 */

public interface ModelListener {
    void onResponse(List<GitHubUser> user, boolean addElements);
    boolean isNetworkAvailable();
    void onFailureRequest(String message);
}
