package com.candra.rsu_royal_prima_app.helper

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Constant{
    const val TABLE_NAME_PATIENT = "table_name_patient"
    const val NAME_OF_PATIENT = "name_patient"
    const val ADDRESS_PATIENT = "address_patient"
    const val TELEPHONE = "telephone"
    const val GENDER_PATIENT = "gender_patient"
    const val NO_KTP = "no_ktp"
    const val ACTIVITY_STATUS_QUEUE = "Status Antrian"
    const val BLOOD_GROUP = "blood_group"
    const val PLACE_OF_BIRTHDAY = "place_of_birthday"
    const val DATE_OF_BIRTH = "date_of_birthday"
    const val JOB_PATIENT  = "job_patient"
    const val STATUS_SINGLE = "status_single"
    const val NMR_BPJS = "nmr_bpjs"
    const val HOSPITAL_REFERRAL_NUMBER = "hospital_referral_number"
    const val CODE_NUMBER = "RM-00"
    const val ADD_PATIENT = "Tambah Pasien"
    const val EDIT_PATIENT = "Edit Data"
    const val PATIENT = "Pasien"
    const val DISPLAY_QUEUE = "Tampilan Antrian"
    const val PROCES = "Proses"

    // DATA FOR DOCTOR
    const val TABLE_NAME_DOCTOR = "table_name_doctor"
    const val NAME_OF_DOCTOR = "name_doctor"
    const val ADDRESS_DOCTOR = "address_doctor"
    const val NO_KTP_DOCTOR = "no_ktp_doctor"
    const val GENDER_DOCTOR = "gender_doctor"
    const val PLACE_OF_BIRTHDAY_DOCTOR = "place_of_birthday_doctor"
    const val DATE_OF_BIRTH_DOCTOR = "date_of_birth_doctor"
    const val STATUS_OF_SINGLE_BIRTH = "single_of_birth"
    const val SPECIALIST = "specialist"
    const val DOCTOR = "Dokter"
    const val ADD_DOCTOR = "Tambah Data Dokter"
    const val EDIT_DOCTOR = "Ubah Data Dokter"
    const val WAITING_QUEUE = "lama_menunggu"
    const val TIME_IN_THREE = "time_three"

    /* preferences for user login */
    private const val KEY_USERNAME = "username"
    private const val KEY_PASSWORD = "password"
    private const val KEY_LOGIN = "is_login"

    // DATA FOR LOGIN USER
    const val LOGIN_USER = "login_user"
    val USERNAME = stringPreferencesKey(KEY_USERNAME)
    val PASSWORD = stringPreferencesKey(KEY_PASSWORD)
    val IS_LOGIN = booleanPreferencesKey(KEY_LOGIN)
    const val USERNAME_LOGIN = "admin"
    const val PASSWORD_LOGIN = "123456"
    const val FORMAT24HOURS = "k:mm:ss a"
    const val FEMALE = "Perempuan"
    const val MALE = "Laki-Laki"
    const val TITLE_DIALOG_BLOOD_TYPE = "Pilih Golongan Darah"
    const val REGISTER_PATIENT = "Pendaftaran Pasien"
    const val WAITING_PAYMENT = "Menunggu Pembayaran"

    // Data Admisi Online
    const val TABLE_NAME_ADMISI = "admisi"
    const val QUEUE_NUMBER = "queue_number"
    const val STATUS_QUEUE = "status_queue"
    const val TIME_IN_QUEUE = "time_in_queue"
    const val TIME_CALL_QUEUE = "time_call_queue"
    const val COUNTER = "counter"
    const val POLI = "poli"
    const val DATE = "date"
    const val TIME_OF_DELAYED = 3000L
    const val TITLE_LOGIN = "login"
    const val RSU = "RSU Royal Prima"
    const val TITLE_HOME = "home"
    const val REGISTER_PATIENT_TEXT= "Daftar Pasien"

    // For Greeting Message
    const val GOOD_MORNING = "Selamat Pagi"
    const val GOOD_AFTERNOON = "Selamat Siang"
    const val GOOD_EVENING = "Selamat Sore"
    const val GOOD_NIGHT = "Selamat Malam"
    const val NOTHING_DATA = "Tidak Ada Data"

    //  DATA FOR PAYMENT\
    const val TABLE_PAYMENT = "table_payment"

    // BLOOD
    const val A = "A"
    const val B = "B"
    const val A_B = "AB"
    const val O = "O"

    // STATUS SINGLE
    const val MARRIED = "Kawin"
    const val NOT_MARRIED = "Belum Kawin"
    const val DISCOVERED = "Cerai"

    // DATA ALL QUEUE
    const val WAITING = "Menunggu Antrian"
    const val CANCEL = "Batal"
    const val DONE = "Selesai"
    const val CALL = "Panggil"
    const val CALL_AGAIN = "Panggil Ulang"
    const val STILL_QUEUE = "Masih Dalam Antrian"

    // COUNTER
    const val ONE = "1"
    const val TWO = "2"
    const val THREE = "3"

    // STATUS PATIENT
    const val PATIENT_BPJS = "Pasien BPJS"
    const val PATIENT_NON_BPJS = "Pasien Non BPJS"



}