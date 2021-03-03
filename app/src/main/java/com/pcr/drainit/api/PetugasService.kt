package com.pcr.drainit.api

import com.pcr.drainit.model.enitity.Petugas
import com.pcr.drainit.model.response.PetugasLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface PetugasService {

    @POST("login/petugas")
    suspend fun login(
        @Body petugas: Petugas
    ): Response<PetugasLoginResponse>

    @GET("petugas/profile")
    suspend fun getPetugasProfile(
        @Header("Authorization") token: String
    ): Response<Petugas>

}