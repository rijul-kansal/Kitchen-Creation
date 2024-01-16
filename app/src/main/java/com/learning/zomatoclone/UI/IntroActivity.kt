package com.learning.zomatoclone.UI

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.learning.zomatoclone.Activity.SignInActivity
import com.learning.zomatoclone.Activity.SignUpActivity
import com.learning.zomatoclone.R
import com.learning.zomatoclone.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    lateinit var binding:ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityIntroBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val text=binding.textView.text.toString()
        charByCharDisplay(text,binding.textView)

        binding.SignInBtn.setOnClickListener {
            startActivity(Intent(this,SignInActivity::class.java))
        }
        binding.SignUpBtn.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
    }

    fun charByCharDisplay(textToAnimate:String,textView:View)
    {
        for (i in textToAnimate.indices) {
            val charAnimator = ObjectAnimator.ofFloat(
                textView,
                "alpha",
                0f,
                1f
            ).apply {
                startDelay = (i * 10000).toLong() // Adjust delay as needed
                duration = 5000 // Adjust duration as needed
            }
            charAnimator.start()
        }
    }
}