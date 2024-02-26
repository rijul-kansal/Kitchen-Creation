package com.learning.zomatoclone.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.AdapterView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.learning.zomatoclone.Adapter.GridViewAdapterInterest
import com.learning.zomatoclone.Model.InterestModel
import com.learning.zomatoclone.Utils.BaseActivity
import com.learning.zomatoclone.Utils.Constants
import com.learning.zomatoclone.ViewModel.ApiModel
import com.learning.zomatoclone.ViewModel.FireStoreStorage
import com.learning.zomatoclone.databinding.ActivityIntreastBinding

class InterestActivity : BaseActivity() {
    private lateinit var viewModel:ApiModel
    private lateinit var viewModel1:FireStoreStorage
    private lateinit var binding:ActivityIntreastBinding
    var list:ArrayList<InterestModel> = ArrayList()
    var set:HashSet<String> = HashSet()
    var selectedList:ArrayList<InterestModel> = ArrayList()
    lateinit var  interestValue:String
    lateinit var mainAdapter :GridViewAdapterInterest
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityIntreastBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel=ViewModelProvider(this)[ApiModel::class.java]
        viewModel1=ViewModelProvider(this)[FireStoreStorage::class.java]
        interestValue=""
        if(intent.hasExtra(Constants.Interests))
        {
            interestValue= intent.getStringExtra(Constants.Interests).toString()
        }
        showProgressBar(this)
        viewModel1.getUserProfileDetails()
        viewModel.getCategoriesMeal(this@InterestActivity)
        viewModel.observeGetCategoriesMeal().observe(this, Observer {
            for(i in 0..it.body()!!.categories!!.size-1)
            {
                list.add(InterestModel(i.toString(),it.body()!!.categories?.get(i)!!.strCategory.toString()))
            }
            Log.d("rk",list.toString())
            displayResult(list)
            if(interestValue=="Yes")
            {
                binding.nextBtn.text= "Update"
                Log.d("rk","Yes")
                viewModel1.observeGetUserProfileDetails().observe(this, Observer {
                    Log.d("rk",it.toString())
                    if(it.interests!=null)
                    {
                        for(i in 0..it.interests!!.size-1)
                        {
                            selectedList.add(it.interests!![i])
                            it.interests!![i].id?.let { it1 -> set.add(it1) }
                            Log.d("rk",it.interests!![i].id.toString())
                            it.interests!![i].id?.let {it1 -> mainAdapter.toggleSelection(it1.toInt()) }
                        }
                    }
                })
            }
            else
            {
                viewModel1.observeGetUserProfileDetails().observe(this, Observer {
                    Log.d("rk",it.toString())
                    if(it.interests!=null)
                    {
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                    }
                })
            }
        })

        binding.nextBtn.setOnClickListener {
            val map = HashMap<String,ArrayList<InterestModel>>()
            map["interests"] = selectedList
            viewModel1.updateUserPersonalDataIntoDB1(map)
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        charByCharDisplay(binding.textView.text.toString(),binding.textView)

    }
    fun charByCharDisplay(textToDisplay:String,textView: TextView) {
        val handler = Handler(Looper.getMainLooper())
        var currentIndex=0
        handler.post(object : Runnable {
            override fun run() {
                if (currentIndex < textToDisplay.length) {
                    textView.text = textToDisplay.substring(0, currentIndex + 1)
                    currentIndex++
                    handler.postDelayed(this, 100)
                }
            }
        })
    }
    fun displayResult(lis:ArrayList<InterestModel>)
    {
        cancelProgressBar()
        mainAdapter = GridViewAdapterInterest(this, lis)
        binding.gridView.adapter = mainAdapter
        binding.gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            if(set.contains(list[position].id) == true)
            {
                mainAdapter.toggleSelection(position)
                set.remove(list[position].id)
                selectedList.remove(list[position])
            }
            else
            {
                mainAdapter.toggleSelection(position)
                list[position].id?.let { set.add(it) }
                selectedList.add(list[position])
            }
        }
    }
}