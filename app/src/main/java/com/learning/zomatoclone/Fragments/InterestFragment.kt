package com.learning.zomatoclone.Fragments

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.learning.zomatoclone.Activity.DishSpecification
import com.learning.zomatoclone.Adapter.InterestFragmentAdapter
import com.learning.zomatoclone.Model.InterestFragModel
import com.learning.zomatoclone.Model.SingleCatOrCus.Meal
import com.learning.zomatoclone.R
import com.learning.zomatoclone.Utils.Constants
import com.learning.zomatoclone.ViewModel.ApiModel
import com.learning.zomatoclone.ViewModel.FireStoreStorage
import com.learning.zomatoclone.databinding.FragmentInterestBinding

class InterestFragment : Fragment() {
    var dialog:Dialog?=null
    lateinit var binding:FragmentInterestBinding
     lateinit var viewModel:FireStoreStorage
     lateinit var viewModel1:ApiModel
     var itemList:ArrayList<Meal> = ArrayList()
    var arrlis:ArrayList<InterestFragModel> = ArrayList()
    var set:HashSet<InterestFragModel> = HashSet()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= FragmentInterestBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        viewModel= ViewModelProvider(requireActivity())[FireStoreStorage::class.java]
        viewModel1= ViewModelProvider(requireActivity())[ApiModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        showProgressBar(requireContext())
        viewModel.getUserProfileDetails()
        viewModel.observeGetUserProfileDetails().observe(viewLifecycleOwner, Observer { userProfile ->
            Log.d("rk", userProfile.interests.toString())
            if (userProfile.interests != null) {
                val catCount = userProfile.interests!!.size
                var receivedCatCount = 0
                for (item in userProfile.interests!!) {
                    viewModel1.getSingleCat(requireActivity(), item.name!!)
                    viewModel1.observeSingleCatOrCus().observe(viewLifecycleOwner, Observer { response ->
                        if (response.isSuccessful) {
                            response.body()?.meals?.let { meals ->
                                itemList.addAll(meals.filterNotNull())
                            }
                        }
                        receivedCatCount++
                        if (receivedCatCount == catCount) {

                            Log.d("rk", "list: $itemList")
                        }
                    })
                }
            } else {
                // Handle case where interests are null
            }
        })
        Handler().postDelayed({
            Log.d("rk", "list: $itemList")
            displayResult(itemList)
        },2000)

        return binding.root
    }
    fun displayResult(lis:ArrayList<Meal>)
    {
        arrlis.clear()
        set.clear()
        for(item in lis)
        {
            var model=InterestFragModel(item.strMeal,item.strMealThumb,item.idMeal)
            if(set.contains(model) == false)
            {
                arrlis.add(model)
                set.add(model)
            }
        }
        Log.d("rk",arrlis.size.toString())
        cancelProgressBar()
        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
        val ItemAdapter = InterestFragmentAdapter(arrlis,requireContext())
        binding.recycleView.adapter = ItemAdapter
        ItemAdapter.setOnClickListener(object :
            InterestFragmentAdapter.OnClickListener {
            override fun onClick(position: Int, model: InterestFragModel) {
                var intent= Intent(requireContext(), DishSpecification::class.java)
                intent.putExtra(Constants.DETAILS_OF_CAT_OR_CUS,lis[position].idMeal)
                intent.putExtra(Constants.DETAILS_OF_CAT_OR_CUS_IMAGE,lis[position].strMealThumb)
                intent.putExtra(Constants.DETAILS_OF_CAT_OR_CUS_Name,lis[position].strMeal)
                startActivity(intent)
            }
        })
    }
    fun showProgressBar(context: Context) {
        dialog = Dialog(context)
        dialog!!.setContentView(R.layout.progress_bar)
        dialog!!.window?.apply {
            setBackgroundDrawableResource(android.R.color.transparent) // Optional: Set background to transparent
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        dialog!!.show()
    }
    fun cancelProgressBar()
    {
        if(dialog!=null)
        {
            dialog!!.dismiss()
            dialog=null
        }
    }
}