package com.example.maksim.testapp.list.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Maksim on 2017-09-10.
 */

@Entity(tableName = "GitHubUser")
public class GitHubUser {
    @SerializedName("login")
    @Expose
    public String login;
    @SerializedName("id")
    @Expose
    @PrimaryKey
    public Integer id;
    @SerializedName("avatar_url")
    @Expose
    public String avatarurl;
    @SerializedName("gravatar_id")
    @Expose
    public String gravatarid;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("html_url")
    @Expose
    public String htmlurl;
    @SerializedName("followers_url")
    @Expose
    public String followersurl;
    @SerializedName("following_url")
    @Expose
    public String followingurl;
    @SerializedName("gists_url")
    @Expose
    public String gistsurl;
    @SerializedName("starred_url")
    @Expose
    public String starredurl;
    @SerializedName("subscriptions_url")
    @Expose
    public String subscriptionsurl;
    @SerializedName("organizations_url")
    @Expose
    public String organizationsurl;
    @SerializedName("repos_url")
    @Expose
    public String reposurl;
    @SerializedName("events_url")
    @Expose
    public String eventsurl;
    @SerializedName("received_events_url")
    @Expose
    public String receivedeventsurl;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("site_admin")
    @Expose
    public Boolean siteadmin;
    @SerializedName("score")
    @Expose
    public Double score;
}
