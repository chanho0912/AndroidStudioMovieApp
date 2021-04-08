package edu.skku.map.a2016310526_personalproj.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.skku.map.a2016310526_personalproj.Model.MovieResponseResults;
import edu.skku.map.a2016310526_personalproj.Model.PersonResponseResults;
import edu.skku.map.a2016310526_personalproj.R;
import edu.skku.map.a2016310526_personalproj.Third_Activity;
import edu.skku.map.a2016310526_personalproj.ViewHolders.SearchViewHolder;

public class PersonSearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    private Activity activity;
    private List<PersonResponseResults> results;

    public PersonSearchAdapter(Activity activity, List<PersonResponseResults> results) {
        this.activity = activity;
        this.results = results;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.search_layout_item,parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, final int position) {
        final PersonResponseResults responseResults = results.get(position);
        searchViewHolder.setPosterImageView(activity,responseResults.getProfile_path());

        String title = responseResults.getName();

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
                intent.putExtra("title", responseResults.getName());
                intent.putExtra("original_title", "Actor or Actress Poster...");
                intent.putExtra("poster_path", responseResults.getProfile_path());
                intent.putExtra("overview", responseResults.getAdult());
                intent.putExtra("release_date", responseResults.getPopularity());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}