package com.example.aibooksummaryapp.API_Interface;

import com.example.aibooksummaryapp.APIModels.GeminiRequest;
import com.example.aibooksummaryapp.APIModels.GeminiResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GeminiApi {
    @Headers("Content-Type: application/json")
    @POST("v1beta/models/gemini-2.0-flash:generateContent")
    Call<GeminiResponse> generateContent(@Query("key") String apiKey, @Body GeminiRequest request);
}