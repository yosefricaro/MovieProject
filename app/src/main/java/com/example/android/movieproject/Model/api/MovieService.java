package com.example.android.movieproject.Model.api;

import com.example.android.movieproject.Model.Detail;
import com.example.android.movieproject.Model.Result;
import com.example.android.movieproject.Model.Video;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Yosefricaro on 02/12/2016.
 */

public interface MovieService {
    @GET("3/discover/movie?api_key=f158e7d37fd68fcf28c1239e333d2278")
    Call<Result> getMovieP(@Query("sort_by") String sort, @Query("primary_release_year") String year);

    @GET("3/discover/movie?api_key=f158e7d37fd68fcf28c1239e333d2278")
    Call<Result> getMovieR(@Query("sort_by") String sort, @Query("primary_release_year") String year, @Query("vote_count.gte") Integer count);

    @GET("3/search/movie?api_key=f158e7d37fd68fcf28c1239e333d2278")
    Call<Result> getMovieQ(@Query("query") String query);

    @GET("3/movie/{movieId}?api_key=f158e7d37fd68fcf28c1239e333d2278")
    Call<Detail> getDetail(@Path("movieId") Long movieId);

    @GET("3/movie/{movieId}/videos?api_key=f158e7d37fd68fcf28c1239e333d2278")
    Call<Video> getVideo(@Path("movieId") Long movieId);
}
