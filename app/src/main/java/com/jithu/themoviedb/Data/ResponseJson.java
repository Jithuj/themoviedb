package com.jithu.themoviedb.Data;

import java.util.ArrayList;

/**
 * Created by jithuj on 8/28/2017.
 */

public class ResponseJson {

    private ArrayList<Movie> results;

    public ArrayList<Movie> getResults() {
        return results;
    }

    public void setResults(ArrayList<Movie> results) {
        this.results = results;
    }
}
