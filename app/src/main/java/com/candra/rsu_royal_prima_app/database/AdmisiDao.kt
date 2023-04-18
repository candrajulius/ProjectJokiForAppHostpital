package com.candra.rsu_royal_prima_app.database

import androidx.room.*
import com.candra.rsu_royal_prima_app.data.Admisi
import kotlinx.coroutines.flow.Flow

@Dao
interface AdmisiDao{
    @Insert
    suspend fun insertAdmisiPatient(admisi: Admisi)

    @Update
    suspend fun updateAdmisiPatient(admisi: Admisi)

    @Delete
    suspend fun deleteAdmisiPatient(admisi: Admisi)

    @Query("select * from admisi order by id asc")
    fun showAllAdmisiPatient(): Flow<List<Admisi>>

    @Query("select * from admisi where status_queue like :status_queue order by id asc")
    fun showAdmisiFromStatus(status_queue: String): Flow<List<Admisi>>
}