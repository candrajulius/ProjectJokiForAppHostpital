package com.candra.rsu_royal_prima_app.repository

import com.candra.rsu_royal_prima_app.data.Admisi
import com.candra.rsu_royal_prima_app.data.Doctor
import com.candra.rsu_royal_prima_app.data.Patient
import com.candra.rsu_royal_prima_app.data.Payment
import com.candra.rsu_royal_prima_app.data.source.LocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource
): IRepository
{
    override fun showAllPatient(): Flow<List<Patient>> =
        localDataSource.showAllPatient()

    override suspend fun insertPatient(patient: Patient) =
        localDataSource.insertPatient(patient)

    override suspend fun deletePatient(patient: Patient) =
        localDataSource.deletePatient(patient)

    override suspend fun updatePatient(patient: Patient) =
        localDataSource.updatePatient(patient)

    override fun showAllDoctor(): Flow<List<Doctor>> =
        localDataSource.showAllDoctor()

    override suspend fun insertDoctor(doctor: Doctor) =
        localDataSource.insertDoctor(doctor)

    override suspend fun updateDoctor(doctor: Doctor) =
        localDataSource.updateDoctor(doctor)

    override suspend fun deleteDoctor(doctor: Doctor) = localDataSource.deleteDoctor(doctor)

    override fun showAdmisiFromStatusQueue(status_queue: String): Flow<List<Admisi>> =
        localDataSource.showAdmisiFromStatus(status_queue)

    override suspend fun insertAdmisi(admisi: Admisi) =
        localDataSource.insertAdmisiPatient(admisi)

    override suspend fun updateAdmisi(admisi: Admisi) =
        localDataSource.updateAdmisiPatient(admisi)

    override suspend fun deleteAdmisi(admisi: Admisi) =
        localDataSource.deleteAdmisiPatient(admisi)

    override fun showAllAdmisiPatient(): Flow<List<Admisi>> =
        localDataSource.showAllAdmisiPatient()

    override suspend fun insertPayment(payment: Payment) = localDataSource.insertPayment(payment)

}