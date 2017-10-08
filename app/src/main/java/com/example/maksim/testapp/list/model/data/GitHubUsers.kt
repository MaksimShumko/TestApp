package com.example.maksim.testapp.list.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Maksim on 2017-08-28.
 */

class GitHubUsers {
    @SerializedName("total_count")
    @Expose
    var totalCount: Int? = null
    @SerializedName("incomplete_results")
    @Expose
    var incompleteResults: Boolean? = null
    @SerializedName("items")
    @Expose
    var items: MutableList<GitHubUser>? = null
}
