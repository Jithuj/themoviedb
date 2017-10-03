package com.jithu.themoviedb;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jithu.themoviedb.Data.Movie;
import com.jithu.themoviedb.Data.ResponseJson;
import com.jithu.themoviedb.Utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListOfMoviesAdapter.ListOfMoviesOnClickHandler {

    private static String POPULAR = "popular";
    private static String TOPRATED = "top_rated";
    private ArrayList<Movie> movies;
    private GridView listOfMovies;

    private RecyclerView mRecyclerView;
    private com.jithu.themoviedb.ListOfMoviesAdapter mMovieListAdapter;

    /* private TextView mErrorMessageDisplay;

     private ProgressBar mLoadingIndicator;*/
    private Toast mToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        loadMovieData(POPULAR);
        mMovieListAdapter = new ListOfMoviesAdapter(movies, this);
        mRecyclerView.setAdapter(mMovieListAdapter);
           // mMoviesTextView =(TextView) findViewById(R.id.mMovieTextView) ;


        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sort_most_popular:
                loadMovieData(POPULAR);
                return true;
            case R.id.sort_top_rated:
                loadMovieData(TOPRATED);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadMovieData(String sortOption){

        String apiKey = "19369bd50ed61d9cffa106363825a2c8";
        new FetchMoviesTask().execute(apiKey,sortOption);
    }

    @Override
    public void onClick(Movie movie) {
        //String toastMessage = "movie Name - " + moveName;
        //mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);
        //mToast.show();
        Context context = this;
        Class destinationClass = MovieDetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        // COMPLETED (1) Pass the weather to the DetailActivity
        intentToStartDetailActivity.putExtra("movie", movie);
        startActivity(intentToStartDetailActivity);
    }

    public class FetchMoviesTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String apiKey = params[0];
            String sortOption=params[1];
            URL movieRequestUrl = NetworkUtils.buildUrl(apiKey,sortOption);


            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestUrl);


                return jsonMovieResponse;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String movieData){
            //mLoadingIndicator.setVisibility(View.INVISIBLE);
            if(movieData!=null){
                ResponseJson moviesResponse = new Gson().fromJson(movieData, ResponseJson.class);
                movies=moviesResponse.getResults();
                Log.e("MainActivity", "hit movies - " + movies);
                mMovieListAdapter.setMoviesData(movies);


            }
        }


    }


}
