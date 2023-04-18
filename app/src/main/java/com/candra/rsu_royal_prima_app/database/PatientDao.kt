package com.candra.rsu_royal_prima_app.database

import androidx.room.*
import com.candra.rsu_royal_prima_app.data.Patient
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPatient(patient: Patient)

    @Query("select * from table_name_patient order by id asc")
    fun showAllPatient(): Flow<List<Patient>>

    @Delete
    suspend fun deletePatient(patient: Patient)

    @Update
    suspend fun updatePatient(patient: Patient)


}