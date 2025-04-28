package com.example.aibooksummaryapp.Repository;

import android.util.Log;

import com.example.aibooksummaryapp.API_Interface.GoogleBookApiClient;
import com.example.aibooksummaryapp.API_Interface.GoogleBooksApi;
import com.example.aibooksummaryapp.Model.BookResponse;
import com.example.aibooksummaryapp.Model.BookSummary;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookRepository {
    public void getRecommendedBooks(String query, BookListCallback callback) {
        GoogleBooksApi api = GoogleBookApiClient.getClient().create(GoogleBooksApi.class);
        Call<BookResponse> call = api.getBooks(query);
        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BookResponse bookResponse = response.body();
                    Log.d("BookRepository", "Received data: " + new Gson().toJson(bookResponse));
                    callback.onBooksFetched(response.body().getItems());
                }else{
                    Log.e("BookRepository", "Response failed or empty: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Log.e("BookRepository", "API call failed: " + t.getMessage());
                callback.onError(t.getMessage());
            }
        });
    }

    public interface BookListCallback {
        void onBooksFetched(List<BookSummary> books);
        void onError(String error);
    }
}