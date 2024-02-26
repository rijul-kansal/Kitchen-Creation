package com.learning.zomatoclone.Activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.learning.zomatoclone.R
import com.learning.zomatoclone.Utils.BaseActivity
import com.learning.zomatoclone.Utils.Constants
import com.learning.zomatoclone.ViewModel.FireStoreStorage
import com.learning.zomatoclone.ViewModel.Storage
import com.learning.zomatoclone.databinding.ActivityProfileBinding


class ProfileActivity : BaseActivity() {
    lateinit var binding:ActivityProfileBinding
    lateinit var viewModel: FireStoreStorage
    lateinit var viewModel1: Storage
    lateinit var name:String
    lateinit var email:String
    lateinit var mobileNumber:String
    lateinit var userProfileUrl:String
    var fileUri:Uri?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showProgressBar(this@ProfileActivity)
        viewModel=ViewModelProvider(this)[FireStoreStorage::class.java]
        viewModel1=ViewModelProvider(this)[Storage::class.java]
        try {
            viewModel.getUserProfileDetails()
            viewModel.observeGetUserProfileDetails().observe(this, Observer {
                cancelProgressBar()
                Log.d("rk",it.toString())
                if(it.name!=null) {
                    name = it.name.toString()
                    binding.etName.setText(name)
                }
                else{
                    name=""
                }
                if(it.email!=null) {
                    email = it.email.toString()
                    binding.etEmail.setText(email)
                }
                else{
                    email=""
                }
                if(it.mobileNumber!=null) {
                    mobileNumber = it.mobileNumber.toString()
                    binding.etMobileNo.setText(mobileNumber)
                }
                else{
                    mobileNumber=""
                }
                if(it.image!=null) {
                    userProfileUrl = it.image.toString()
                    Glide
                        .with(this)
                        .load(userProfileUrl)
                        .centerCrop()
                        .placeholder(R.drawable.img)
                        .into(binding.profileImage)
                }
                else{
                    userProfileUrl=""
                }
            })
        }catch (e:Exception)
        {
            Log.d("rk",e.message.toString())
        }

        binding.profileImage.setOnClickListener {
            if(checkPermission())
            {
                imageChooser()
            }
            else
            {
                requestPermission()
            }
        }

        binding.updateBtn.setOnClickListener {
            val newName=binding.etName.text.toString()
            val newEmail=binding.etEmail.text.toString()
            val newMobileNumber=binding.etMobileNo.text.toString()
            if(fileUri!=null)
            {
                viewModel1.uploadImage(fileUri!!)
            }
            if(newName!=name)
            {
                 var map= HashMap<String,String>();
                 map["name"]=newName
                 viewModel.updateUserPersonalDataIntoDB(map)
            }
            if(newEmail!=email)
            {
                 var map= HashMap<String,String>();
                 map["email"]=newEmail
                 viewModel.updateUserPersonalDataIntoDB(map)
            }
            if(newMobileNumber!=mobileNumber)
            {
                 showProgressBar(this)
                 var map= HashMap<String,String>();
                 map["mobileNumber"]=newMobileNumber
                 viewModel.updateUserPersonalDataIntoDB(map)
                 cancelProgressBar()
            }
            Toast(this,"Updated Successfully")
        }
    }


    // check if permission is granted code
    private fun checkPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_MEDIA_IMAGES
        ) == PackageManager.PERMISSION_GRANTED)
    }
    // else req permission
    private fun requestPermission() {
        ActivityCompat.requestPermissions(this@ProfileActivity ,permissions(), 1)

    }
    val storagePermissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    val storagePermissions33 = arrayOf(
        Manifest.permission.READ_MEDIA_IMAGES,
    )
    fun permissions(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            storagePermissions33
        } else {
            storagePermissions
        }
    }

    fun imageChooser() {
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(i, "Select Picture"), Constants.SELECT_PICTURE)
    }
     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.SELECT_PICTURE) {
                val selectedImageUri = data?.data
                fileUri=data?.data
                Log.d("rk",selectedImageUri.toString())
                if (selectedImageUri !=null) {
                    // update the preview image in the layout
                    binding.profileImage.setImageURI(selectedImageUri)
                }
            }
        }
    }
}