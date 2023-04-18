package com.candra.rsu_royal_prima_app.database

import androidx.room.*
import com.candra.rsu_royal_prima_app.data.Doctor
import com.candra.rsu_royal_prima_app.data.Patient
import kotlinx.coroutines.flow.Flow


@Dao
interface DoctorDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDoctor(doctor: Doctor)

    @Query("select * from table_name_doctor order by id asc")
    fun showAllDoctor(): Flow<List<Doctor>>

    @Delete
    suspend fun deleteDoctor(doctor: Doctor)

    @Update
    suspend fun updateDoctor(doctor: Doctor)

}