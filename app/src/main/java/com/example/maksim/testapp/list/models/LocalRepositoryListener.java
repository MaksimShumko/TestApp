package com.example.maksim.testapp.list.models;

import com.example.maksim.testapp.list.data.GitHubUser;

import java.util.List;

/**
 * Created by Maksim on 2017-09-10.
 */

public interface LocalRepositoryListener {
    void onUsersLoaded(List<GitHubUser> users);
}
