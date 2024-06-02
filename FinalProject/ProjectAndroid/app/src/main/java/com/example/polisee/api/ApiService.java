package com.example.polisee.api;

import com.example.polisee.Politician;
import com.example.polisee.response.DataResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("politicians")
    Call<List<Politician>> getData();

    @GET("politicians/{id}")
    Call<DataResponse> getDetail(@Path("id") String id);

    @GET("politicians")
    Call<List<Politician>> searchPoliticians(@Query("search") String query);
}