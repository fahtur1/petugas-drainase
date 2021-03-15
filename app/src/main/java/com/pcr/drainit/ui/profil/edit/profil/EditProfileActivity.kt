package com.pcr.drainit.ui.profil.edit.profil

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.pcr.drainit.R
import com.pcr.drainit.databinding.ActivityEditProfileBinding
import com.pcr.drainit.utill.Util
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_PERMISSION_CODE = 101
    }

    private lateinit var dataBinding: ActivityEditProfileBinding
    private val editProfileViewModel: EditProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)

        setSupportActionBar(dataBinding.editToolbar)
        supportActionBar?.apply {
            show()
            title = "Edit Profil"
            setDisplayHomeAsUpEnabled(true)
        }

        dataBinding.apply {
            viewModel = editProfileViewModel
            lifecycleOwner = this@EditProfileActivity
        }

        editProfileViewModel.action.observe(this, { action ->
            when (action) {
                EditProfileViewModel.ACTION_EDIT_PROFILE_SUCCESS -> onEditProfileSuccess()
                EditProfileViewModel.ACTION_EDIT_PROFILE_FORMBLANK -> onFormBlank()
                EditProfileViewModel.ACTION_EDIT_PROFILE_FAILED -> onEditProfileFailed()
                EditProfileViewModel.ACTION_EDIT_PROFILE_FOTO_CLICK -> onProfileFotoClick()
            }
        })
        editProfileViewModel.setPetugasData()


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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CODE && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            CropImage.startPickImageActivity(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE -> {
                    val uri = CropImage.getPickImageResultUri(this, data)

                    if (Util.isImageFile(uri, this)) {
                        val intent = CropImage.activity(uri).getIntent(this)
                        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
                    } else {
                        Toast.makeText(
                            this,
                            "Hanya Bisa Menampilkan File Gambar",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                    val imageResult = CropImage.getActivityResult(data)
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageResult.uri)

                    editProfileViewModel.fotoBitmap.value = Util.encodeBitmaptoBase64(bitmap)
                    editProfileViewModel.fotoPetugas.value = imageResult.uri
                }
            }
        }
    }

    private fun onProfileFotoClick() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
            &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            CropImage.startPickImageActivity(this)
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        val listPermission = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        ActivityCompat.requestPermissions(this, listPermission, REQUEST_PERMISSION_CODE)
    }

    private fun onEditProfileFailed() {
        Toast.makeText(this, "Gagal mengedit profil", Toast.LENGTH_SHORT).show()
    }

    private fun onFormBlank() {
        Toast.makeText(this, "Form tidak boleh kosong", Toast.LENGTH_SHORT).show()
    }

    private fun onEditProfileSuccess() {
        Toast.makeText(this, "Berhasil mengedit profile", Toast.LENGTH_SHORT).show()
        finish()
    }

}