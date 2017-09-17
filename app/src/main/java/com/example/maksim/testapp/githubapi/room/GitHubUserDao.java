package com.example.maksim.testapp.githubapi.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.maksim.testapp.list.model.data.GitHubUser;

import java.util.List;

/**
 * Created by Maksim on 2017-09-10.
 */

@Dao
public interface GitHubUserDao {
    @Query("SELECT * FROM GitHubUser")
    List<GitHubUser> getAllUsers();

    @Query("SELECT * FROM GitHubUser WHERE id IN (:userIds)")
    List<GitHubUser> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM GitHubUser WHERE login LIKE :login LIMIT 1")
    GitHubUser findByLogin(String login);

    @Query("DELETE FROM GitHubUser")
    void deleteUserList();

    @Insert
    void insertUsers(List<GitHubUser> users);

    @Update
    void updateUser(GitHubUser user);

    @Update
    void updateUsers(List<GitHubUser> users);

    @Delete
    void deleteUsers(List<GitHubUser> users);
}