package com.candra.rsu_royal_prima_app.di

import android.content.Context
import androidx.room.Room
import com.candra.rsu_royal_prima_app.database.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): DatabaseHospital =
        Room.databaseBuilder(
            context,
            DatabaseHospital::class.java,"hospital.db"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

    @Provides
    fun providePatientDao(database: DatabaseHospital): PatientDao = database.patientDao()

    @Provides
    fun provideDoctorDao(database: DatabaseHospital): DoctorDao = database.doctorDao()

    @Provides
    fun provideAdmisiDao(database: DatabaseHospital): AdmisiDao = database.admisiDao()

    @Provides
    fun providePaymentDao(database: DatabaseHospital): PaymentDao = database.paymentDao()
}