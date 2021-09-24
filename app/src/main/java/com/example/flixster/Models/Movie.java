package com.example.flixster.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

//import parcels
@Parcel

public class Movie {

    int movieId;
    String backdropPath; //to change the landscape mode's photo
    String posterPath;
    String title;
    String overview;
    //add the ratings bar...
    double ratings;

    //Add an empty constructor as needed for the parcel library
    public Movie() {

    }

    public Movie(JSONObject jsonObject) throws JSONException {
        backdropPath = jsonObject.getString("backdrop_path"); //for landscape...
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        //get information from JSON about the rating bar...
        ratings = jsonObject.getDouble("vote_average"); //double b/c ratings is in double...

        movieId = jsonObject.getInt("id");

    }
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i=0; i< movieJsonArray.length(); i++){
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }
    //GENERATE....GETTER...ALL 3 OPTIONS:

    public String getPosterPath() {
        // %s= here's what I want to replace with poster_path and hardcode the size url with width 342.
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    //continue for landscape...
    public String getBackdropPath(){
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    //add a getter for ratings...
    public double getRatings() {
        return ratings;
    }

    //add getter for movie ID...
    public int getMovieId() {
        return movieId;
    }
}
