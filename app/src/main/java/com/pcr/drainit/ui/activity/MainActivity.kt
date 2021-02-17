package com.pcr.drainit.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.pcr.drainit.R
import com.pcr.drainit.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ChipNavigationBar.OnItemSelectedListener {

    private lateinit var dataBinding: ActivityMainBinding

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        navHostFragment =
            supportFragmentManager.findFragmentById(dataBinding.navHost.id) as NavHostFragment
        navController = navHostFragment.navController

        dataBinding.navbarMain.apply {
            setOnItemSelectedListener(this@MainActivity)
            setItemSelected(R.id.nav_list)
        }
    }

    override fun onItemSelected(id: Int) {
        when (id) {
            R.id.nav_list -> {
                navController.navigate(id)
            }
            R.id.nav_map -> {
                navController.navigate(id)
            }
            R.id.nav_profil -> {
                navController.navigate(id)
            }
        }
    }
}