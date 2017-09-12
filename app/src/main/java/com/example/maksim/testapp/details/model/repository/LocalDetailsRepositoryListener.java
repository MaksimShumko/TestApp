package com.example.maksim.testapp.details.model.repository;

import com.example.maksim.testapp.details.model.data.GitHubUserDetails;

/**
 * Created by Maksim on 2017-09-10.
 */

public interface LocalDetailsRepositoryListener {
    void onUserDetailsLoaded(GitHubUserDetails userDetails);
}
