package com.pcr.drainit.ui.detail.riwayat

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pcr.drainit.R
import com.pcr.drainit.databinding.ActivityDetailRiwayatBinding
import com.pcr.drainit.model.enitity.Pengaduan
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailRiwayatActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DETAIL_RIWAYAT_PARCEL = "extra_detail_riwayat_parcel"
    }

    private lateinit var dataBinding: ActivityDetailRiwayatBinding
    private val detailRiwayatViewModel: DetailRiwayatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_riwayat)

        val item: Pengaduan? = intent.getParcelableExtra(EXTRA_DETAIL_RIWAYAT_PARCEL)

        dataBinding.apply {
            data = item
            viewModel = detailRiwayatViewModel
            lifecycleOwner = this@DetailRiwayatActivity
        }

        detailRiwayatViewModel.apply {
            idLaporan.value = item?.id
            laporanPetugas.value = item?.laporanPetugas
            tempValue = item?.laporanPetugas

            detailRiwayatViewModel.action.observe(this@DetailRiwayatActivity, { action ->
                when (action) {
                    DetailRiwayatViewModel.ACTION_DETAIL_RIWAYAT_BACK -> onBackButtonClicked()
                    DetailRiwayatViewModel.ACTION_DETAIL_BACK_WITHCHANGES -> onBackButtonWithChanges()
                    DetailRiwayatViewModel.ACTION_DETAIL_CANCEL_EDIT -> onCancelEdit()
                    DetailRiwayatViewModel.ACTION_DETAIL_RIWAYAT_UPDATED -> onRiwayatUpdated()
                }
            })
        }
    }

    private fun onRiwayatUpdated() {
        Toast.makeText(this, "Riwayat berhasil di update", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun onCancelEdit() {
        MaterialAlertDialogBuilder(this)
            .setMessage("Yakin ingin buang perubahan ?")
            .setPositiveButton("Ya") { _, _ ->
                detailRiwayatViewModel.editEnabled.value = false
                detailRiwayatViewModel.resetLaporanValue()
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun onBackButtonWithChanges() {
        MaterialAlertDialogBuilder(this)
            .setMessage("Yakin ingin buang perubahan ?")
            .setPositiveButton("Ya") { _, _ ->
                finish()
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun onBackButtonClicked() {
        finish()
    }

}