package com.jithu.themoviedb;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jithu.themoviedb.Data.Movie;
import com.jithu.themoviedb.Data.ResponseJson;
import com.jithu.themoviedb.Utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

        private static String POPULAR="popular";
        private static String TOPRATED="top_rated";

        private TextView mMoviesTextView;
        private ArrayList<Movie> movies ;
        private GridView listOfMovies;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

           // mMoviesTextView =(TextView) findViewById(R.id.mMovieTextView) ;
            loadMovieData(POPULAR);

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

        String apiKey ="";
        new FetchMoviesTask().execute(apiKey,sortOption);
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

            if(movieData!=null){
                //get object
                //append to grid view
                //mMoviesTextView.append(movieData);
                ResponseJson moviesResponse = new Gson().fromJson(movieData, ResponseJson.class);
                movies=moviesResponse.getResults();
                //mMoviesTextView.append(moviesResponse.getResults().size()+"");
                listOfMovies = (GridView) findViewById(R.id.listOfMovies);
                listOfMovies.setAdapter(new ListOfMoviesAdapter());

            }
        }


    }

    class ViewHolder{
        ImageView posterImage;

    }

        class ListOfMoviesAdapter extends BaseAdapter {

            private LayoutInflater layoutInflater;
            public ListOfMoviesAdapter(){

                layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            }

            @Override
            public int getCount() {
                return movies.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {


                if(convertView==null){
                    convertView= layoutInflater.inflate(R.layout.cell_poster,null);

                    ViewHolder viewHolder =new ViewHolder();
                    viewHolder.posterImage = (ImageView) convertView.findViewById(R.id.posterImage);
                    convertView.setTag(viewHolder);
                }

                ViewHolder viewHolder =(ViewHolder) convertView.getTag();

                Movie movie = movies.get(position);
                //viewHolder.posterURL.setText(movie.getPoster_path());
                Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w185/"+movie.getPoster_path()).into(viewHolder.posterImage);
                return convertView;
            }
        }
}
