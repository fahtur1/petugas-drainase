package com.pcr.drainit.ui.profil.edit.password

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.pcr.drainit.R
import com.pcr.drainit.databinding.ActivityEditPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditPasswordActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityEditPasswordBinding
    private val editPasswordViewModel: EditPasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_password)

        setSupportActionBar(dataBinding.editToolbar)
        supportActionBar?.apply {
            show()
            title = "Ubah Password"
            setDisplayHomeAsUpEnabled(true)
        }

        dataBinding.apply {
            lifecycleOwner = this@EditPasswordActivity
            viewModel = editPasswordViewModel
        }

        editPasswordViewModel.action.observe(this, { action ->
            when (action) {
                EditPasswordViewModel.ACTION_EDIT_PASSWORD_SUCCESS -> onPasswordSuccessChanged()
                EditPasswordViewModel.ACTION_EDIT_PASSWORD_NOT_SAME -> passwordIsNotSame()
                EditPasswordViewModel.ACTION_EDIT_PASSWORD_OLD_WRONG -> oldPassIsWrong()
            }
        })
    }

    private fun oldPassIsWrong() {
        Toast.makeText(this, "Password lama salah", Toast.LENGTH_SHORT).show()
    }

    private fun passwordIsNotSame() {
        Toast.makeText(
            this,
            "Password baru dan confirmasi password harus sama",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun onPasswordSuccessChanged() {
        finish()
        Toast.makeText(this, "Berhasil mengubah password", Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}