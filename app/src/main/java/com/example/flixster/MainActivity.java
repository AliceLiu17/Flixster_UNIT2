package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.Models.Movie;
import com.example.flixster.adapters.MovieAdapter;
import com.facebook.stetho.common.ArrayListAccumulator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG = "MainActivity"; //we want tag so we can easily log data

    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Define recycler view:
        RecyclerView rvMovies = findViewById(R.id.rvMoviews);

        movies = new ArrayList<>();

        //RECYCLERVIEW PART: Create an adapter and set it into a local variable
        MovieAdapter movieAdapter = new MovieAdapter(this, movies);

        //RECYCLERVIEW PART: Set the adapter on the recycler view
        rvMovies.setAdapter(movieAdapter);

        //RECYCLERVIEW PART: Set a layout manager on the recycler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));


        AsyncHttpClient client = new AsyncHttpClient();
        //to get request on the NOW_PLAYING_URL:
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");

                //The object we want is inside JSON data so we want to pull it out:
                //json.jsonObject
                //refractor-->introduce variable--> jsonObject:
                JSONObject jsonObject = json.jsonObject;

                //inside jsonObject we want to get an jsonArray called results:
                //jsonObject.getJSONArray
                //refractor-->introduce variable--> results + hover over getJSONArray and surround with try and catch:
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    //if we did succeed in getting out, i=info level
                    Log.i(TAG, "Results: " + results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    //to verify the data in movies:
                    Log.i(TAG, "Movies: " + movies.size()); //shows you that the size is 20, which corresponds to the data we have.
                } catch (JSONException e) {
                    //if you do catch the JSONException log in an error
                    Log.e(TAG, "Hit json exception", e);
                    //COMMENT OUT/DELETE: e.printStackTrace();
                }

                //hover over getJSONArray and surround with try and catch

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");

            }
        });
    }
}