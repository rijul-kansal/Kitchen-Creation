package com.learning.zomatoclone.Utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Constants {

    const val ID="id"
    const val EMAIL="email"
    const val PASSWORD="password"
    const val SEE_ALL_CAT_OR_CUS="catorcus"
    const val DETAILS_OF_CAT_OR_CUS="detailofcatorcus"
    const val BASE_URL= "https://www.themealdb.com"
    fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    var lis: Array<String> = arrayOf("Do you know: Peanuts are not nuts",
        "Do you know: Honey never spoils","Do you know: Kiwis are native to China","Do you know: The world's largest desert is Antarctica",
        "Do you know: Apples float in water because they're 25% air"
    )


}