package com.example.aibooksummaryapp.Repository;

import com.example.aibooksummaryapp.API_Interface.GoogleBooksApi;
import com.example.aibooksummaryapp.Model.BookResponse;
import com.example.aibooksummaryapp.Model.BookSummary;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookRepository {
    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    public void getRecommendedBooks(String query, BookListCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GoogleBooksApi api = retrofit.create(GoogleBooksApi.class);
        Call<BookResponse> call = api.getBooks(query);
        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onBooksFetched(response.body().getItems());
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public interface BookListCallback {
        void onBooksFetched(List<BookSummary> books);
        void onError(String error);
    }
}