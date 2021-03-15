package com.pcr.drainit.repository

import com.pcr.drainit.api.PengaduanService
import com.pcr.drainit.api.PetugasService
import com.pcr.drainit.model.enitity.Petugas
import com.pcr.drainit.model.response.*
import com.pcr.drainit.utill.Resource
import com.pcr.drainit.utill.Util
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
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
        laporanPetugas: String,
        statusPengaduan: String
    ): Resource<PengaduanUpdateResponse> {
        pengaduanService.updatePengaduan(
            token = token,
            id = id,
            laporanPetugas = laporanPetugas,
            statusPengaduan = statusPengaduan,
            method = "put"
        ).let { response ->
            if (response.isSuccessful) {
                response.body()?.let { return Resource.Success(it, response.code()) }
            }
            return Resource.Error(response.message())
        }
    }

    suspend fun updatePetugasProfile(
        token: String,
        petugas: Petugas
    ): Resource<PetugasEditResponse> {
        val typeJson = "text/plain"
        val method = "put"

        val mapPetugas = mapOf(
            "email" to (petugas.email ?: "").toRequestBody(typeJson.toMediaTypeOrNull()),
            "nama" to (petugas.nama ?: "").toRequestBody(typeJson.toMediaTypeOrNull()),
            "foto" to (petugas.foto ?: "").toRequestBody(typeJson.toMediaTypeOrNull()),
            "posisi_petugas" to
                    (petugas.posisiPetugas ?: "").toRequestBody(typeJson.toMediaTypeOrNull()),
            "tempat_lahir" to
                    (petugas.tempatLahir ?: "").toRequestBody(typeJson.toMediaTypeOrNull()),
            "tgl_lahir" to (petugas.tglLahir ?: "").toRequestBody(typeJson.toMediaTypeOrNull()),
            "alamat" to (petugas.alamat ?: "").toRequestBody(typeJson.toMediaTypeOrNull()),
            "no_hp" to (petugas.noHp ?: "").toRequestBody(typeJson.toMediaTypeOrNull()),
            "_method" to method.toRequestBody(typeJson.toMediaTypeOrNull())
        )


        petugasService.updatePetugasProfile(token, mapPetugas)
            .let { response ->
                if (response.isSuccessful)
                    response.body()?.let { return Resource.Success(it, response.code()) }

                return Resource.Error(response.message())
            }
    }

    suspend fun changePassword(
        token: String,
        oldPassword: String,
        newPassword: String
    ): Resource<PetugasChangePasswordResponse> {
        petugasService.changePassword(token, oldPassword, newPassword, newPassword, "put")
            .let { response ->
                if (response.isSuccessful)
                    response.body()?.let { return Resource.Success(it, response.code()) }
                return Resource.Error(response.message())
            }
    }

}