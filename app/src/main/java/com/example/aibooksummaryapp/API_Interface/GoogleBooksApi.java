package com.example.aibooksummaryapp.API_Interface;

import com.example.aibooksummaryapp.Model.BookResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleBooksApi {
    @GET("volumes")
    Call<BookResponse> getBooks(@Query("q") String query);
}