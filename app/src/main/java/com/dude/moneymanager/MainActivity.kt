package com.dude.moneymanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

import com.dude.moneymanager.databinding.ActivityMainBinding
import com.dude.moneymanager.databinding.FragmentRegisterExchangeBinding

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

            //,R.layout.fragment_register_exchange, container, false)
        val navController = this.findNavController(R.id.theNavHostFragment)

        NavigationUI.setupWithNavController(binding.navView, navController)
        drawerLayout = binding.drawerLayout
        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)

    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.theNavHostFragment)
        return NavigationUI.navigateUp(navController,drawerLayout)
    }
}