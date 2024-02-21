package com.learning.zomatoclone.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.learning.zomatoclone.Adapter.GridViewAdapter2
import com.learning.zomatoclone.Model.SingleCatOrCus.Meal
import com.learning.zomatoclone.Utils.BaseActivity
import com.learning.zomatoclone.Utils.Constants
import com.learning.zomatoclone.ViewModel.ApiModel
import com.learning.zomatoclone.databinding.ActivityDetailOfCatCusBinding

class DetailOfCatCus : BaseActivity() {
    lateinit var binding:ActivityDetailOfCatCusBinding
    var type:String=""
    lateinit var viewModel: ApiModel
    var arrlis =ArrayList<String>()
      var lis:ArrayList<Meal> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityDetailOfCatCusBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel= ViewModelProvider(this)[ApiModel::class.java]
        type=intent.getStringExtra(Constants.DETAILS_OF_CAT_OR_CUS).toString()
        Log.d("rk",type)
        binding.typeTv.text=type
        populateData()
        showProgressBar(this@DetailOfCatCus)
        if(check(type))
        {
            viewModel.getSingleCat(this@DetailOfCatCus,type)
        }
        else
        {
            viewModel.getSingleCus(this@DetailOfCatCus,type)
        }

        viewModel.observeSingleCatOrCus().observe(this@DetailOfCatCus, Observer { result->
                if(result.isSuccessful)
                {
                    for (i in 0..result.body()!!.meals!!.size-1)
                    {
                        result.body()!!.meals?.get(i)?.let { lis.add(it) }
                    }
                    displayResult(lis)
                }
        })
    }
    fun displayResult(lis:ArrayList<Meal>)
    {
        cancelProgressBar()
        val mainAdapter = GridViewAdapter2(this@DetailOfCatCus, lis)
        binding.gridView.adapter = mainAdapter
        binding.gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            var intent= Intent(this,DishSpecification::class.java)
            intent.putExtra(Constants.DETAILS_OF_CAT_OR_CUS,lis[position].idMeal)
            intent.putExtra(Constants.DETAILS_OF_CAT_OR_CUS_IMAGE,lis[position].strMealThumb)
            intent.putExtra(Constants.DETAILS_OF_CAT_OR_CUS_Name,lis[position].strMeal)
            startActivity(intent)
        }
    }
    fun populateData()
    {
        arrlis.add("Beef")
        arrlis.add("BreakFast")
        arrlis.add("Chicken")
        arrlis.add("Dessert")
        arrlis.add("Goat")
        arrlis.add("Lamb")
        arrlis.add("Miscellaneous")
        arrlis.add("Pasta")
        arrlis.add("Pork")
        arrlis.add("Seafood")
        arrlis.add("Side")
        arrlis.add("Starter")
        arrlis.add("Vegan")
        arrlis.add("Vegetarian")
    }
    fun check(type:String):Boolean{
        for(i in 0..arrlis.size-1)
        {
            if(type==arrlis[i]) return true
        }
        return false
    }
}