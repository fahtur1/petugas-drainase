package com.pcr.drainit.model.enitity


import com.google.gson.annotations.SerializedName

data class Petugas(
    @SerializedName("alamat")
    var alamat: String? = "",
    @SerializedName("created_at")
    var createdAt: String? = "",
    @SerializedName("email")
    var email: String? = "",
    @SerializedName("email_verified_at")
    var emailVerifiedAt: Any? = Any(),
    @SerializedName("foto")
    var foto: String? = "",
    @SerializedName("id")
    var id: String? = "",
    @SerializedName("nama")
    var nama: String? = "",
    @SerializedName("no_hp")
    var noHp: String? = "",
    @SerializedName("posisi_petugas")
    var posisiPetugas: String? = "",
    @SerializedName("tempat_lahir")
    var tempatLahir: String? = "",
    @SerializedName("tgl_lahir")
    var tglLahir: String? = "",
    @SerializedName("updated_at")
    var updatedAt: String? = "",
    @SerializedName("password")
    var password: String? = ""
)