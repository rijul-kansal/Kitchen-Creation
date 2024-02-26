package com.learning.zomatoclone.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.learning.zomatoclone.Utils.BaseActivity
import com.learning.zomatoclone.ViewModel.FireStoreStorage
import com.learning.zomatoclone.databinding.ActivityContactUsBinding

class ContactUsActivity : BaseActivity() {
    lateinit var binding:ActivityContactUsBinding
    lateinit var viewModel:FireStoreStorage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactUsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.sendBtn.setOnClickListener {

            val subject=binding.subject.text.toString()
            val mess=binding.message.text.toString()

            val mIntent = Intent(Intent.ACTION_SEND)
            mIntent.data = Uri.parse("mailto:")
            mIntent.type = "text/plain"
            mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("kansalrijul@gmail.com"))
            mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            mIntent.putExtra(Intent.EXTRA_TEXT, mess)


            try {
                startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
            }
            catch (e: Exception){
                Toast(this, e.message.toString())
            }
        }
    }
}