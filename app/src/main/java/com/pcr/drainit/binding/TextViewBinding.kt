package com.pcr.drainit.binding

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.pcr.drainit.R

@BindingAdapter("android:setColorTextFromStatus")
fun TextView.setColorText(status: String) {
    when (status) {
        "Laporan Proses" -> setTextColor(
            ContextCompat.getColor(
                context,
                R.color.statusRiwayatProses
            )
        )
        "Laporan Selesai" -> setTextColor(
            ContextCompat.getColor(
                context,
                R.color.statusRiwayatSelesai
            )
        )
        "Pengajuan ditolak" -> setTextColor(
            ContextCompat.getColor(
                context,
                R.color.statusPengaduanTolak
            )
        )
        "Sudah diverifikasi" -> setTextColor(
            ContextCompat.getColor(
                context,
                R.color.statusPengaduanVerified
            )
        )
        "Verified" -> setTextColor(
            ContextCompat.getColor(
                context,
                R.color.statusPengaduanVerified
            )
        )
    }
}