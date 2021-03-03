package com.pcr.drainit.ui.profil.edit

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.pcr.drainit.R
import com.pcr.drainit.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityEditProfileBinding
    private val editProfileViewModel: EditProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)

        dataBinding.apply {
            viewModel = editProfileViewModel
            lifecycleOwner = this@EditProfileActivity
        }
    }

}