package com.example.maksim.testapp.githubapi.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

import com.example.maksim.testapp.list.model.data.GitHubUser
import com.example.maksim.testapp.details.model.data.GitHubUserDetails

/**
 * Created by Maksim on 2017-09-10.
 */

@Database(entities = arrayOf(GitHubUser::class, GitHubUserDetails::class), version = 1)
abstract class RoomSqlDatabase : RoomDatabase() {
    abstract val userDao: GitHubUserDao
    abstract val userDetailsDao: GitHubUserDetailsDao

    companion object {
        val DATABASE_NAME_GIT_HUB = "GIT_HUB_USERS"
    }
}
