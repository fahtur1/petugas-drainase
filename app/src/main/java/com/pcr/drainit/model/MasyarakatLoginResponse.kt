package com.pcr.drainit.model


import com.google.gson.annotations.SerializedName

data class MasyarakatLoginResponse(
    @SerializedName("access_token")
    var accessToken: String?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status_code")
    var statusCode: Int?,
    @SerializedName("user")
    var masyarakat: Masyarakat?
)