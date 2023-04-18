package com.candra.rsu_royal_prima_app.data.source

import com.candra.rsu_royal_prima_app.data.Admisi
import com.candra.rsu_royal_prima_app.data.Doctor
import com.candra.rsu_royal_prima_app.data.Patient
import com.candra.rsu_royal_prima_app.data.Payment
import com.candra.rsu_royal_prima_app.database.AdmisiDao
import com.candra.rsu_royal_prima_app.database.DoctorDao
import com.candra.rsu_royal_prima_app.database.PatientDao
import com.candra.rsu_royal_prima_app.database.PaymentDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val patientDao: PatientDao,
    private val admisiDao: AdmisiDao,
    private val doctorDao: DoctorDao,
    private val paymentDao: PaymentDao
){
    // For patient
    fun showAllPatient(): Flow<List<Patient>> = patientDao.showAllPatient()
    suspend fun insertPatient(patient: Patient) = patientDao.insertPatient(patient)
    suspend fun updatePatient(patient: Patient) = patientDao.updatePatient(patient)
    suspend fun deletePatient(patient: Patient) = patientDao.deletePatient(patient)

    // For doctor
    fun showAllDoctor(): Flow<List<Doctor>> = doctorDao.showAllDoctor()
    suspend fun insertDoctor(doctor: Doctor) = doctorDao.insertDoctor(doctor)
    suspend fun deleteDoctor(doctor: Doctor) = doctorDao.deleteDoctor(doctor)
    suspend fun updateDoctor(doctor: Doctor) = doctorDao.updateDoctor(doctor)

    // For Admisi
    fun showAdmisiFromStatus(status_queue: String): Flow<List<Admisi>> =
        admisiDao.showAdmisiFromStatus(status_queue)
    suspend fun insertAdmisiPatient(admisi: Admisi) = admisiDao.insertAdmisiPatient(admisi)
    suspend fun deleteAdmisiPatient(admisi: Admisi) = admisiDao.deleteAdmisiPatient(admisi)
    suspend fun updateAdmisiPatient(admisi: Admisi) = admisiDao.updateAdmisiPatient(admisi)
    fun showAllAdmisiPatient(): Flow<List<Admisi>> = admisiDao.showAllAdmisiPatient()

    // For Payment
    suspend fun insertPayment(payment: Payment) = paymentDao.insertPayment(payment)
}