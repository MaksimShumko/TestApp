package com.example.maksim.testapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maksim on 2017-07-20.
 */

public class Model implements Parcelable {
    private String title;
    private String description;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
    }

    public Model(String title, String description) {
        this.title = title;
        this.description = description;
    }

    private Model(Parcel in) {
        title = in.readString();
        description = in.readString();
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
