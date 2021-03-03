package com.pcr.drainit.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.pcr.drainit.R
import com.pcr.drainit.databinding.ActivityMainBinding
import com.pcr.drainit.utill.Session
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ChipNavigationBar.OnItemSelectedListener {

    companion object {
        const val MAIN_EXTRA_MESSAGE = "main_extra_message"
    }

    private lateinit var dataBinding: ActivityMainBinding

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    private var backButtonPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        navHostFragment =
            supportFragmentManager.findFragmentById(dataBinding.navHost.id) as NavHostFragment
        navController = navHostFragment.navController

        dataBinding.apply {
            bottomnav.apply {
                selectedItemId = R.id.nav_list
                setupWithNavController(navController)
            }
            navbarMain.apply {
                setOnItemSelectedListener(this@MainActivity)
                setItemSelected(bottomnav.selectedItemId)
            }
        }

        intent.getStringExtra(MAIN_EXTRA_MESSAGE)?.let {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onItemSelected(id: Int) {
        dataBinding.bottomnav.selectedItemId = id
    }

    override fun onBackPressed() {
        if (navHostFragment.childFragmentManager.backStackEntryCount > 0) {
            navController.popBackStack()
            dataBinding.navbarMain.setItemSelected(navController.currentDestination?.id ?: 0)
        } else {
            if (backButtonPressedOnce) {
                finish()
            } else {
                backButtonPressedOnce = true
                Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show()

                GlobalScope.launch {
                    delay(1500L)
                    backButtonPressedOnce = false
                }
            }
        }
    }
}