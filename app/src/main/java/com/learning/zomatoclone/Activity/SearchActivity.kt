package com.learning.zomatoclone.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.learning.zomatoclone.Adapter.SearchByLetterAdapter
import com.learning.zomatoclone.Model.Search.Meal
import com.learning.zomatoclone.Utils.Constants
import com.learning.zomatoclone.ViewModel.ApiModel
import com.learning.zomatoclone.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    lateinit var binding:ActivitySearchBinding
    lateinit var viewModel:ApiModel
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivitySearchBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel= ViewModelProvider(this)[ApiModel::class.java]
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.searchItemByLetter(this@SearchActivity,query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    viewModel.searchItemByLetter(this@SearchActivity,newText)
                }
                return true
            }
        })

        viewModel.observeSearchByLetter().observe(this, Observer { result->
            if(result.isSuccessful)
            {
                var lis=ArrayList<Meal>()
                for(i in 0..result.body()!!.meals!!.size-1)
                {
                    result.body()!!.meals?.get(i)?.let { lis.add(it) }
                }
                populateData(lis)
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
    }


    private fun populateData(result: List<Meal>) {
        binding.recycleView.layoutManager = LinearLayoutManager(this@SearchActivity)
        val ItemAdapter = SearchByLetterAdapter(result,this@SearchActivity)
        binding.recycleView.adapter = ItemAdapter
        ItemAdapter.setOnClickListener(object :
            SearchByLetterAdapter.OnClickListener {
            override fun onClick(position: Int, model: Meal) {
                var intent= Intent(this@SearchActivity,DishSpecification::class.java)
                intent.putExtra(Constants.DETAILS_OF_CAT_OR_CUS,model.idMeal.toString())
                startActivity(intent)
            }
        })
    }
}