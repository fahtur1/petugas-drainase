package com.pcr.drainit.di

import com.pcr.drainit.api.PetugasService
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun providePetugasService(retrofit: Retrofit) = retrofit.create(PetugasService::class.java)

}