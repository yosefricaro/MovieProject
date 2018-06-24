package com.example.android.movieproject.Model;

import io.realm.RealmObject;

/**
 * Created by Yosefricaro on 02/12/2016.
 */

public class Detail extends RealmObject {
    String original_title;
    String release_date;
    Float vote_average;
    String overview;
    Integer runtime;
    String poster_path;

    public String getOriginal_title() {
        return original_title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public Float getVote_average() {
        return vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public String getPoster_path() {
        return poster_path;
    }
}
