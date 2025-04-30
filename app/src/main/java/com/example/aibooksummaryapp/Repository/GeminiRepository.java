package com.example.aibooksummaryapp.Repository;

import android.util.Log;

import com.example.aibooksummaryapp.APIModels.GeminiRequest;
import com.example.aibooksummaryapp.APIModels.GeminiRequest.Content;
import com.example.aibooksummaryapp.APIModels.GeminiRequest.Part;
import com.example.aibooksummaryapp.APIModels.GeminiResponse;
import com.example.aibooksummaryapp.API_Interface.GeminiApi;
import com.example.aibooksummaryapp.API_Interface.GeminiClient;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeminiRepository {
    private final GeminiApi geminiApi;
    private final String apiKey;

    public GeminiRepository(String apiKey) {
        this.apiKey = apiKey;
        geminiApi = GeminiClient.getClient().create(GeminiApi.class);
    }

    public void getBookSummary(String bookTitle, OpenAiRepository.SummaryCallback callback) {
        Part part = new Part("Summarize the book: " + bookTitle);
        Content content = new Content(Collections.singletonList(part));
        GeminiRequest request = new GeminiRequest(Collections.singletonList(content));

        geminiApi.generateContent(apiKey, request).enqueue(new Callback<GeminiResponse>() {
            @Override
            public void onResponse(Call<GeminiResponse> call, Response<GeminiResponse> response) {
                if (response.isSuccessful() && response.body() != null &&
                        response.body().getCandidates() != null && !response.body().getCandidates().isEmpty()) {

                    String text = response.body().getCandidates().get(0).getContent().getParts().get(0).getText();
                    callback.onSummaryReceived(text);
                } else {
                    callback.onError("API Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<GeminiResponse> call, Throwable t) {
                callback.onError("Request failed: " + t.getMessage());
            }
        });
    }
}