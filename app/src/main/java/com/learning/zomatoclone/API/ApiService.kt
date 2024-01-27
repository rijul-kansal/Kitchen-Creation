package com.learning.zomatoclone.API

import com.learning.zomatoclone.Model.Categories.CategoreisModel
import com.learning.zomatoclone.Model.RandomMeal.RandomMealModel
import com.learning.zomatoclone.Model.SingleCatOrCus.SingleCatOrCusModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/api/json/v1/1/filter.php")
    suspend fun getSingleCat(
        @Query("c") list:String,
    ): Response<SingleCatOrCusModel>
    @GET("/api/json/v1/1/filter.php")
    suspend fun getSingleCus(
        @Query("a") list:String,
    ): Response<SingleCatOrCusModel>

    @GET("/api/json/v1/1/random.php")
    suspend fun getRandomMeal():Response<RandomMealModel>
    @GET("/api/json/v1/1/categories.php")
    suspend fun getCategories():Response<CategoreisModel>
}