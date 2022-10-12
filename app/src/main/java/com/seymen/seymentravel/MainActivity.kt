package com.seymen.seymentravel

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.seymen.seymentravel.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var bottomNavigationView : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        bottomNavigationView = binding.bottomNavView
        binding.bottomNavView.itemIconTintList = null
        setupNavController()
    }

    private fun setupNavController() {
        val navHostFragment  = supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNavView,navHostFragment.navController)
    }
}