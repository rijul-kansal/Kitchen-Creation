package com.learning.zomatoclone.ViewModel

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.zomatoclone.API.ApiService
import com.learning.zomatoclone.Fragments.HomeFragment
import com.learning.zomatoclone.Model.Categories.CategoreisModel
import com.learning.zomatoclone.Model.RandomMeal.RandomMealModel
import com.learning.zomatoclone.Utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class ApiModel:ViewModel() {
    private var randomMealResult:MutableLiveData<Response<RandomMealModel>> = MutableLiveData()
    private var categoriesResult: MutableLiveData<Response<CategoreisModel>> = MutableLiveData()
    fun getRandomMeal(activity: Activity) {
        if (checkForInternet(activity)) {
            val matchApi = Constants.getInstance().create(ApiService::class.java)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val result = matchApi.getRandomMeal()
                    withContext(Dispatchers.Main)
                    {
                        randomMealResult.value=result
                    }
                }catch (e: Exception) {
                    Log.e("rijul", "Exception: ${e.message}")
                }
            }
        }
        else{
            HomeFragment().updateUI("Internet is not working")
        }
    }
    fun getCategoriesMeal(activity: Activity) {
        if (checkForInternet(activity)) {
            val matchApi = Constants.getInstance().create(ApiService::class.java)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val result = matchApi.getCategories()
                    withContext(Dispatchers.Main)
                    {
                        categoriesResult.value=result
                    }
                }catch (e: Exception) {
                    Log.e("rijul", "Exception: ${e.message}")
                }
            }
        }
        else{
            HomeFragment().updateUI("Internet is not working")
        }
    }
    fun observeGetRandomMeal():LiveData<Response<RandomMealModel>> = randomMealResult
    fun observeGetCategoriesMeal():LiveData<Response<CategoreisModel>> = categoriesResult
    private fun checkForInternet(context: Activity): Boolean
    {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}