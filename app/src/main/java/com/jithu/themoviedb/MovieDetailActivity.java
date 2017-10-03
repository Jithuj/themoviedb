package com.jithu.themoviedb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.jithu.themoviedb.Data.Movie;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MovieDetailActivity extends AppCompatActivity {
    private TextView mMovieDetails;
    private ImageView mPosterImage;
    private TextView mReleaseDate;
    private TextView mUserRating;
    private TextView mOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mMovieDetails = (TextView) findViewById(R.id.movie_name);
        mPosterImage = (ImageView) findViewById(R.id.posterImage);
        mReleaseDate = (TextView) findViewById(R.id.release_date);
        mUserRating = (TextView) findViewById(R.id.user_rating);
        mOverview = (TextView) findViewById(R.id.overview);

        Intent intentThatStartedThisActivity = getIntent();

        // COMPLETED (2) Display the weather forecast that was passed from MainActivity
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("movie")) {
                Bundle data = getIntent().getExtras();
                Movie movie = data.getParcelable("movie");
                mMovieDetails.setText(movie.getTitle());
                Picasso.with(mPosterImage.getContext()).load("http://image.tmdb.org/t/p/w185/" + movie.getPoster_path()).into(mPosterImage);
                mReleaseDate.setText(movie.getRelease_date());
                mUserRating.setText(movie.getVote_average());
                mOverview.setText(movie.getOverview());


            }
        }

    }
}
