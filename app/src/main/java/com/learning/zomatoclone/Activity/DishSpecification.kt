package com.learning.zomatoclone.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.learning.zomatoclone.Adapter.GridViewAdapterDishSpecification
import com.learning.zomatoclone.Model.Dish.DishModel
import com.learning.zomatoclone.R
import com.learning.zomatoclone.Utils.BaseActivity
import com.learning.zomatoclone.Utils.Constants
import com.learning.zomatoclone.ViewModel.ApiModel
import com.learning.zomatoclone.databinding.ActivityDishSpecificationBinding
import retrofit2.Response

class DishSpecification : BaseActivity() {
    lateinit var mealId:String
    lateinit var binding:ActivityDishSpecificationBinding
    lateinit var viewModel:ApiModel
    lateinit var  arrayAdapter: ArrayAdapter<String>
    var flag=0
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityDishSpecificationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel= ViewModelProvider(this)[ApiModel::class.java]
        mealId=intent.getStringExtra(Constants.DETAILS_OF_CAT_OR_CUS).toString()
        Log.d("rk",mealId)
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
    }

    private fun populateData(result: Response<DishModel>) {
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