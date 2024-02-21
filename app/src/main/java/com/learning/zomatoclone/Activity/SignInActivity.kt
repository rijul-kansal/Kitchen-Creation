package com.learning.zomatoclone.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.learning.zomatoclone.Utils.BaseActivity
import com.learning.zomatoclone.Utils.Constants
import com.learning.zomatoclone.ViewModel.AuthenticationClass
import com.learning.zomatoclone.databinding.ActivitySignInBinding
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import com.learning.zomatoclone.ViewModel.FireStoreStorage

class SignInActivity : BaseActivity() {
    lateinit var binding:ActivitySignInBinding

    lateinit var viewModel: AuthenticationClass
    lateinit var viewModel1: FireStoreStorage
    lateinit var email:String
    lateinit var password:String
    lateinit var user:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivitySignInBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        charByCharDisplay(binding.signInTv.text.toString(),binding.signInTv)
        viewModel= ViewModelProvider(this)[AuthenticationClass::class.java]
        viewModel1= ViewModelProvider(this)[FireStoreStorage::class.java]
        try {
            email=""
            password=""
            if(intent.hasExtra(Constants.PASSWORD))
            password=intent.getStringExtra(Constants.PASSWORD).toString()
            if(intent.hasExtra(Constants.EMAIL))
            email=intent.getStringExtra(Constants.EMAIL).toString()

            binding.etEmail.setText(email)
            binding.etPassword.setText(password)

            // current user Info
            user=FirebaseAuth.getInstance()


            // sign In
            binding.SignInBtn.setOnClickListener{
                email=binding.etEmail.text.toString()
                password=binding.etPassword.text.toString()
                if(valid())
                {
                    if(user.currentUser==null)
                    {
                        showProgressBar(this)
                        viewModel.SignIn(this@SignInActivity, email, password)
                    }
                    else {
                        if (user.currentUser!!.isEmailVerified) {
                            showProgressBar(this)
                            viewModel.SignIn(this@SignInActivity, email, password)
                        } else {
                            user.currentUser?.reload()?.addOnCompleteListener { reloadTask ->
                                if (reloadTask.isSuccessful) {
                                    user.currentUser?.getIdToken(true)
                                        ?.addOnCompleteListener { tokenTask ->
                                            if (tokenTask.isSuccessful) {
                                                if (user.currentUser!!.isEmailVerified) {
                                                    showProgressBar(this)
                                                    viewModel.SignIn(
                                                        this@SignInActivity,
                                                        email,
                                                        password
                                                    )
                                                } else {
                                                    viewModel.sendVerificationCode()
                                                    Toast(this, "Please verify your email first")
                                                }
                                            } else {
                                                Log.e(
                                                    "rk",
                                                    "Error refreshing token: ${tokenTask.exception?.message}"
                                                )
                                            }
                                        }
                                } else {
                                    Log.e(
                                        "rk",
                                        "Error reloading user: ${reloadTask.exception?.message}"
                                    )
                                }
                            }
                        }
                    }
                }
            }


            // result of sign In if successful the call sign in
            viewModel.observerTaskResultLogin().observe(this, Observer { task->
                cancelProgressBar()
                if(task.isSuccessful)
                {
                    Toast(this,"user Login In successfully")
                    viewModel1.addFavRecipeIntoDB(mapOf("Hi" to "User"))
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }
                else
                {
                    Toast(this,task.exception!!.message.toString())
                }
            })

        }catch (e:Exception)
        {
            Log.e("rk",e.message.toString())
        }
    }
    fun valid():Boolean
    {
        if(email.length>0 && password.length>0)
        {
            return true
        }
        else if(email.length==0)
        {
            Toast(this,"Please Enter your email")
        }
        else if(password.length==0)
        {
            Toast(this,"Please Enter your password")
        }
        else
        {
            Toast(this,"Please Enter your name")
        }
        return false
    }

    fun charByCharDisplay(textToDisplay:String,textView: TextView) {
        val handler = Handler(Looper.getMainLooper())
        var currentIndex=0
        handler.post(object : Runnable {
            override fun run() {
                if (currentIndex < textToDisplay.length) {
                    textView.text = textToDisplay.substring(0, currentIndex + 1)
                    currentIndex++
                    handler.postDelayed(this, 300)
                }
            }
        })
    }
}