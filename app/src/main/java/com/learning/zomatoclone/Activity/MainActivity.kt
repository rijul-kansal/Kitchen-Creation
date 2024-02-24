package com.learning.zomatoclone.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.learning.zomatoclone.Fragments.FavFragment
import com.learning.zomatoclone.Fragments.HomeFragment
import com.learning.zomatoclone.Fragments.SettingFragment
import com.learning.zomatoclone.R
import com.learning.zomatoclone.Utils.BaseActivity
import com.learning.zomatoclone.databinding.ActivityMainBinding


class MainActivity : BaseActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

//        binding.bottomNavigationView.background = null
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.favourite -> replaceFragment(FavFragment())
                R.id.setting -> replaceFragment(SettingFragment())
            }
            true
        }

    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.commit()
    }
}