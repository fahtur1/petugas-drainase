package com.pcr.drainit.api

import com.pcr.drainit.model.Petugas
import com.pcr.drainit.model.PetugasLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PetugasService {

    @POST("api/login/petugas")
    suspend fun login(
        @Body petugas: Petugas
    ): Response<PetugasLoginResponse>

}