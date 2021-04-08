package edu.skku.map.a2016310526_personalproj.Client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private  static final String baseUri = "https://api.themoviedb.org/3/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(baseUri).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

}
