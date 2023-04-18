package com.candra.rsu_royal_prima_app.ui.activity

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.candra.rsu_royal_prima_app.R
import com.candra.rsu_royal_prima_app.data.Patient
import com.candra.rsu_royal_prima_app.databinding.ActivityPatientBinding
import com.candra.rsu_royal_prima_app.databinding.EditPatientLayoutBinding
import com.candra.rsu_royal_prima_app.helper.Constant
import com.candra.rsu_royal_prima_app.helper.Constant.CODE_NUMBER
import com.candra.rsu_royal_prima_app.helper.Utils
import com.candra.rsu_royal_prima_app.ui.adapter.AdapterPatient
import com.candra.rsu_royal_prima_app.ui.viewmodel.PatientViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PatientActivity : AppCompatActivity() {
    private val patientViewModel by viewModels<PatientViewModel>()
    private val patientAdapter by lazy { AdapterPatient(::onUpdate,::onDelete)}
    private var medisData = 0
    private lateinit var binding: ActivityPatientBinding
    private var selectedIndex = 0
    private val listGender = arrayOf(
        Constant.MALE,Constant.FEMALE
    )
    private val listStatus = arrayOf(
        Constant.MARRIED,Constant.NOT_MARRIED,Constant.DISCOVERED
    )
    private val listBloodType = arrayOf(
        Constant.A,Constant.B,Constant.A_B,Constant.O
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvListEmpty.text = getString(R.string.message_empty_list).uppercase()
        Utils.setToolbar(Constant.RSU,Constant.PATIENT,supportActionBar,0)
        onClickAddButton()
        setAdapterPatient()
        showAllDataPatient()
        binding.tvListEmpty.textAlignment = View.TEXT_ALIGNMENT_CENTER
    }

    private fun showAllDataPatient(){
        patientViewModel.showAllPatient.observe(this@PatientActivity,this::temptDataForShowAllPatient)
    }

    private fun temptDataForShowAllPatient(it: List<Patient>){
        binding.apply {
            if (it.isEmpty()){
                Utils.showTvEmpty(true,tvListEmpty,listPatient)
            }else{
                patientAdapter.submitListData(it)
                Utils.showTvEmpty(false,tvListEmpty,listPatient)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home)
        {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setAdapterPatient()
    {
       binding.listPatient.apply {
           layoutManager = LinearLayoutManager(this@PatientActivity)
           adapter = patientAdapter
       }
    }

    private fun onClickAddButton(){
        binding.btnAdd.setOnClickListener {
            showDialogAddPatient()
        }
    }

    private fun updatePatient(data: Patient) {
        val dialog = Dialog(this@PatientActivity)
        val dialogBinding = EditPatientLayoutBinding.inflate(layoutInflater)
        dialogBinding.apply {
            toolbarDetailPatient.apply {
                title = Constant.RSU
                subtitle = Constant.EDIT_PATIENT
                setNavigationOnClickListener {
                    dialog.dismiss()
                }
            }
            edtBloodType.setOnClickListener { messageBloodTypePatient(edtBloodType) }
            edtGender.setOnClickListener {  messageGenderPatient(edtGender) }
            edtStatusPatient.setOnClickListener { messageStatusPatient(edtStatusPatient) }
            edtNamePatient.setText(data.name_patient)
            edtJobPatient.setText(data.job_patient)
            edtDateOfBirthdayPatient.setText(data.date_of_birth)
            edtNumberBpjsPatient.setText(data.nmr_bpjs_patient)
            edtPlaceOfBirthdayPatient.setText(data.place_of_birthday_patient)
            edtAddressPatient.setText(data.address_patient)
            edtNumberKtpPatient.setText(data.no_ktp)
            edtTelephonePatient.setText(data.telephone_patient)
            edtGender.setText(data.gender_patient)
            edtStatusPatient.setText(data.status_single)
            edtBloodType.setText(data.blood_group_patient)

            btnAddPatient.text = Constant.ADD_PATIENT

            Utils.showCalendar(edtDateOfBirthdayPatient,this@PatientActivity)

            btnAddPatient.text = Constant.EDIT_PATIENT
            btnAddPatient.setOnClickListener {
                val namePatient = tilNamePatient.editText?.text.toString().lowercase()
                val addressPatient = tilAddressPatient.editText?.text.toString().lowercase()
                val telephonePatient = tilTelephonePatient.editText?.text.toString().lowercase()
                val numberKtpPatient = tilNumberKtpPatient.editText?.text.toString().lowercase()
                val placeOfBirthdayPatient = tilPlaceOfBirthdayPatient.editText?.text.toString().lowercase()
                val dateOfBirthday = tilDateOfBirthdayPatient.editText?.text.toString()
                val jobPatient = jobPatient.editText?.text.toString()
                val numberBPJSPatient = tilInputNumberBpjsPatient.editText?.text.toString().lowercase()
                val statusPatient = tilStatusPatient.editText?.text.toString().lowercase()
                val genderPatient = tilGender.editText?.text.toString().lowercase()
                val bloodTypePatient = tilBloodType.editText?.text.toString().lowercase()

                if (namePatient.isEmpty() && addressPatient.isEmpty() && telephonePatient.isEmpty() &&
                    numberKtpPatient.isEmpty() && placeOfBirthdayPatient.isEmpty() && dateOfBirthday.isEmpty()
                    && jobPatient.isEmpty() && numberBPJSPatient.isEmpty()
                ) {
                    Utils.makeToast(this@PatientActivity, getString(R.string.failed_add_toast))
                } else {
                    val patient = Patient(data.id,namePatient,addressPatient,telephonePatient,genderPatient,
                        numberKtpPatient,bloodTypePatient,placeOfBirthdayPatient,dateOfBirthday,jobPatient,statusPatient,
                        numberBPJSPatient,data.hospital_refereal_number)
                    Log.d("TAG", "updatePatient: $patient")
                    lifecycleScope.launch {
                        patientViewModel.updatePatient(patient).also {
                            dialog.dismiss()
                            Utils.makeToast(
                                this@PatientActivity,
                                getString(R.string.successfully_add_toast,"Diubah")
                            )
                        }
                    }
                }
            }
        }
        dialog.apply {
            setContentView(dialogBinding.root)
            show()
            window?.apply {
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                attributes.windowAnimations = R.style.DialogAnimation
                setGravity(Gravity.BOTTOM)
            }
        }
    }

    private fun showDialogAddPatient()
    {
        val dialog = Dialog(this@PatientActivity)
        val dialogBinding = EditPatientLayoutBinding.inflate(layoutInflater)
        dialogBinding.apply { 
            toolbarDetailPatient.apply {
                title = Constant.RSU
                subtitle = Constant.ADD_PATIENT
                setNavigationOnClickListener {
                    dialog.dismiss()
                }
            }
            edtBloodType.setOnClickListener { messageBloodTypePatient(edtBloodType) }
            edtGender.setOnClickListener {  messageGenderPatient(edtGender) }
            edtStatusPatient.setOnClickListener { messageStatusPatient(edtStatusPatient) }
            btnAddPatient.text = Constant.ADD_PATIENT

            Utils.showCalendar(edtDateOfBirthdayPatient,this@PatientActivity)
            btnAddPatient.setOnClickListener {
                val namePatient = tilNamePatient.editText?.text.toString().lowercase()
                val addressPatient = tilAddressPatient.editText?.text.toString().lowercase()
                val telephonePatient = tilTelephonePatient.editText?.text.toString().lowercase()
                val placeOfBirthdayPatient = tilPlaceOfBirthdayPatient.editText?.text.toString().lowercase()
                val dateOfBirthday = tilDateOfBirthdayPatient.editText?.text.toString().lowercase()
                val jobPatient = jobPatient.editText?.text.toString().lowercase()
                val numberBPJSPatient = tilInputNumberBpjsPatient.editText?.text.toString().lowercase()
                val numberKtpPatient = tilNumberKtpPatient.editText?.text.toString().lowercase()
                val statusPatient = tilStatusPatient.editText?.text.toString().lowercase()
                val genderPatient = tilGender.editText?.text.toString().lowercase()
                val bloodTypePatient = tilBloodType.editText?.text.toString().lowercase()
                if (namePatient.isEmpty() || addressPatient.isEmpty() || telephonePatient.isEmpty() ||
                    numberKtpPatient.isEmpty() || placeOfBirthdayPatient.isEmpty() || dateOfBirthday.isEmpty()
                    || jobPatient.isEmpty() ||  numberBPJSPatient.isEmpty() || statusPatient.isEmpty() || genderPatient.isEmpty()
                    || bloodTypePatient.isEmpty())
                {
//                    Log.d("TAG", "showDialogAddPatient: $namePatient,$addressPatient,$telephonePatient," +
//                            "$numberKtpPatient,$placeOfBirthdayPatient,$dateOfBirthday,$jobPatient,$numberBPJSPatient," +
//                            "$statusPatient,$genderPatient,$bloodTypePatient")
                    Utils.makeToast(this@PatientActivity, getString(R.string.failed_add_toast))
                } else {
                    val patient = Patient(0,namePatient,addressPatient,telephonePatient,genderPatient,
                        numberKtpPatient,bloodTypePatient,placeOfBirthdayPatient,dateOfBirthday,jobPatient,statusPatient,
                        numberBPJSPatient,"${CODE_NUMBER}${medisData++}")
//                    Log.d("TAG", "readDataFromDatabase: $patient")
                    lifecycleScope.launch {
                        patientViewModel.insertPatient(patient).also {
                            dialog.dismiss()
                            Utils.makeToast(
                                this@PatientActivity,
                                getString(R.string.successfully_add_toast,"Ditambahkan")
                            )
                        }
                    }
                }
            }
        }
        dialog.apply {
            setContentView(dialogBinding.root)
            show()
            window?.apply {
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                attributes.windowAnimations = R.style.DialogAnimation
                setGravity(Gravity.BOTTOM)
            }
        }

        
    }

    private fun messageStatusPatient(edtStatusDoctor: TextInputEditText)
    {
        var statusList = listStatus[selectedIndex]
        MaterialAlertDialogBuilder(this)
            .setTitle("Pilih Status Pasien")
            .setSingleChoiceItems(listStatus,selectedIndex){_, which ->
                selectedIndex = which
                statusList = listStatus[which]
            }
            .setPositiveButton(getString(R.string.ok)){dialog,_ ->
                edtStatusDoctor.setText(statusList)
                dialog.dismiss()
            }
            .setNeutralButton(getString(R.string.cancel)){ dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun messageGenderPatient(edtGender: TextInputEditText)
    {
        var genderList = listGender[selectedIndex]
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.gender))
            .setSingleChoiceItems(listGender,selectedIndex){_, which ->
                selectedIndex = which
                genderList = listGender[which]
            }
            .setPositiveButton(getString(R.string.ok)){dialog,_ ->
                edtGender.setText(genderList)
                dialog.dismiss()
            }
            .setNeutralButton(getString(R.string.cancel)){ dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun messageBloodTypePatient(edtBloodType: TextInputEditText)
    {
        var bloodList = listBloodType[selectedIndex]
        MaterialAlertDialogBuilder(this)
            .setTitle(Constant.TITLE_DIALOG_BLOOD_TYPE)
            .setSingleChoiceItems(listBloodType,selectedIndex){_, which ->
                selectedIndex = which
                bloodList = listBloodType[which]
            }
            .setPositiveButton(getString(R.string.ok)){dialog,_ ->
                edtBloodType.setText(bloodList)
                dialog.dismiss()
            }
            .setNeutralButton(getString(R.string.cancel)){ dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun onUpdate(data: Patient)
    {
        updatePatient(data)
    }

    private fun onDelete(data: Patient)
    {
        onDeleteDataPatient(data)
    }

    private fun onDeleteDataPatient(data: Patient){
        val builder = AlertDialog.Builder(this@PatientActivity)
        builder.apply {
            setPositiveButton(getString(R.string.yes)){_,_ ->
                lifecycleScope.launch {
                    patientViewModel.deletePatient(patient = data)
                    patientAdapter.notifyItemRemoved(data.id)
                    Utils.makeToast(this@PatientActivity,getString(R.string.toast_delete_success,data.name_patient))
                }
            }
            setNegativeButton(getString(R.string.no)){_,_ ->
                Utils.makeToast(this@PatientActivity,getString(R.string.toast_delete_failed,data.name_patient))
            }
            setTitle(getString(R.string.title_dialog,data.name_patient))
            setMessage(getString(R.string.message_dialog,data.name_patient))
            create().show()
        }
    }


}