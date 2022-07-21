package com.stackexchangelite.app.data.network

import com.stackexchangelite.app.data.model.Welcome10
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface MainApi {

//    https://api.stackexchange.com/2.3/questions?page=1&order=desc&sort=activity&site=stackoverflow
    @GET("questions")
    suspend fun getQuestions(@Query("page") id: Int, @Query("order") order: String, @Query("sort") sort: String, @Query("site") site: String): Response<Welcome10>
}