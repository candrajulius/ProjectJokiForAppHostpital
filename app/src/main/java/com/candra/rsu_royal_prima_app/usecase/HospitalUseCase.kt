package com.candra.rsu_royal_prima_app.usecase

import com.candra.rsu_royal_prima_app.data.Admisi
import com.candra.rsu_royal_prima_app.data.Doctor
import com.candra.rsu_royal_prima_app.data.Patient
import com.candra.rsu_royal_prima_app.data.Payment
import kotlinx.coroutines.flow.Flow

interface HospitalUseCase {
    fun showAllPatient(): Flow<List<Patient>>
    suspend fun insertPatient(patient: Patient)
    suspend fun deletePatient(patient: Patient)
    suspend fun updatePatient(patient: Patient)

    fun showAllDoctor(): Flow<List<Doctor>>
    suspend fun insertDoctor(doctor: Doctor)
    suspend fun deleteDoctor(doctor: Doctor)
    suspend fun updateDoctor(doctor: Doctor)

    fun showAllAdmisiWhereStatusQueue(status_queue: String): Flow<List<Admisi>>
    suspend fun insertAdmisi(admisi: Admisi)
    suspend fun updateAdmisi(admisi: Admisi)
    suspend fun deleteAdmisi(admisi: Admisi)
    fun showAllAdmisiPatient(): Flow<List<Admisi>>

   suspend fun insertPayment(payment: Payment)
}