package com.example.aibooksummaryapp.Repository;

import android.util.Log;

import com.example.aibooksummaryapp.API_Interface.OpenAIApi;
import com.example.aibooksummaryapp.API_Interface.OpenAiClient;
import com.example.aibooksummaryapp.Model.ChatRequest;
import com.example.aibooksummaryapp.Model.ChatRequest.Message;
import com.example.aibooksummaryapp.Model.ChatResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OpenAiRepository {

    private final OpenAIApi openAIApi;

    public OpenAiRepository(String apiKey) {
        Retrofit retrofit = OpenAiClient.getClient(apiKey);
        openAIApi = retrofit.create(OpenAIApi.class);
    }

    public void getBookSummary(String bookTitle, SummaryCallback callback) {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("system", "You are a helpful assistant who summarizes books."));
        messages.add(new Message("user", "Give a good summary of the book '" + bookTitle +
                "', including what it’s about, the basic story, and main characters."));

        ChatRequest request = new ChatRequest("gpt-3.5-turbo", messages);

        Call<ChatResponse> call = openAIApi.getChatCompletion(request);
        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String reply = response.body().getChoices().get(0).getMessage().getContent();
                    callback.onSummaryReceived(reply);
                } else {
                    Log.e("OpenAiRepository", "API error: " + response.code());
                    callback.onError("API Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                Log.e("OpenAiRepository", "Failure: " + t.getMessage());
                callback.onError(t.getMessage());
            }
        });
    }

    public interface SummaryCallback {
        void onSummaryReceived(String summary);
        void onError(String error);
    }
}
