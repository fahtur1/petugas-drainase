package com.pcr.drainit.model.response


import com.google.gson.annotations.SerializedName
import com.pcr.drainit.model.enitity.Pengaduan

data class PengaduanUpdateResponse(
    @SerializedName("data")
    var `data`: Pengaduan? = Pengaduan(),
    @SerializedName("message")
    var message: String? = "",
    @SerializedName("status_code")
    var statusCode: Int? = 0
)