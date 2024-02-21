package com.learning.zomatoclone.Fragments

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.learning.zomatoclone.Adapter.FavRecipeAdapter
import com.learning.zomatoclone.Model.FavRecipeModel
import com.learning.zomatoclone.R
import com.learning.zomatoclone.Utils.Constants
import com.learning.zomatoclone.ViewModel.FireStoreStorage
import com.learning.zomatoclone.databinding.FragmentFavBinding


class FavFragment : Fragment() {
    var dialog: Dialog? = null
    lateinit private var binding: FragmentFavBinding
    lateinit var viewModel: FireStoreStorage
    lateinit var list:List<FavRecipeModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[FireStoreStorage::class.java]
        binding = FragmentFavBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        showProgressBar(requireContext())
        viewModel.getFavRecipeDetails()
        viewModel.observergetFavRecipe1().observe(requireActivity(), Observer {
            cancelProgressBar()
            Log.d("rk",it)
            if(it=="item present")
            {
                viewModel.observergetFavRecipe().observe(requireActivity(), Observer {result->
                    Log.d("rk",result.toString())
                    try {
                        displayResult(result as ArrayList<FavRecipeModel>)
                    }catch (e:Exception)
                    {
                        Log.d("rk",e.message.toString())
                    }
                })
            }
        })
        return binding.root
    }
    fun displayResult(lis:ArrayList<FavRecipeModel>)
    {
        binding.recycleFav.layoutManager = LinearLayoutManager(requireContext())
        val ItemAdapter = FavRecipeAdapter(lis,requireContext())
        binding.recycleFav.adapter = ItemAdapter
        ItemAdapter.setOnClickListener(object :
            FavRecipeAdapter.OnClickListener {
            override fun onClick(position: Int, model: FavRecipeModel) {
                Log.d("rk",model.name.toString())
                var intent= Intent(requireActivity(), DishSpecification::class.java)
                intent.putExtra(Constants.DETAILS_OF_CAT_OR_CUS,lis[position].id)
                intent.putExtra(Constants.DETAILS_OF_CAT_OR_CUS_IMAGE,lis[position].imagr)
                intent.putExtra(Constants.DETAILS_OF_CAT_OR_CUS_Name,lis[position].name)
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