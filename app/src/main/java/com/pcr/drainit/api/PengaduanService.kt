package com.pcr.drainit.api

import com.pcr.drainit.model.response.PengaduanUpdateResponse
import com.pcr.drainit.model.response.PengaduanGetResponse
import retrofit2.Response
import retrofit2.http.*

interface PengaduanService {

    @GET("pengaduan_not_assign")
    suspend fun getAllPengaduan(
        @Header("Authorization") token: String
    ): Response<PengaduanGetResponse>

    @FormUrlEncoded
    @POST("update_pengaduan/petugas/{id}")
    suspend fun updatePengaduan(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Field("status_pengaduan") statusPengaduan: String,
        @Field("laporan_petugas") laporanPetugas: String,
        @Field("_method") method: String
    ): Response<PengaduanUpdateResponse>

    @GET("pengaduan_by_petugas")
    suspend fun getPengaduanAssign(
        @Header("Authorization") token: String
    ): Response<PengaduanGetResponse>

}