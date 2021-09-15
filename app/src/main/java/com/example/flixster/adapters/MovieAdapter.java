package com.example.flixster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.Models.Movie;
import com.example.flixster.R;

import java.util.List;

//further define the adapter with extends... it is parameterized by the viewholder...
// hence we define the viewholder in move adapter, not the one in recycler view
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    //(1) the context:
    Context context;
    //(2) the data:
    List<Movie> movies; //this will be passed into the microconstructor

    //use android studio to help you build a constructor...


    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    } //after this start to fill out the 'meat' of the adapter

    //to fill them out we need a few key pieces...
    //(1) the context so we know where the adapter is being constructed from
    //(2) the actual data that the adapter needs to hold onto.

    //onCreateViewHolder: usually involves inflating a layout (item movie) from XML and returning the view holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);//takes in a context
        //grab the view into the viewholder:
        return new ViewHolder(movieView);
    }

    //onBindViewHolder: involves populating data into the item through holder
    //take the data from the position and put it into the holder contained inside the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        //get the movie at the position
        Movie movie = movies.get(position);
        //bind the movie data into the viewholder
        holder.bind(movie);
    }

    //getItemCount: returns the total count of items in the list.
    @Override
    public int getItemCount() {
        return movies.size(); //returns the list of movies AKA the size of movies.
    }

    //establish a new viewholder class that will extend to the recycler viewholder + complete constructor
    //viewholder is a representation of our row in the recycler view (contents inside item_movie.xml)
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvOverview;
        ImageView IvPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //identify the viewholder.
            //next step is to identify the adapter...(top)
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            IvPoster = itemView.findViewById(R.id.IvPoster);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            String imageUrl;
            //if phone is in landscape then we want to set image URL=landscape backdropImage
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                //then image URL = landscape backdrop image
                imageUrl = movie.getBackdropPath();
            } else {
                //else image URL = poster image
                imageUrl = movie.getPosterPath();
            }

            //to render an image we will use a library called Glide (offered by codepath)
            //load the URL into a particular view, into the IvPoster
            Glide.with(context).load(imageUrl).into(IvPoster);
        }
    }
}
