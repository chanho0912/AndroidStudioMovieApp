package edu.skku.map.a2016310526_personalproj.interfaces;
import edu.skku.map.a2016310526_personalproj.Model.MovieResponse;
import edu.skku.map.a2016310526_personalproj.Model.PersonResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {


    @GET("search/movie")
    Call<MovieResponse> getMovieByQuery(@Query("api_key") String api_key, @Query("query") String query);

    @GET("search/person")
    Call<PersonResponse> getPersonsByQuery(@Query("api_key") String api_key, @Query("query") String query);

}