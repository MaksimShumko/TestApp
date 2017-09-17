package com.example.maksim.testapp.githubapi.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.maksim.testapp.list.model.data.GitHubUser;
import com.example.maksim.testapp.details.model.data.GitHubUserDetails;

/**
 * Created by Maksim on 2017-09-10.
 */

@Database(entities = {GitHubUser.class, GitHubUserDetails.class}, version = 1)
public abstract class RoomSqlDatabase extends RoomDatabase {
    public static final String DATABASE_NAME_GIT_HUB = "GIT_HUB_USERS";
    public abstract GitHubUserDao getUserDao();
    public abstract GitHubUserDetailsDao getUserDetailsDao();
}
