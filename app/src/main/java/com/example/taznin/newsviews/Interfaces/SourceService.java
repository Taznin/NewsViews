package com.example.taznin.newsviews.Interfaces;

import com.example.taznin.newsviews.Model.NewsPaper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SourceService {
    @GET("sources")
    Call<NewsPaper> getSources(
            @Query("apikey") String apikey
    );
}
