package com.learning.zomatoclone.ViewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.collections.HashMap

class Storage :ViewModel() {
    private  val storage = Firebase.storage
    private  var storageRef = storage.reference
    lateinit var downloadUri:Uri
    fun uploadImage(fileUri : Uri) {
        viewModelScope.launch(Dispatchers.IO)
        {
            val ref = storageRef.child("UserImages/${fileUri.lastPathSegment}")
            val uploadTask = ref.putFile(fileUri)
            val urlTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                Log.d("rk","download ${ref.downloadUrl}")
                ref.downloadUrl

            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    downloadUri = task.result
                    Log.d("rk",downloadUri.toString())
                    val valuee=downloadUri.toString()
                    val map= HashMap<String,String>()
                    map["image"] = valuee
                    FireStoreStorage().updateUserPersonalDataIntoDB(map)
                } else {

                }
            }
        }
    }
}