package com.pcr.drainit.api

import com.pcr.drainit.model.enitity.Petugas
import com.pcr.drainit.model.response.PetugasChangePasswordResponse
import com.pcr.drainit.model.response.PetugasEditResponse
import com.pcr.drainit.model.response.PetugasLoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface PetugasService {

    @POST("login/petugas")
    suspend fun login(
        @Body petugas: Petugas
    ): Response<PetugasLoginResponse>

    @GET("petugas/profile")
    suspend fun getPetugasProfile(
        @Header("Authorization") token: String
    ): Response<Petugas>

    @Multipart
    @POST("petugas")
    suspend fun updatePetugasProfile(
        @Header("Authorization") token: String,
        @PartMap petugas: Map<String, @JvmSuppressWildcards RequestBody>
    ): Response<PetugasEditResponse>

    @FormUrlEncoded
    @POST("change_password/petugas")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Field("oldpassword") oldPassword: String,
        @Field("newpassword") newPassword: String,
        @Field("newpassword_confirmation") newPasswordConfirm: String,
        @Field("_method") method: String
    ): Response<PetugasChangePasswordResponse>

}