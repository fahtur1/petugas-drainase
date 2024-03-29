package com.pcr.drainit.model.response

import com.google.gson.annotations.SerializedName
import com.pcr.drainit.model.enitity.Petugas

data class PetugasLoginResponse(
    @SerializedName("access_token")
    var accessToken: String?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status_code")
    var statusCode: Int,
    @SerializedName("user")
    var petugas: Petugas
)