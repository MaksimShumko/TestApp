package com.example.maksim.testapp.githubapi.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

import com.example.maksim.testapp.list.model.data.GitHubUser

/**
 * Created by Maksim on 2017-09-10.
 */

@Dao
interface GitHubUserDao {
    @get:Query("SELECT * FROM GitHubUser")
    val allUsers: MutableList<GitHubUser>

    @Query("SELECT * FROM GitHubUser WHERE id IN (:arg0)")
    fun loadAllByIds(userIds: IntArray): MutableList<GitHubUser>

    @Query("SELECT * FROM GitHubUser WHERE login LIKE :arg0 LIMIT 1")
    fun findByLogin(login: String): GitHubUser

    @Query("DELETE FROM GitHubUser")
    fun deleteUserList()

    @Insert
    fun insertUsers(users: MutableList<GitHubUser>)

    @Update
    fun updateUser(user: GitHubUser)

    @Update
    fun updateUsers(users: MutableList<GitHubUser>)

    @Delete
    fun deleteUsers(users: MutableList<GitHubUser>)
}