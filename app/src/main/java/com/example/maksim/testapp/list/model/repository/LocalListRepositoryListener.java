package com.example.maksim.testapp.list.model.repository;

import com.example.maksim.testapp.list.model.data.GitHubUser;

import java.util.List;

/**
 * Created by Maksim on 2017-09-10.
 */

public interface LocalListRepositoryListener {
    void onUsersLoaded(List<GitHubUser> users);
}
