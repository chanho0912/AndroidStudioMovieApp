package edu.skku.map.a2016310526_personalproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import edu.skku.map.a2016310526_personalproj.Youtube.Youtube;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Third_Activity extends AppCompatActivity {

    ArrayList<Youtube> youtubeList;
/*
    private YouTubePlayerView youTubePlayerView;
    private String trailer01;
    private String m_id;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_);

        Intent intent = getIntent();


        String title = intent.getStringExtra("title");
        String original_title = intent.getStringExtra("original_title");
        String poster_path = intent.getStringExtra("poster_path");
        String overview = intent.getStringExtra("overview");
        String release_date = intent.getStringExtra("release_date");

        TextView textView_title = findViewById(R.id.tv_title);
        textView_title.setText(title);
        TextView textView_original_title = findViewById(R.id.tv_original_title);
        textView_original_title.setText(original_title);
        ImageView imageView_poster = findViewById(R.id.iv_poster);

        Glide.with(this)
                .load(poster_path)
                .centerCrop()
                .crossFade()
                .into(imageView_poster);

        TextView textView_overview = findViewById(R.id.tv_overview);
        textView_overview.setText(overview);
        TextView textView_release_date = findViewById(R.id.tv_release_date);
        textView_release_date.setText(release_date);


    }
}
