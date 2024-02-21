package com.learning.zomatoclone.ViewModel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthenticationClass():ViewModel() {
    private  var mAuth=FirebaseAuth.getInstance()
    private var signUpResult:MutableLiveData<Task<AuthResult>> = MutableLiveData()
    private var signInResult:MutableLiveData<Task<AuthResult>> = MutableLiveData()
    private var verifyTask:MutableLiveData<Task<Void>> = MutableLiveData()
    fun SignUp(name:String,activity: Activity, email:String, password:String)
    {
        viewModelScope.launch(Dispatchers.IO) {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity) { task ->
                    signUpResult.value = task
                }
        }
    }
    fun SignIn(activity: Activity,email: String,password: String)
    {
        viewModelScope.launch(Dispatchers.IO) {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity) { task ->
                    signInResult.value = task
                }
        }
    }

    fun sendVerificationCode()
    {
        viewModelScope.launch(Dispatchers.IO) {
            mAuth.currentUser!!.sendEmailVerification()
                .addOnCompleteListener { task ->
                    verifyTask.value = task
                    if (task.isSuccessful) {
                        Log.d("rk", "email sent")
                    }
                }
        }
    }
    fun updateProfileInAuth(name:String)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val profileUpdates = userProfileChangeRequest {
                displayName = name
            }

            mAuth.currentUser!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("rk", "User profile updated.")
                    }
                }
        }
    }
    fun SignOut() {
        mAuth.signOut()
    }
    fun observerTaskResult():LiveData<Task<AuthResult>> = signUpResult
    fun observerTaskResultLogin():LiveData<Task<AuthResult>> = signInResult
    fun observerVerifiedEmail():LiveData<Task<Void>> = verifyTask

    fun getUserId():String
    {
        if(mAuth.currentUser!=null)
        {
            return mAuth.currentUser!!.uid
        }
        return ""
    }
}