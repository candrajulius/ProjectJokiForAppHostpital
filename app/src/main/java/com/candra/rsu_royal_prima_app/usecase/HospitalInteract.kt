package com.candra.rsu_royal_prima_app.usecase

import com.candra.rsu_royal_prima_app.data.Admisi
import com.candra.rsu_royal_prima_app.data.Doctor
import com.candra.rsu_royal_prima_app.data.Patient
import com.candra.rsu_royal_prima_app.data.Payment
import com.candra.rsu_royal_prima_app.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HospitalInteract @Inject constructor(
    private val repository: Repository
): HospitalUseCase
{
    override fun showAllPatient(): Flow<List<Patient>> =
        repository.showAllPatient()
    override suspend fun insertPatient(patient: Patient) = repository.insertPatient(patient)
    override suspend fun deletePatient(patient: Patient) = repository.deletePatient(patient)
    override suspend fun updatePatient(patient: Patient)  = repository.updatePatient(patient)

    override fun showAllDoctor(): Flow<List<Doctor>> = repository.showAllDoctor()
    override suspend fun insertDoctor(doctor: Doctor) = repository.insertDoctor(doctor)
    override suspend fun deleteDoctor(doctor: Doctor) = repository.deleteDoctor(doctor)
    override suspend fun updateDoctor(doctor: Doctor) = repository.updateDoctor(doctor)

    override fun showAllAdmisiWhereStatusQueue(status_queue: String): Flow<List<Admisi>> =
        repository.showAdmisiFromStatusQueue(status_queue)
    override suspend fun insertAdmisi(admisi: Admisi) = repository.insertAdmisi(admisi)
    override suspend fun updateAdmisi(admisi: Admisi) = repository.updateAdmisi(admisi)
    override suspend fun deleteAdmisi(admisi: Admisi) = repository.deleteAdmisi(admisi)
    override fun showAllAdmisiPatient(): Flow<List<Admisi>> = repository.showAllAdmisiPatient()

    override suspend fun insertPayment(payment: Payment) = repository.insertPayment(payment)

}