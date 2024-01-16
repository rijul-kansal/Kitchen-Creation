package com.learning.zomatoclone.Utils

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.learning.zomatoclone.R

open class BaseActivity : AppCompatActivity() {
    var dialog:Dialog?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    fun Toast(constext:Context,message:String)
    {
        android.widget.Toast.makeText(constext,message, android.widget.Toast.LENGTH_LONG).show()
    }

    fun showProgressBar(context: Context)
    {
        dialog= Dialog(context)
        dialog!!.setContentView(R.layout.progress_bar)
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