package com.learning.zomatoclone.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.learning.zomatoclone.Activity.MainActivity
import com.learning.zomatoclone.Activity.SignInActivity
import com.learning.zomatoclone.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    lateinit var binding:ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivitySplashScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.animationView.speed= 0.6f
        Handler().postDelayed({
            val user=FirebaseAuth.getInstance().currentUser
            if(user==null)
            {
                startActivity(Intent(this, WalkThrewScreen::class.java))
            }
            else if(user.isEmailVerified)
            {
                startActivity(Intent(this, MainActivity::class.java))
            }
            else
            {
                startActivity(Intent(this, SignInActivity::class.java))
            }
        },5000)
    }
}