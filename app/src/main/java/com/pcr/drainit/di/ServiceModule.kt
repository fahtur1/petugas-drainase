package com.pcr.drainit.di

import com.pcr.drainit.api.PengaduanService
import com.pcr.drainit.api.PetugasService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun providePetugasService(retrofit: Retrofit) = retrofit.create(PetugasService::class.java)

    @Singleton
    @Provides
    fun providePengaduanService(retrofit: Retrofit) = retrofit.create(PengaduanService::class.java)

}