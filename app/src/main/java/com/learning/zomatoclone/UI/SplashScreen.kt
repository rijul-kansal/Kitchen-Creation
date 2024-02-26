package com.learning.zomatoclone.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.window.OnBackInvokedDispatcher
import com.google.firebase.auth.FirebaseAuth
import com.learning.zomatoclone.Activity.MainActivity
import com.learning.zomatoclone.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    // view Binding
    lateinit var binding:ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivitySplashScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // speed of lottie
        binding.animationView.speed= 0.6f

        // delay of 5 second
        Handler().postDelayed({
            val user=FirebaseAuth.getInstance().currentUser
            if(user !=null && user.isEmailVerified)
            {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            else
            {
                startActivity(Intent(this, WalkThrewScreen::class.java))
                finish()
            }
        },5000)
    }
}