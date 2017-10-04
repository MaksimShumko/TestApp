package com.example.maksim.testapp.githubapi.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import com.example.maksim.testapp.details.model.data.GitHubUserDetails

/**
 * Created by Maksim on 2017-09-12.
 */

@Dao
interface GitHubUserDetailsDao {
    @Query("SELECT * FROM GitHubUserDetails WHERE login LIKE :arg0 LIMIT 1")
    fun findUserDetailsByLogin(userLogin: String): GitHubUserDetails

    @Query("DELETE FROM GitHubUserDetails")
    fun deleteAllDetails()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserDetails(userDetails: GitHubUserDetails)

    @Delete
    fun deleteUserDetails(userDetails: GitHubUserDetails)
}