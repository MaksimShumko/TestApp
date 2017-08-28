package com.example.maksim.testapp.models;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by Maksim on 2017-07-20.
 */

public class GitHubUser implements Parcelable {
    private String login;
    private String name;
    private String avatarUrl;
    private String url;
    private double score;

    public static final Creator<GitHubUser> CREATOR = new Creator<GitHubUser>() {
        @Override
        public GitHubUser createFromParcel(Parcel in) {
            return new GitHubUser(in);
        }

        @Override
        public GitHubUser[] newArray(int size) {
            return new GitHubUser[size];
        }
    };

    private GitHubUser(Parcel in) {
        login = in.readString();
        name = in.readString();
        avatarUrl = in.readString();
        url = in.readString();
        score = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(login);
        dest.writeString(name);
        dest.writeString(avatarUrl);
        dest.writeString(url);
        dest.writeDouble(score);
    }

    public GitHubUser(String login, String name, String avatarUrl, String url, double score) {
        this.login = login;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.url = url;
        this.score = score;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getUrl() {
        return url;
    }

    public double getScore() {
        return score;
    }
}
