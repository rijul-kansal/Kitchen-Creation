package com.learning.zomatoclone.Utils

import com.learning.zomatoclone.Model.Cuisine.CusShown
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

object Constants {

    const val EMAIL="email"
    const val PASSWORD="password"
    const val BASE_URL= "https://www.themealdb.com"
    fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}