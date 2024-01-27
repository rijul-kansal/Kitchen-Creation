package com.learning.zomatoclone.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.learning.zomatoclone.R
import com.learning.zomatoclone.Utils.Constants
import com.learning.zomatoclone.databinding.ActivityDetailOfCatCusBinding

class DetailOfCatCus : AppCompatActivity() {
    lateinit var binding:ActivityDetailOfCatCusBinding
    var type:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityDetailOfCatCusBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        type=intent.getStringExtra(Constants.DETAILS_OF_CAT_OR_CUS).toString()

    }
}