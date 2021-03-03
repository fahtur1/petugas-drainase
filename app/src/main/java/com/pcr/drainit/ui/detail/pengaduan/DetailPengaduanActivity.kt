package com.pcr.drainit.ui.detail.pengaduan

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.pcr.drainit.R
import com.pcr.drainit.databinding.ActivityDetailPengaduanBinding
import com.pcr.drainit.model.enitity.Pengaduan
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPengaduanActivity : AppCompatActivity() {

    companion object {
        const val DETAIL_EXTRA_PARCEL = "detail_extra_parcel"
    }

    private lateinit var dataBinding: ActivityDetailPengaduanBinding
    private val detailPengaduanViewModel: DetailPengaduanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_pengaduan)
        val item: Pengaduan? = intent.getParcelableExtra(DETAIL_EXTRA_PARCEL)

        dataBinding.apply {
            lifecycleOwner = this@DetailPengaduanActivity
            viewModel = detailPengaduanViewModel
            data = item
        }

        detailPengaduanViewModel.action.observe(this, { action ->
            when (action) {
                DetailPengaduanViewModel.ACTION_DETAIL_ASSIGN -> pengaduanAssigned()
                DetailPengaduanViewModel.ACTION_DETAIL_BACK -> buttonBackOnClick()
            }
        })
        detailPengaduanViewModel.idPengaduan.value = item?.id
    }

    private fun buttonBackOnClick() {
        finish()
    }

    private fun pengaduanAssigned() {
        finish()
        Toast.makeText(this, "Berhasil menambahkan ke daftar riwayat", Toast.LENGTH_SHORT).show()
    }

}