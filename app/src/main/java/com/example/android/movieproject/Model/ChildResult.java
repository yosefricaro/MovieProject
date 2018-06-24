package com.example.android.movieproject.Model;

/**
 * Created by Yosefricaro on 02/12/2016.
 */

public class ChildResult {
    String poster_path;
    Long id;
    Integer total_results;

    public ChildResult(String tempPoster , Long Id) {
        this.poster_path = tempPoster;
        this.id = Id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotal_results() {return total_results;}

    public void setTotal_results(Integer total_results) {this.total_results = total_results;}

}
