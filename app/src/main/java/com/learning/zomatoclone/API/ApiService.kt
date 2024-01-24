package com.learning.zomatoclone.API

import com.learning.zomatoclone.Model.Categories.CategoreisModel
import com.learning.zomatoclone.Model.RandomMeal.RandomMealModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
//    @GET("/api/json/v1/1/list.php")
//    suspend fun getCategories(
//        @Query("c") list:String,
//    ): Response<Category>

    @GET("/api/json/v1/1/random.php")
    suspend fun getRandomMeal():Response<RandomMealModel>
    @GET("/api/json/v1/1/categories.php")
    suspend fun getCategories():Response<CategoreisModel>
}