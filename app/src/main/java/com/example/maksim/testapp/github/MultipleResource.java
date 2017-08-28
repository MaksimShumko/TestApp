package com.example.maksim.testapp.github;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Maksim on 2017-08-28.
 */

public class MultipleResource {
    @SerializedName("total_count")
    @Expose
    public Integer totalCount;
    @SerializedName("incomplete_results")
    @Expose
    public Boolean incompleteResults;
    @SerializedName("items")
    @Expose
    public List<Datum> items = null;

    public class Datum {

        @SerializedName("login")
        @Expose
        public String login;
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("avatar_url")
        @Expose
        public String avatarUrl;
        @SerializedName("gravatar_id")
        @Expose
        public String gravatarId;
        @SerializedName("url")
        @Expose
        public String url;
        @SerializedName("html_url")
        @Expose
        public String htmlUrl;
        @SerializedName("followers_url")
        @Expose
        public String followersUrl;
        @SerializedName("following_url")
        @Expose
        public String followingUrl;
        @SerializedName("gists_url")
        @Expose
        public String gistsUrl;
        @SerializedName("starred_url")
        @Expose
        public String starredUrl;
        @SerializedName("subscriptions_url")
        @Expose
        public String subscriptionsUrl;
        @SerializedName("organizations_url")
        @Expose
        public String organizationsUrl;
        @SerializedName("repos_url")
        @Expose
        public String reposUrl;
        @SerializedName("events_url")
        @Expose
        public String eventsUrl;
        @SerializedName("received_events_url")
        @Expose
        public String receivedEventsUrl;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("site_admin")
        @Expose
        public Boolean siteAdmin;
        @SerializedName("score")
        @Expose
        public Double score;

    }
}