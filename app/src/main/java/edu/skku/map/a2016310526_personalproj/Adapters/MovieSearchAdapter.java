package edu.skku.map.a2016310526_personalproj.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.skku.map.a2016310526_personalproj.Model.MovieResponseResults;
import edu.skku.map.a2016310526_personalproj.R;
import edu.skku.map.a2016310526_personalproj.Third_Activity;
import edu.skku.map.a2016310526_personalproj.ViewHolders.SearchViewHolder;

public class MovieSearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    private Activity activity;
    private List<MovieResponseResults> results;

    public MovieSearchAdapter(Activity activity, List<MovieResponseResults> results) {
        this.activity = activity;
        this.results = results;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(R.layout.search_layout_item,parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int position) {
        final MovieResponseResults responseResults = results.get(position);

        searchViewHolder.setPosterImageView(activity,responseResults.getPoster_path());

        String title = responseResults.getTitle();

        if(title != null){
            searchViewHolder.posterTitle.setVisibility(View.VISIBLE);
            searchViewHolder.posterTitle.setText(title);
        }
        else{
            searchViewHolder.posterTitle.setVisibility(View.GONE);
        }

        searchViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, Third_Activity.class);
                intent.putExtra("title", responseResults.getTitle());
                intent.putExtra("original_title", responseResults.getOriginal_title());
                intent.putExtra("poster_path", responseResults.getPoster_path());
                intent.putExtra("overview", responseResults.getOverview());
                intent.putExtra("release_date", responseResults.getRelease_date());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
