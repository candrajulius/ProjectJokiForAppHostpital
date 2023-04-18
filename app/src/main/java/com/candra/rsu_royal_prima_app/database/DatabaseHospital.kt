package com.candra.rsu_royal_prima_app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.candra.rsu_royal_prima_app.data.Admisi
import com.candra.rsu_royal_prima_app.data.Doctor
import com.candra.rsu_royal_prima_app.data.Patient
import com.candra.rsu_royal_prima_app.data.Payment

@Database(entities = [Doctor::class,Patient::class,Admisi::class,Payment::class], version = 2, exportSchema = false)
abstract class DatabaseHospital: RoomDatabase()
{
    abstract fun patientDao(): PatientDao
    abstract fun doctorDao(): DoctorDao
    abstract fun admisiDao(): AdmisiDao
    abstract fun paymentDao(): PaymentDao
}
