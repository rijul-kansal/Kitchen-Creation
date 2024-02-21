package com.learning.zomatoclone.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.learning.zomatoclone.Adapter.GridViewAdapterDishSpecification
import com.learning.zomatoclone.Model.Dish.DishModel
import com.learning.zomatoclone.Model.FavRecipeModel
import com.learning.zomatoclone.R
import com.learning.zomatoclone.Utils.BaseActivity
import com.learning.zomatoclone.Utils.Constants
import com.learning.zomatoclone.ViewModel.ApiModel
import com.learning.zomatoclone.ViewModel.FireStoreStorage
import com.learning.zomatoclone.databinding.ActivityDishSpecificationBinding
import retrofit2.Response

class DishSpecification : BaseActivity() {
    lateinit var mealId:String
    lateinit var binding:ActivityDishSpecificationBinding
    lateinit var viewModel:ApiModel
    lateinit var viewModel2:FireStoreStorage
    lateinit var mealUrl:String
    lateinit var mealName:String
    lateinit var mealCat:String
    lateinit var mealArea:String
    var flag=0
    var flag1=0
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityDishSpecificationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel= ViewModelProvider(this)[ApiModel::class.java]
        viewModel2= ViewModelProvider(this)[FireStoreStorage::class.java]
        mealId=intent.getStringExtra(Constants.DETAILS_OF_CAT_OR_CUS).toString()
        mealUrl=intent.getStringExtra(Constants.DETAILS_OF_CAT_OR_CUS_IMAGE).toString()
        mealName=intent.getStringExtra(Constants.DETAILS_OF_CAT_OR_CUS_Name).toString()
        Log.d("rk",mealId)
        Log.d("rk",mealUrl)
        Log.d("rk",mealName)
        showProgressBar(this@DishSpecification)
        viewModel.getDish(this@DishSpecification,mealId)
        viewModel.observeGetDish().observe(this, Observer { result->
            cancelProgressBar()
            if(result.isSuccessful)
            {
                populateData(result)
            }
            else {
                val errorBody = result.errorBody()?.string()
                if (errorBody != null) {
                    Log.d("rk", errorBody)
                } else {
                    Log.d("rk", "Unknown error")
                }
            }
        })
        viewModel2.check_If_MealId_Is_Present(mealId)
        viewModel2.observerGetMeal().observe(this, Observer {
                result->
            Log.d("rk",result.toString())
            if(result==true)
            {
                flag1=1
                binding.idFABHome.setImageResource(R.drawable.baseline_favorite_24)
            }
            else
            {
                flag1=0
                binding.idFABHome.setImageResource(R.drawable.empty_heart)
            }
        })

        binding.sliderUpDown.setOnClickListener {
            if(flag==0)
            {
                binding.RecycleView1.visibility= View.VISIBLE
                binding.intsicon.setImageResource(R.drawable.up)
                flag=1
            }
            else
            {
                binding.RecycleView1.visibility= View.GONE
                binding.intsicon.setImageResource(R.drawable.down)
                flag=0
            }
        }
        try{
            binding.idFABHome.setOnClickListener {
                if(flag1==0)
                    viewModel2.addFavRecipeIntoDB2(FavRecipeModel(mealCat,mealName,mealUrl,mealId,mealArea))
                else
                    viewModel2.deleteFavRecipeIntoDB(FavRecipeModel(mealCat,mealName,mealUrl,mealId,mealArea))
            }
            viewModel2.observerAddRecipeData2().observe(this, Observer {
                    result->
                if(result=="Recipe is added into fav")
                {
                    flag1=1
                    binding.idFABHome.setImageResource(R.drawable.baseline_favorite_24)
                }
                Toast(this,result)
                Log.d("rk",result)
            })
            viewModel2.observerDeleteRecipeData().observe(this, Observer {
                    result->
                if(result=="Recipe is removed from fav")
                {
                    flag1=0;
                    binding.idFABHome.setImageResource(R.drawable.empty_heart)
                }
                Toast(this,result)
                Log.d("rk",result)
            })
        }catch (e:Exception)
        {
            Log.d("rk",e.message.toString())
        }

    }


    private fun populateData(result: Response<DishModel>) {
        mealCat= result.body()!!.meals?.get(0)!!.strCategory.toString()
        mealArea= result.body()!!.meals?.get(0)!!.strArea.toString()
        binding.typeTv.text= result.body()!!.meals?.get(0)!!.strCategory
        binding.nameOfDish.text= result.body()!!.meals?.get(0)!!.strMeal
        Glide
            .with(this@DishSpecification)
            .load(result.body()!!.meals?.get(0)?.strMealThumb)
            .centerCrop()
            .placeholder(R.drawable.img)
            .into(binding.dishImage)
        val ins: List<String> = result.body()!!.meals?.get(0)!!.strInstructions!!.split("\r\n")
        Log.d("rk",ins.toString())
        binding.RecycleView1.layoutManager = LinearLayoutManager(this@DishSpecification)
        val ItemAdapter = GridViewAdapterDishSpecification(ins,this@DishSpecification)
        binding.RecycleView1.adapter = ItemAdapter
    }

}