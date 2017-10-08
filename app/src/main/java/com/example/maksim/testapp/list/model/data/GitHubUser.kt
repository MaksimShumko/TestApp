package com.example.maksim.testapp.list.model.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Maksim on 2017-09-10.
 */

@Entity
class GitHubUser {
    @SerializedName("login")
    @Expose
    var login: String? = null
    @SerializedName("id")
    @Expose
    @PrimaryKey
    var id: Int? = null
    @SerializedName("avatar_url")
    @Expose
    var avatarurl: String? = null
    @SerializedName("gravatar_id")
    @Expose
    var gravatarid: String? = null
    @SerializedName("url")
    @Expose
    var url: String? = null
    @SerializedName("html_url")
    @Expose
    var htmlurl: String? = null
    @SerializedName("followers_url")
    @Expose
    var followersurl: String? = null
    @SerializedName("following_url")
    @Expose
    var followingurl: String? = null
    @SerializedName("gists_url")
    @Expose
    var gistsurl: String? = null
    @SerializedName("starred_url")
    @Expose
    var starredurl: String? = null
    @SerializedName("subscriptions_url")
    @Expose
    var subscriptionsurl: String? = null
    @SerializedName("organizations_url")
    @Expose
    var organizationsurl: String? = null
    @SerializedName("repos_url")
    @Expose
    var reposurl: String? = null
    @SerializedName("events_url")
    @Expose
    var eventsurl: String? = null
    @SerializedName("received_events_url")
    @Expose
    var receivedeventsurl: String? = null
    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("site_admin")
    @Expose
    var siteadmin: Boolean? = null
    @SerializedName("score")
    @Expose
    var score: Double? = null
}
