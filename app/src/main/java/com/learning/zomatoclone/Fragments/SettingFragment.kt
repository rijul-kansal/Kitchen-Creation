package com.learning.zomatoclone.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.learning.zomatoclone.Activity.ContactUsActivity
import com.learning.zomatoclone.Activity.InterestActivity
import com.learning.zomatoclone.Activity.ProfileActivity
import com.learning.zomatoclone.UI.IntroActivity
import com.learning.zomatoclone.Utils.Constants
import com.learning.zomatoclone.ViewModel.AuthenticationClass
import com.learning.zomatoclone.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {
    lateinit var viewModel: AuthenticationClass
    lateinit var binding:FragmentSettingBinding
    override fun onCreate(savedInstanceState: Bundle?){
        binding= FragmentSettingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[AuthenticationClass::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=binding.root

        binding.signOut.setOnClickListener {
            viewModel.SignOut()
            startActivity(Intent(requireActivity(),IntroActivity::class.java))
        }
        binding.profile.setOnClickListener {
            startActivity(Intent(requireActivity(),ProfileActivity::class.java))
        }
        binding.contactUs.setOnClickListener {
            startActivity(Intent(requireActivity(),ContactUsActivity::class.java))
        }

        binding.Interest.setOnClickListener {
            val intent=Intent(requireActivity(),InterestActivity::class.java)
            intent.putExtra(Constants.Interests,"Yes")
            startActivity(intent)
        }
        return view
    }
}