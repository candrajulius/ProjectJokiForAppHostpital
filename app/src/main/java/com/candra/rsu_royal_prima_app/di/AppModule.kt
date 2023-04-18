package com.candra.rsu_royal_prima_app.di

import com.candra.rsu_royal_prima_app.usecase.HospitalInteract
import com.candra.rsu_royal_prima_app.usecase.HospitalUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun provideUseCase(hospitalInteract: HospitalInteract): HospitalUseCase
}