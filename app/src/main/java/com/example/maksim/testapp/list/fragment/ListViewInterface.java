package com.example.maksim.testapp.list.fragment;

import com.example.maksim.testapp.list.model.data.GitHubUser;

import java.util.List;

/**
 * Created by Maksim on 2017-09-10.
 */

public interface ListViewInterface {
    void onDataChanged(List<GitHubUser> gitHubUser);
    String getPrefSearchQuery();
}
