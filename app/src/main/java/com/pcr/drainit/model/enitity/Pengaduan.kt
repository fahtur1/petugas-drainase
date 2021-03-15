package com.pcr.drainit.model.enitity


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pengaduan(
    @SerializedName("deskripsi_pengaduan")
    var deskripsiPengaduan: String? = null,
    @SerializedName("feedback_masyarakat")
    var feedbackMasyarakat: String? = null,
    @SerializedName("foto")
    var foto: String? = null,
    @SerializedName("geometry")
    var geometry: String? = null,
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("id_admin")
    var idAdmin: String? = null,
    @SerializedName("id_masyarakat")
    var idMasyarakat: String? = null,
    @SerializedName("id_petugas")
    var idPetugas: String? = null,
    @SerializedName("laporan_petugas")
    var laporanPetugas: String? = null,
    @SerializedName("nama_jalan")
    var namaJalan: String? = null,
    @SerializedName("status_pengaduan")
    var statusPengaduan: String? = null,
    @SerializedName("tipe_pengaduan")
    var tipePengaduan: String? = null,
    @SerializedName("nama_petugas")
    var namaPetugas: String? = null,
    @SerializedName("nama_admin")
    var namaAdmin: String? = null,
    @SerializedName("nama_pelapor")
    var namaPelapor: String? = null
) : Parcelable