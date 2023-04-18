package com.candra.rsu_royal_prima_app.di

import com.candra.rsu_royal_prima_app.repository.IRepository
import com.candra.rsu_royal_prima_app.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(repository: Repository): IRepository
}