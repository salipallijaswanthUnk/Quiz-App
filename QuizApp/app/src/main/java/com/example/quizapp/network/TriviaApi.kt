package com.example.quizapp.network

import com.example.quizapp.model.CategoryResponse
import com.example.quizapp.model.QuestionResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface TriviaApi {
    @GET("api_category.php") suspend fun categories(): CategoryResponse
    @GET("api.php") suspend fun questions(@Query("amount") amount: Int = 10, @Query("category") category: Int, @Query("difficulty") difficulty: String, @Query("type") type: String = "multiple"): QuestionResponse
    companion object {
        val service: TriviaApi by lazy { Retrofit.Builder().baseUrl("https://opentdb.com/").addConverterFactory(GsonConverterFactory.create()).build().create(TriviaApi::class.java) }
    }
}
