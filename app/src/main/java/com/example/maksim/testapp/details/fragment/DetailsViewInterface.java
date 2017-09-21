package com.example.maksim.testapp.details.fragment;

import com.example.maksim.testapp.details.model.data.GitHubUserDetails;

/**
 * Created by Maksim on 2017-09-12.
 */

public interface DetailsViewInterface {
    void updateView(GitHubUserDetails gitHubUserDetails);
    String getSelectedUserLogin();
    void showRequestErrorToast(String message);
    void startSwipeRefresh();
    void stopSwipeRefresh();
}
