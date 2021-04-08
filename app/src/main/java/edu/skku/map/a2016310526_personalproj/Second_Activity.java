package edu.skku.map.a2016310526_personalproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

import edu.skku.map.a2016310526_personalproj.Adapters.MovieSearchAdapter;
import edu.skku.map.a2016310526_personalproj.Adapters.PersonSearchAdapter;
import edu.skku.map.a2016310526_personalproj.Client.RetrofitClient;
import edu.skku.map.a2016310526_personalproj.Model.MovieResponse;
import edu.skku.map.a2016310526_personalproj.Model.MovieResponseResults;
import edu.skku.map.a2016310526_personalproj.Model.PersonResponse;
import edu.skku.map.a2016310526_personalproj.Model.PersonResponseResults;
import edu.skku.map.a2016310526_personalproj.interfaces.RetrofitService;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Second_Activity extends AppCompatActivity {
    private NiceSpinner sourceSpinner;
    private AppCompatEditText queryEditText;

    private AppCompatButton querySearchButton;

    private RecyclerView resultRecyclerView;

    private String movie = "By Movie Title";
    private String person = "By Person Name";

    private RetrofitService retrofitService;

    private MovieSearchAdapter movieSearchAdapter;
    private PersonSearchAdapter personSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        sourceSpinner = findViewById(R.id.source_spinner);
        queryEditText = findViewById(R.id.query_edit_text);

        querySearchButton = findViewById(R.id.query_search_button);

        resultRecyclerView = findViewById(R.id.results_recycler_view);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            resultRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        }
        else{
            resultRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        }

        Paper.init(this);
        retrofitService =  RetrofitClient.getClient().create(RetrofitService.class);
        final ArrayList<String> category = new ArrayList<>();

        category.add(movie);
        category.add(person);

        sourceSpinner.attachDataSource(category);

        if(Paper.book().read("position") != null){
            int position = Paper.book().read("position");

            sourceSpinner.setSelectedIndex(position);
        }

        int position = sourceSpinner.getSelectedIndex();

        if(position == 0){
            queryEditText.setHint("Enter any movie title...");
        }
        else{
            queryEditText.setHint("Enter any person name...");
        }

        sourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    queryEditText.setHint("Enter any movie title...");
                }
                else{
                    queryEditText.setHint("Enter any person name...");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(Paper.book().read("cache") != null){
            String results = Paper.book().read("cache");
            if(Paper.book().read("source") !=null){
                String source = Paper.book().read("source");

                if(source.equals("movie")){
                    MovieResponse movieResponse = new Gson().fromJson(results, MovieResponse.class);

                    if(movieResponse != null){
                        List<MovieResponseResults> movieResponseResults = movieResponse.getResults();

                        movieSearchAdapter = new MovieSearchAdapter(Second_Activity.this, movieResponseResults);

                        resultRecyclerView.setAdapter(movieSearchAdapter);

                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(Second_Activity.this,R.anim.layout_slide_right);

                        resultRecyclerView.setLayoutAnimation(controller);
                        resultRecyclerView.scheduleLayoutAnimation();

                        Paper.book().write("cache", new Gson().toJson(movieResponse));

                        Paper.book().write("source", "movie");
                    }
                }
                else{
                    PersonResponse personResponse = new Gson().fromJson(results, PersonResponse.class);

                    if(personResponse != null){
                        List<PersonResponseResults> personResponseResults = personResponse.getResults();

                        personSearchAdapter = new PersonSearchAdapter(Second_Activity.this, personResponseResults);

                        resultRecyclerView.setAdapter(personSearchAdapter);

                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(Second_Activity.this,R.anim.layout_slide_right);

                        resultRecyclerView.setLayoutAnimation(controller);
                        resultRecyclerView.scheduleLayoutAnimation();

                        Paper.book().write("cache", new Gson().toJson(personResponse));

                        Paper.book().write("source", "person");
                    }
                }
            }
        }

        querySearchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(queryEditText.getText() != null){
                    String query = queryEditText.getText().toString();

                    if(query.equals("") || query.equals(" ")){
                        Toast.makeText(Second_Activity.this,"Please enter any text...",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        queryEditText.setText("");

                        String finalQuery = query.replaceAll(" ", "+");

                        if(category.size() >0){
                            String categoryName = category.get(sourceSpinner.getSelectedIndex());

                            if(categoryName.equals(movie)){
                                Call<MovieResponse> movieResponseCall = retrofitService.getMovieByQuery(BuildConfig.THE_MOVIE_DB_API_KEY,finalQuery);

                                movieResponseCall.enqueue(new Callback<MovieResponse>() {
                                    @Override
                                    public void onResponse (@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                                        MovieResponse movieResponse = response.body();

                                        if(movieResponse != null){
                                            List<MovieResponseResults> movieResponseResults = movieResponse.getResults();

                                            movieSearchAdapter = new MovieSearchAdapter(Second_Activity.this, movieResponseResults);

                                            resultRecyclerView.setAdapter(movieSearchAdapter);

                                            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(Second_Activity.this,R.anim.layout_slide_right);

                                            resultRecyclerView.setLayoutAnimation(controller);
                                            resultRecyclerView.scheduleLayoutAnimation();

                                            Paper.book().write("cache", new Gson().toJson(movieResponse));

                                            Paper.book().write("source", "movie");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<MovieResponse> call, Throwable t) {

                                    }
                                });
                            }
                            else{
                                Call<PersonResponse> personResponseCall = retrofitService.getPersonsByQuery(BuildConfig.THE_MOVIE_DB_API_KEY,finalQuery);

                                personResponseCall.enqueue(new Callback<PersonResponse>() {
                                    @Override
                                    public void onResponse(@NonNull Call<PersonResponse> call,@NonNull Response<PersonResponse> response) {
                                        PersonResponse personResponse = response.body();

                                        if(personResponse != null){
                                            List<PersonResponseResults> personResponseResults = personResponse.getResults();

                                            personSearchAdapter = new PersonSearchAdapter(Second_Activity.this, personResponseResults);

                                            resultRecyclerView.setAdapter(personSearchAdapter);

                                            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(Second_Activity.this,R.anim.layout_slide_right);

                                            resultRecyclerView.setLayoutAnimation(controller);
                                            resultRecyclerView.scheduleLayoutAnimation();

                                            Paper.book().write("cache", new Gson().toJson(personResponse));

                                            Paper.book().write("source", "person");
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<PersonResponse> call, @NonNull Throwable t) {

                                    }
                                });
                            }

                        }
                    }
                }
            }

        });

    }

    @Override
    protected void onStop() {
        super.onStop();

        Paper.book().write("position",sourceSpinner.getSelectedIndex());
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Toast.makeText(this, "horizontal", Toast.LENGTH_SHORT).show();
        //    resultRecyclerView = findViewById(R.id.results_recycler_view);
        //    resultRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        }
        else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "vertical", Toast.LENGTH_SHORT).show();
        //    resultRecyclerView = findViewById(R.id.results_recycler_view);
        //    resultRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        }




    }
}
