package com.pcr.drainit.repository

import com.pcr.drainit.api.PetugasService
import com.pcr.drainit.model.Masyarakat
import com.pcr.drainit.model.MasyarakatLoginResponse
import com.pcr.drainit.model.Petugas
import com.pcr.drainit.model.PetugasLoginResponse
import com.pcr.drainit.utill.Resource
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val petugasService: PetugasService
) {

    suspend fun petugasLogin(petugas: Petugas): Resource<PetugasLoginResponse> {
        petugasService.login(petugas).let { response ->
            if (response.isSuccessful) {
                response.body()?.let { return Resource.Success(it) }
            }
            return Resource.Error(response.message())
        }
    }

    suspend fun masyarakatLogin(masyarakat: Masyarakat): Resource<MasyarakatLoginResponse> {
        petugasService.loginMasyarakat(masyarakat).let { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    return Resource.Success(it)
                }
            }
            return Resource.Error(response.message())
        }
    }

}