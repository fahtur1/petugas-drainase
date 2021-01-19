package com.pcr.drainit.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.pcr.drainit.R
import com.pcr.drainit.databinding.ActivityDetailPengaduanBinding
import com.pcr.drainit.utill.Session
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityDetailPengaduanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_pengaduan)

        Session.unset()
    }

}