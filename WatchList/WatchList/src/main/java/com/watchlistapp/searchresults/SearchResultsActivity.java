package com.watchlistapp.searchresults;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.watchlistapp.R;
import com.watchlistapp.fullmoviedescription.FullMovieDescriptionActivity;
import com.watchlistapp.themoviedb.SearchMovies;

/**
 * Created by VEINHORN on 02/12/13.
 */
public class SearchResultsActivity extends Activity {

    private TextView testTextView;
    private String queryString;
    private ListView searchResultsListView;
    private SearchResultsItemAdapter searchResultsItemAdapter;
    private SearchResultsContainer searchResultsContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        queryString = getIntent().getStringExtra("query");

        searchResultsListView = (ListView)findViewById(R.id.search_results_listview);

        searchResultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchResultsActivity.this, FullMovieDescriptionActivity.class);
                intent.putExtra("movieId", searchResultsContainer.getSearchResultsItemArrayList().get(position).getMovieId());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
            }
        });

        searchResultsContainer = new SearchResultsContainer();
        // Get the query that we sent from RecentActivity
        searchResultsItemAdapter = new SearchResultsItemAdapter(SearchResultsActivity.this, searchResultsContainer, SearchResultsActivity.this, searchResultsListView);
        searchResultsListView.setAdapter(searchResultsItemAdapter);

        SearchMovies searchMovies = new SearchMovies(SearchResultsActivity.this, queryString, searchResultsItemAdapter, searchResultsContainer, SearchResultsActivity.this);
        searchMovies.execute();
    }
}
