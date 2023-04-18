package com.candra.rsu_royal_prima_app.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.candra.rsu_royal_prima_app.helper.Constant
import com.candra.rsu_royal_prima_app.helper.Constant.ADDRESS_PATIENT
import com.candra.rsu_royal_prima_app.helper.Constant.BLOOD_GROUP
import com.candra.rsu_royal_prima_app.helper.Constant.DATE_OF_BIRTH
import com.candra.rsu_royal_prima_app.helper.Constant.GENDER_PATIENT
import com.candra.rsu_royal_prima_app.helper.Constant.HOSPITAL_REFERRAL_NUMBER
import com.candra.rsu_royal_prima_app.helper.Constant.JOB_PATIENT
import com.candra.rsu_royal_prima_app.helper.Constant.NAME_OF_PATIENT
import com.candra.rsu_royal_prima_app.helper.Constant.NMR_BPJS
import com.candra.rsu_royal_prima_app.helper.Constant.NO_KTP
import com.candra.rsu_royal_prima_app.helper.Constant.PLACE_OF_BIRTHDAY
import com.candra.rsu_royal_prima_app.helper.Constant.STATUS_SINGLE
import com.candra.rsu_royal_prima_app.helper.Constant.TABLE_NAME_PATIENT
import com.candra.rsu_royal_prima_app.helper.Constant.TELEPHONE
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = TABLE_NAME_PATIENT)
data class Patient(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = NAME_OF_PATIENT)
    val name_patient: String,

    @ColumnInfo(name = ADDRESS_PATIENT)
    val address_patient: String,

    @ColumnInfo(name = TELEPHONE)
    val telephone_patient: String,

    @ColumnInfo(name = GENDER_PATIENT)
    val gender_patient: String,

    @ColumnInfo(name = NO_KTP)
    val no_ktp: String,

    @ColumnInfo(name = BLOOD_GROUP)
    val blood_group_patient: String,

    @ColumnInfo(name = PLACE_OF_BIRTHDAY)
    val place_of_birthday_patient: String,

    @ColumnInfo(name = DATE_OF_BIRTH)
    val date_of_birth: String,

    @ColumnInfo(name = JOB_PATIENT)
    val job_patient: String,

    @ColumnInfo(name = STATUS_SINGLE)
    val status_single: String,

    @ColumnInfo(name = NMR_BPJS)
    val nmr_bpjs_patient: String,

    @ColumnInfo(name = HOSPITAL_REFERRAL_NUMBER)
    val hospital_refereal_number: String

):Parcelable