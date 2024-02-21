package com.learning.zomatoclone.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.learning.zomatoclone.Model.FavRecipeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.http.Url

class FireStoreStorage: ViewModel() {
    val db = Firebase.firestore
    private var addRecipeResult:MutableLiveData<String> = MutableLiveData()
    private var deleteRecipeResult:MutableLiveData<String> = MutableLiveData()
    private var checkMealResult:MutableLiveData<Boolean> = MutableLiveData()
    private var getFavRecipeResult:MutableLiveData<List<FavRecipeModel>>  =  MutableLiveData()
    private var getFavRecipeResult1:MutableLiveData<String>  =  MutableLiveData()
    fun addFavRecipeIntoDB(value:Map<String,String>)
    {
        viewModelScope.launch(Dispatchers.IO) {
            db.collection("FavRecipe").document(AuthenticationClass().getUserId())
                .set(value, SetOptions.merge())
        }
    }
    fun addFavRecipeIntoDB2(value:FavRecipeModel)
    {
        viewModelScope.launch(Dispatchers.IO) {
            db.collection("FavRecipe").document(AuthenticationClass().getUserId())
                .update("recipeTable", FieldValue.arrayUnion(value))
                .addOnSuccessListener { it->
                    addRecipeResult.value="Recipe is added into fav"
                }
                .addOnFailureListener {
                        it->  addRecipeResult.value= it.toString()
                }
        }
    }
    fun deleteFavRecipeIntoDB(value:FavRecipeModel)
    {
        viewModelScope.launch(Dispatchers.IO) {
            db.collection("FavRecipe").document(AuthenticationClass().getUserId())
                .update("recipeTable", FieldValue.arrayRemove(value))
                .addOnSuccessListener { it->
                    deleteRecipeResult.value="Recipe is removed from fav"
                }
                .addOnFailureListener {
                        it->  deleteRecipeResult.value= it.toString()
                }
        }
    }

    fun check_If_MealId_Is_Present(mealId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            db.collection("FavRecipe").document(AuthenticationClass().getUserId())
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val list = document.get("recipeTable") as? List<HashMap<String,String>>
                        Log.d("rk","lis item" +list.toString())
                        if (list != null) {
                            try {
                                val favRecipeList = list.map { map ->
                                    FavRecipeModel(
                                        cat = map["cat"] ,
                                        name = map["name"] ,
                                        imagr = map["image"] ,
                                        id = map["id"] ,
                                        area = map["area"]
                                    )
                                }
                                var isMealIdPresent = false
                                for(i in 0..favRecipeList.size-1)
                                {
                                    if(favRecipeList[i].id==mealId)
                                    {
                                        isMealIdPresent = true
                                        break
                                    }
                                }
                                checkMealResult.value = isMealIdPresent
                            }catch (e:Exception)
                            {
                                Log.d("rk",e.message.toString())
                                Log.d("rk", e.toString())
                            }

                        } else {
                            checkMealResult.value = false
                        }
                    } else {
                        checkMealResult.value = false
                    }
                }
                .addOnFailureListener {
                    checkMealResult.value = false
                }
        }
    }



    fun getFavRecipeDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            db.collection("FavRecipe").document(AuthenticationClass().getUserId())
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val list = document.get("recipeTable") as? ArrayList<HashMap<String,Any>>
                        if(list!=null)
                        {
                            val favRecipeList = list.map { map ->
                                FavRecipeModel(
                                    cat = map["cat"] as? String ,
                                    name = map["name"] as? String ,
                                    imagr = map["imagr"] as? String ,
                                    id = map["id"] as? String ,
                                    area = map["area"] as? String
                                )
                            }
                            getFavRecipeResult1.value="item present"
                            getFavRecipeResult.value = favRecipeList
                        }
                        else
                        {
                            getFavRecipeResult1.value="no fav item"
                        }
                    } else {
                        getFavRecipeResult1.value="no fav item"
                    }
                }
                .addOnFailureListener {
                    getFavRecipeResult1.value= it.toString()
                }
        }
    }
    fun observerAddRecipeData2():LiveData<String> = addRecipeResult
    fun observerGetMeal():LiveData<Boolean> = checkMealResult
    fun observerDeleteRecipeData():LiveData<String> = deleteRecipeResult

    fun observergetFavRecipe():LiveData<List<FavRecipeModel>> = getFavRecipeResult
    fun observergetFavRecipe1():LiveData<String> = getFavRecipeResult1
}
