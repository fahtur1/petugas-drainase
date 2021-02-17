package com.pcr.drainit.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class PetugasLoginResponse(
    @SerializedName("access_token")
    var accessToken: String?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status_code")
    var statusCode: Int,
    @SerializedName("user")
    var petugas: Petugas
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        TODO("user")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(accessToken)
        parcel.writeString(message)
        parcel.writeInt(statusCode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PetugasLoginResponse> {
        override fun createFromParcel(parcel: Parcel): PetugasLoginResponse {
            return PetugasLoginResponse(parcel)
        }

        override fun newArray(size: Int): Array<PetugasLoginResponse?> {
            return arrayOfNulls(size)
        }
    }

}