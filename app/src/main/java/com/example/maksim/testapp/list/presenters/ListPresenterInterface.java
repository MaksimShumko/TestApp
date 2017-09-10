package com.example.maksim.testapp.list.presenters;

import com.example.maksim.testapp.list.data.GitHubUser;

import java.util.List;

/**
 * Created by Maksim on 2017-09-10.
 */

public interface ListPresenterInterface {
    void executeSearchRequest(String userLogin);
    List<GitHubUser> getUsers();
    void onResponse(List<GitHubUser> user);
    void onItemClick(String userLogin);
}
