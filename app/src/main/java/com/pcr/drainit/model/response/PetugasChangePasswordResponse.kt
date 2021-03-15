package com.pcr.drainit.model.response


import com.google.gson.annotations.SerializedName

data class PetugasChangePasswordResponse(
    @SerializedName("message")
    var message: String? = "",
    @SerializedName("status_code")
    var statusCode: Int? = 0
)