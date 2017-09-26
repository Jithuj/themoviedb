package com.jithu.themoviedb;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jithu.themoviedb.Data.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jithuj on 9/25/2017.
 */

public class ListOfMoviesAdapter extends RecyclerView.Adapter<ListOfMoviesAdapter.ListOfMoviesAdapterViewHolder> {

    private final ListOfMoviesOnClickHandler mClickHandler;
    private ArrayList<Movie> mMovies;

    public ListOfMoviesAdapter(ArrayList<Movie> movies, ListOfMoviesOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
        mMovies = movies;
    }

    @Override
    public ListOfMoviesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.cell_poster;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ListOfMoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListOfMoviesAdapterViewHolder holder, int position) {

        Movie movie = mMovies.get(position);
        //viewHolder.posterURL.setText(movie.getPoster_path());
        Picasso.with(holder.posterImage.getContext()).load("http://image.tmdb.org/t/p/w185/" + movie.getPoster_path()).into(holder.posterImage);

    }

    @Override
    public int getItemCount() {
        if (null == mMovies) return 0;
        return mMovies.size();
    }

    public void setMoviesData(ArrayList<Movie> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    public interface ListOfMoviesOnClickHandler {
        void onClick(String moveName);
    }

    public class ListOfMoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView posterImage;

        public ListOfMoviesAdapterViewHolder(View itemView) {
            super(itemView);
            posterImage = (ImageView) itemView.findViewById(R.id.posterImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String moveName = mMovies.get(adapterPosition).getTitle();
            mClickHandler.onClick(moveName);
        }
    }
}
