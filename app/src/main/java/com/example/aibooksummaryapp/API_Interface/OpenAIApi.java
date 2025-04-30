package com.example.aibooksummaryapp.API_Interface;

import com.example.aibooksummaryapp.Model.ChatRequest;
import com.example.aibooksummaryapp.Model.ChatResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OpenAIApi {
    @Headers({
            "Content-Type: application/json",
            "Authorization: Bearer YOUR_API_KEY" // replace this at runtime in real apps
    })
    @POST("https://api.openai.com/v1/chat/completions")
    Call<ChatResponse> getChatCompletion(@Body ChatRequest request);
}
