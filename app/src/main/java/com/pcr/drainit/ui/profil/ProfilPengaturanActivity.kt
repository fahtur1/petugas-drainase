package com.pcr.drainit.ui.profil

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pcr.drainit.R
import com.pcr.drainit.databinding.ActivityProfilPengaturanBinding
import com.pcr.drainit.ui.login.LoginActivity
import com.pcr.drainit.ui.profil.edit.password.EditPasswordActivity
import com.pcr.drainit.ui.profil.edit.profil.EditProfileActivity
import com.pcr.drainit.utill.Session

class ProfilPengaturanActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var dataBinding: ActivityProfilPengaturanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_profil_pengaturan)

        setSupportActionBar(dataBinding.pengaturanToolbar)
        supportActionBar?.show()
        supportActionBar?.title = "Pengaturan"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dataBinding.apply {
            rvEditProfile.setOnClickListener(this@ProfilPengaturanActivity)
            btnLogout.setOnClickListener(this@ProfilPengaturanActivity)
            rvEditPassword.setOnClickListener(this@ProfilPengaturanActivity)
        }
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rv_edit_profile -> onEditProfileClick()
            R.id.btn_logout -> onBtnLogoutClick()
            R.id.rv_edit_password -> onEditPasswordClick()
        }
    }

    private fun onEditPasswordClick() {
        startActivity(Intent(this, EditPasswordActivity::class.java))
    }

    private fun onBtnLogoutClick() {
        MaterialAlertDialogBuilder(this)
            .setMessage("Yakin ingin logout ?")
            .setPositiveButton("Logout") { _, _ ->
                Session.unset()

                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun onEditProfileClick() {
        startActivity(Intent(this, EditProfileActivity::class.java))
    }

}