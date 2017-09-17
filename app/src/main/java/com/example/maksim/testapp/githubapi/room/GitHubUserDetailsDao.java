package com.example.maksim.testapp.githubapi.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.maksim.testapp.details.model.data.GitHubUserDetails;

/**
 * Created by Maksim on 2017-09-12.
 */

@Dao
public interface GitHubUserDetailsDao {
    @Query("SELECT * FROM GitHubUserDetails WHERE login LIKE :userLogin LIMIT 1")
    GitHubUserDetails findUserDetailsByLogin(String userLogin);

    @Query("DELETE FROM GitHubUserDetails")
    void deleteAllDetails();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserDetails(GitHubUserDetails userDetails);

    @Delete
    void deleteUserDetails(GitHubUserDetails userDetails);
}