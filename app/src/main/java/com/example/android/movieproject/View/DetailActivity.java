package com.example.android.movieproject.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.movieproject.AdapterVideo.RecyclerVideoAdapter;
import com.example.android.movieproject.Model.ChildVideo;
import com.example.android.movieproject.Model.Detail;
import com.example.android.movieproject.Model.Video;
import com.example.android.movieproject.Model.api.MovieService;
import com.example.android.movieproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.defaultValue;

/**
 * Created by Yosefricaro on 02/12/2016.
 */

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Long id = getIntent().getLongExtra("movieId", defaultValue);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final MovieService detail = retrofit.create(MovieService.class);
        detail.getDetail(id).enqueue(new Callback<Detail>() {
            @Override
            public void onResponse(Call<Detail> call, Response<Detail> response) {
                Detail detail = response.body();
                TextView title = (TextView) findViewById(R.id.titleMovie);
                ImageView poster = (ImageView) findViewById(R.id.poster_path);
                TextView year = (TextView) findViewById(R.id.year);
                TextView rating = (TextView) findViewById(R.id.rating);
                TextView overview = (TextView) findViewById(R.id.overview);
                TextView duration = (TextView) findViewById(R.id.duration);

                title.setText(detail.getOriginal_title());
                year.setText(detail.getRelease_date().substring(0,4));
                rating.setText(detail.getVote_average().toString() + "/10");
                duration.setText(detail.getRuntime() + "min");
                overview.setText(detail.getOverview());

                Picasso.with(DetailActivity.this)
                        .load("http://image.tmdb.org/t/p/w342".concat(detail.getPoster_path()))
                        .into(poster);
            }

            @Override
            public void onFailure(Call<Detail> call, Throwable t) {

            }
        });

        final List<ChildVideo> allItems = new ArrayList<ChildVideo>();
        final RecyclerView recyclerView;

        LinearLayoutManager lLayout = new LinearLayoutManager(DetailActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        lLayout.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_video);
        recyclerView.setLayoutManager(lLayout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setFocusable(false);

        detail.getVideo(id).enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {
                Video video = response.body();
                for (int i = 0; i < video.getResults().size(); i++) {
                    allItems.add(new ChildVideo(video.getResults().get(i).getKey()));
                }
                RecyclerVideoAdapter rcAdapter = new RecyclerVideoAdapter(DetailActivity.this, allItems);
                recyclerView.setAdapter(rcAdapter);
            }

            @Override
            public void onFailure(Call<Video> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Tidak ada internet", Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerVideoAdapter rcAdapter = new RecyclerVideoAdapter(DetailActivity.this, allItems);
        recyclerView.setAdapter(rcAdapter);
    }
}
