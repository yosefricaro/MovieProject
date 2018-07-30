package com.example.android.movieproject.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.movieproject.AdapterMain.RecyclerViewAdapter;
import com.example.android.movieproject.Model.ChildResult;
import com.example.android.movieproject.Model.Result;
import com.example.android.movieproject.Model.api.MovieService;
import com.example.android.movieproject.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {
    private Subscription subscriber = new CompositeSubscription();
    String ubahQuery;
    int count = 0;
    boolean check = false;
    boolean check2 = false;

    public void tempQuery(String temp){
        ubahQuery = temp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAllItemList();

        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshContainer);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllItemList();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getAllItemList();
                        refreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllItemList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        if(id == R.id.about){
            AlertDialog.Builder alertDialog  = new AlertDialog.Builder(this);
            alertDialog.setMessage("Developed by Yosef Ricaro Latif");
            alertDialog.setTitle("About");
            alertDialog.setCancelable(true);
            alertDialog.create().show();
            return true;
        }

        if(id == R.id.action_search){
            AlertDialog.Builder alertDialog  = new AlertDialog.Builder(this);
            alertDialog.setTitle("Search");
            alertDialog.setCancelable(true);
            // Set up the input
            final EditText input = new EditText(this);

            FrameLayout container = new FrameLayout(this);
            FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin= 40; // remember to scale correctly
            params.rightMargin= 40;
            input.setLayoutParams(params);
            container.addView(input);
            alertDialog.setView(container);

            // Set up the buttons
            alertDialog.setPositiveButton("OK", (dialog, which) -> {
                String temp = input.getText().toString();
                tempQuery(temp);
                if(!temp.isEmpty()) {
                    getAllItemList();
                }
            });
            alertDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            alertDialog.create().show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getAllItemList(){
        ProgressBar mLoading = (ProgressBar) findViewById(R.id.progress_bar);
        mLoading.setVisibility(View.VISIBLE);

        GridLayoutManager gridLayout;
        gridLayout = new GridLayoutManager(MainActivity.this, 2);

        final RecyclerView recyclerView;
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayout);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        final List<ChildResult> allItems = new ArrayList<>();

        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(this);
        final String ubahSort = sharedPrefs.getString(
                getString(R.string.sort_value),
                getString(R.string.popular));
        final String ubahYear = sharedPrefs.getString(
                getString(R.string.year_value),
                getString(R.string.y2016));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final MovieService movieTemp = retrofit.create(MovieService.class);

        if(ubahQuery != null){
                movieTemp.getMovieQ(ubahQuery).enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        getResponse(response.body(), recyclerView, allItems, mLoading);
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        mLoading.setVisibility(View.INVISIBLE);
                        Toast.makeText(MainActivity.this, "Tidak ada internet", Toast.LENGTH_SHORT).show();
                    }
                });
        }
        else if(ubahSort.equals("popularity.desc")) {
            movieTemp.getMovieP(ubahSort, ubahYear).enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    getResponse(response.body(), recyclerView, allItems, mLoading);
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    mLoading.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "Tidak ada internet", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
//            DBHelper dbHelper = new DBHelper(getBaseContext());
//
//            dbHelper.insertRating("2010", 0);
//            dbHelper.insertRating("2011", 0);
//            dbHelper.insertRating("2012", 0);
//            dbHelper.insertRating("2013", 0);
//            dbHelper.insertRating("2014", 0);
//            dbHelper.insertRating("2015", 0);
//            dbHelper.insertRating("2016", 0);
//            dbHelper.insertRating("2017", 0);
//            dbHelper.insertRating("2018", 0);
//
//            Cursor cursor = dbHelper.getRating(ubahYear);
//            count = cursor.getColumnIndexOrThrow("vote");

            switch (ubahYear){
                case "2018":count=1150;break;
                case "2017":count=4600;break;
                case "2016":count=4100;break;
                case "2015":count=4150;break;
                case "2014":count=5950;break;
                case "2013":count=5000;break;
                case "2012":count=3850;break;
                case "2011":count=3300;break;
                case "2010":count=3150;break;
            }

//            while (!check2) {
                movieTemp.getMovieR(ubahSort, ubahYear, count).enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
//                        if (response.body().getResults().size() == 20) {
//                            check2 = true;
//                            dbHelper.updateRating(ubahYear, count);
//                            getResponse(response.body(), recyclerView, allItems, mLoading);
//
//                        } else if (response.body().getResults().size() < 20) {
//                            count -= 50;
//                            check = true;
//                        } else {
//                            if (!check) {
//                                count += 50;
//                            } else {
//                                check2 = true;
//                                dbHelper.updateRating(ubahYear, count);
                                getResponse(response.body(), recyclerView, allItems, mLoading);
//                            }
//                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        mLoading.setVisibility(View.INVISIBLE);
                        Toast.makeText(MainActivity.this, "Tidak ada internet", Toast.LENGTH_SHORT).show();
                    }
                });
//            }
        }

        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(MainActivity.this, allItems);
        recyclerView.setAdapter(rcAdapter);
    }

    public void getResponse(Result result, RecyclerView recyclerView, List<ChildResult> allItems, ProgressBar mLoading){
        for (int i = 0; i < result.getResults().size(); i++) {
            try {
                allItems.add(new ChildResult("http://image.tmdb.org/t/p/w342"
                        .concat(result.getResults().get(i).getPoster_path())
                        , result.getResults().get(i).getId()));
            } catch (Exception e) {
            }
        }

        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(MainActivity.this, allItems);
        recyclerView.setAdapter(rcAdapter);
        mLoading.setVisibility(View.INVISIBLE);
    }
}