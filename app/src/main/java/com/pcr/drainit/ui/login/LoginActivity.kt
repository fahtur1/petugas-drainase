package com.pcr.drainit.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.pcr.drainit.R
import com.pcr.drainit.databinding.ActivityLoginBinding
import com.pcr.drainit.ui.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var dataBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        dataBinding.apply {
            lifecycleOwner = this@LoginActivity
            viewModel = loginViewModel
        }

        loginViewModel.action.observe(this, Observer { action ->
            when (action) {
                LoginViewModel.ACTION_LOGIN_SUCCESS -> loginSuccess()
                LoginViewModel.ACTION_LOGIN_FORM_BLANK -> formBlank()
                LoginViewModel.ACTION_LOGIN_USER_LOGGEDIN -> userLoggedIn()
                LoginViewModel.ACTION_LOGIN_INVALID -> userInvalid()
            }
        })
        loginViewModel.checkSession()
    }

    private fun userInvalid() {
        Snackbar.make(dataBinding.root, "User tidak ada", Snackbar.LENGTH_SHORT).show()
    }

    private fun userLoggedIn() {
        GlobalScope.launch {
            delay(1000L)

            val intent = Intent(this@LoginActivity, MainActivity::class.java).apply {
                putExtra(MainActivity.MAIN_EXTRA_MESSAGE, "User telah login")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            startActivity(intent)
        }
    }

    private fun formBlank() {
        Snackbar.make(dataBinding.root, "Form tidak boleh kosong", Snackbar.LENGTH_SHORT).show()
    }

    private fun loginSuccess() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }

}