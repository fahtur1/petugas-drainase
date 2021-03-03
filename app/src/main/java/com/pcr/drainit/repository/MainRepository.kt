package com.pcr.drainit.repository

import com.pcr.drainit.api.PengaduanService
import com.pcr.drainit.api.PetugasService
import com.pcr.drainit.model.enitity.Petugas
import com.pcr.drainit.model.response.PengaduanUpdateResponse
import com.pcr.drainit.model.response.PengaduanGetResponse
import com.pcr.drainit.model.response.PetugasLoginResponse
import com.pcr.drainit.utill.Resource
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val petugasService: PetugasService,
    private val pengaduanService: PengaduanService
) {

    suspend fun getPetugasProfile(token: String): Resource<Petugas> {
        petugasService.getPetugasProfile(token).let { response ->
            if (response.isSuccessful) {
                response.body()?.let { dataPetugas ->
                    return Resource.Success(dataPetugas, response.code())
                }
            }
            return Resource.Error(response.message(), response.code())
        }
    }

    suspend fun petugasLogin(petugas: Petugas): Resource<PetugasLoginResponse> {
        petugasService.login(petugas).let { response ->
            if (response.isSuccessful) {
                response.body()?.let { dataPetugas ->
                    return Resource.Success(dataPetugas, response.code())
                }
            }
            return Resource.Error(response.message(), response.code())
        }
    }

    suspend fun getAllPengaduan(token: String): Resource<PengaduanGetResponse> {
        pengaduanService.getAllPengaduan(token).let { response ->
            if (response.isSuccessful) {
                response.body()?.let { listData ->
                    return Resource.Success(listData, response.code())
                }
            }
            return Resource.Error(response.message())
        }
    }

    suspend fun assignPengaduan(
        token: String,
        id: String
    ): Resource<PengaduanUpdateResponse> {
        pengaduanService.updatePengaduan(
            token = token,
            id = id,
            statusPengaduan = "Laporan Proses",
            laporanPetugas = "",
            method = "put"
        ).let { response ->
            if (response.isSuccessful) {
                response.body()?.let { return Resource.Success(it, response.code()) }
            }
            return Resource.Error(response.message())
        }
    }

    suspend fun getPengaduanAssign(token: String): Resource<PengaduanGetResponse> {
        pengaduanService.getPengaduanAssign(token).let { response ->
            if (response.isSuccessful) {
                response.body()?.let { return Resource.Success(it, response.code()) }
            }
            return Resource.Error(response.message())
        }
    }

    suspend fun updatePengaduan(
        token: String,
        id: String,
        laporanPetugas: String
    ): Resource<PengaduanUpdateResponse> {
        pengaduanService.updatePengaduan(
            token = token,
            id = id,
            laporanPetugas = laporanPetugas,
            statusPengaduan = "Laporan Selesai",
            method = "put"
        ).let { response ->
            if (response.isSuccessful) {
                response.body()?.let { return Resource.Success(it, response.code()) }
            }
            return Resource.Error(response.message())
        }
    }

}