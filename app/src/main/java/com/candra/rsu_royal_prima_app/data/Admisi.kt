package com.candra.rsu_royal_prima_app.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.candra.rsu_royal_prima_app.helper.Constant.COUNTER
import com.candra.rsu_royal_prima_app.helper.Constant.DATE
import com.candra.rsu_royal_prima_app.helper.Constant.NAME_OF_DOCTOR
import com.candra.rsu_royal_prima_app.helper.Constant.NAME_OF_PATIENT
import com.candra.rsu_royal_prima_app.helper.Constant.NMR_BPJS
import com.candra.rsu_royal_prima_app.helper.Constant.POLI
import com.candra.rsu_royal_prima_app.helper.Constant.QUEUE_NUMBER
import com.candra.rsu_royal_prima_app.helper.Constant.STATUS_QUEUE
import com.candra.rsu_royal_prima_app.helper.Constant.TABLE_NAME_ADMISI
import com.candra.rsu_royal_prima_app.helper.Constant.TIME_CALL_QUEUE
import com.candra.rsu_royal_prima_app.helper.Constant.TIME_IN_QUEUE
import com.candra.rsu_royal_prima_app.helper.Constant.TIME_IN_THREE
import com.candra.rsu_royal_prima_app.helper.Constant.WAITING_QUEUE
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = TABLE_NAME_ADMISI)
data class Admisi(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = QUEUE_NUMBER)
    val number_queue: String,

    @ColumnInfo(name = STATUS_QUEUE)
    val status_queue: String,

    @ColumnInfo(name = COUNTER)
    val counter: String,

    @ColumnInfo(name = TIME_IN_QUEUE)
    val time_in_queue: String,

    @ColumnInfo(name = TIME_CALL_QUEUE)
    val time_call_queue: String,

    @ColumnInfo(name = WAITING_QUEUE)
    val time_waiting: String,

    @ColumnInfo(name = TIME_IN_THREE)
    val time_three: String,

    @ColumnInfo(name = NMR_BPJS)
    val nmr_bpjs: String,

    @ColumnInfo(name = DATE)
    val date: String,

    @ColumnInfo(name = NAME_OF_PATIENT)
    val name_of_patient: String,

    @ColumnInfo(name = POLI)
    val poli: String,

    @ColumnInfo(name = NAME_OF_DOCTOR)
    val name_of_doctor: String,
): Parcelable