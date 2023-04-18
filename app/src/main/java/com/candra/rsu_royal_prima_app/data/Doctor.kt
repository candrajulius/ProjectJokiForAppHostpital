package com.candra.rsu_royal_prima_app.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.candra.rsu_royal_prima_app.helper.Constant.ADDRESS_DOCTOR
import com.candra.rsu_royal_prima_app.helper.Constant.DATE_OF_BIRTH_DOCTOR
import com.candra.rsu_royal_prima_app.helper.Constant.GENDER_DOCTOR
import com.candra.rsu_royal_prima_app.helper.Constant.NAME_OF_DOCTOR
import com.candra.rsu_royal_prima_app.helper.Constant.NO_KTP_DOCTOR
import com.candra.rsu_royal_prima_app.helper.Constant.PLACE_OF_BIRTHDAY_DOCTOR
import com.candra.rsu_royal_prima_app.helper.Constant.SPECIALIST
import com.candra.rsu_royal_prima_app.helper.Constant.STATUS_OF_SINGLE_BIRTH
import com.candra.rsu_royal_prima_app.helper.Constant.TABLE_NAME_DOCTOR
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = TABLE_NAME_DOCTOR)
data class Doctor(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = NAME_OF_DOCTOR)
    val name_doctor: String,

    @ColumnInfo(name = ADDRESS_DOCTOR)
    val address_doctor: String,

    @ColumnInfo(name = SPECIALIST)
    val specialist_doctor: String,

    @ColumnInfo(name = NO_KTP_DOCTOR)
    val no_ktp_doctor: String,

    @ColumnInfo(name = GENDER_DOCTOR)
    val gender_doctor: String,

    @ColumnInfo(name = PLACE_OF_BIRTHDAY_DOCTOR)
    val place_of_birhtday_doctor: String,

    @ColumnInfo(name = DATE_OF_BIRTH_DOCTOR)
    val date_of_birth_doctor: String,

    @ColumnInfo(name = STATUS_OF_SINGLE_BIRTH)
    val status_of_single_birth: String
): Parcelable