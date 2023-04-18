package com.candra.rsu_royal_prima_app.data

import android.media.MediaDrm
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.candra.rsu_royal_prima_app.helper.Constant
import kotlinx.parcelize.Parcelize

@Entity(tableName = Constant.TABLE_PAYMENT)
data class Payment(
    @PrimaryKey(autoGenerate = true)
    val idPayment: Int,

    @ColumnInfo(name = Constant.NAME_OF_PATIENT)
    val patient: String,

    @ColumnInfo(name = Constant.STATUS_QUEUE)
    val status_queue: String,

    @ColumnInfo(name = Constant.POLI)
    val poly: String,

    @ColumnInfo(name = Constant.NMR_BPJS)
    val number_bpjs: String
)