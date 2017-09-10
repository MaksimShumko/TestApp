package com.example.maksim.testapp.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.maksim.testapp.list.data.GitHubUser;

/**
 * Created by Maksim on 2017-09-10.
 */

@Database(entities = {GitHubUser.class}, version = 2)
public abstract class RoomSqlDatabase extends RoomDatabase {
    public abstract GitHubUserDao getUserDao();
}
