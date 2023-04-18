package com.candra.rsu_royal_prima_app.ui.activity

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.candra.rsu_royal_prima_app.R
import com.candra.rsu_royal_prima_app.data.Doctor
import com.candra.rsu_royal_prima_app.databinding.ActivityDoctorBinding
import com.candra.rsu_royal_prima_app.databinding.EditDoctorLayoutBinding
import com.candra.rsu_royal_prima_app.helper.Constant
import com.candra.rsu_royal_prima_app.helper.Utils
import com.candra.rsu_royal_prima_app.ui.adapter.AdapterDoctor
import com.candra.rsu_royal_prima_app.ui.viewmodel.DoctorViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DoctorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorBinding
    private val doctorViewModel by viewModels<DoctorViewModel>()
    private val adapterDoctor by lazy { AdapterDoctor(::onUpdate,::onDelete) }
    private var selectedIndex = 0
    private val listGender = arrayOf(
        Constant.MALE,Constant.FEMALE
    )
    private val listStatus = arrayOf(
        Constant.MARRIED,Constant.NOT_MARRIED,Constant.DISCOVERED
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Utils.setToolbar(Constant.RSU,Constant.DOCTOR,supportActionBar,0)
        actionButtonAdd()
        setAdapter()
        observerOnLiveDataListDoctor()
        binding.tvListEmpty.apply {
            text = getString(R.string.message_empty_list).uppercase()
            textAlignment = View.TEXT_ALIGNMENT_CENTER
        }
    }

    private fun observerOnLiveDataListDoctor()
    {
        doctorViewModel.showAllDoctor.observe(this@DoctorActivity,this::setShowList)
    }

    private fun setShowList(item: List<Doctor>)
    {
        with(binding)
        {
            if(item.isEmpty()){
                Utils.showTvEmpty(true,tvListEmpty,listDoctor)
            }else{
                adapterDoctor.submitListData(item)
                Utils.showTvEmpty(false,tvListEmpty,listDoctor)
            }
        }

    }

    private fun setAdapter(){
        binding.listDoctor.apply {
            layoutManager = LinearLayoutManager(this@DoctorActivity)
            adapter = adapterDoctor
        }
    }

    private fun onUpdate(data: Doctor) {
        showDialogUpdateDoctor(data)
    }

    private fun messageDialogGender(edtGender: TextInputEditText)
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

    private fun actionButtonAdd(){
        binding.btnAddDoctor.setOnClickListener {
            showAddDialogDoctor()
        }
    }

    private fun onDelete(data: Doctor)
    {
        showDialogDeleteDoctor(data)
    }

    private fun showDialogDeleteDoctor(data: Doctor){
        val builder = AlertDialog.Builder(this@DoctorActivity)
        builder.apply {
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                lifecycleScope.launch {
                    doctorViewModel.deleteDoctor(data)
                    adapterDoctor.notifyItemRemoved(data.id)
                    Utils.makeToast(
                        this@DoctorActivity,
                        getString(R.string.toast_delete_success, data.name_doctor)
                    )
                }
            }
            setNegativeButton(getString(R.string.no)) { _, _ ->
                Utils.makeToast(this@DoctorActivity, getString(R.string.toast_delete_failed))
            }
            setTitle(getString(R.string.title_dialog, data.name_doctor))
            setMessage(getString(R.string.message_dialog, data.name_doctor))
            create().show()
        }
    }

    private fun showAddDialogDoctor()
    {
        val dialog = Dialog(this@DoctorActivity)
        val dialogBinding = EditDoctorLayoutBinding.inflate(layoutInflater)
        dialogBinding.apply {
            toolbarDetailDoctor.apply {
                title = Constant.RSU
                subtitle = Constant.ADD_DOCTOR
                setNavigationOnClickListener { dialog.dismiss() }
            }

            edtGenderDoctor.setOnClickListener { messageDialogGender(edtGenderDoctor) }
            edtSingleDoctor.setOnClickListener { messageStatusDoctor(edtSingleDoctor) }

            Utils.showCalendar(edtDateOfBirth,this@DoctorActivity)

            mtvTitleBtn.text = Constant.ADD_DOCTOR
            cardBtnConfirmation.setOnClickListener {
                val nameDoctor = tilInputName.editText?.text.toString().lowercase()
                val specialistDoctor = tilInputSpesialis.editText?.text.toString().lowercase()
                val addressDoctor = tilInputAddressDoctor.editText?.text.toString().lowercase()
                val numberKTPDoctor = tilInputNumberKtpDoctor.editText?.text.toString().lowercase()
                val genderDoctor = tilInputGenderDoctor.editText?.text.toString().lowercase()
                val placeBirthDoctor = tilInputPlaceOfBirthdayDoctor.editText?.text.toString().lowercase()
                val dateOfBirth = tilInputDateOfBirth.editText?.text.toString().lowercase()
                val singleDoctor = tilSingleDoctor.editText?.text.toString().lowercase()
                if (nameDoctor.isEmpty() && specialistDoctor.isEmpty() && addressDoctor.isEmpty() && numberKTPDoctor.isEmpty()
                    && genderDoctor.isEmpty() && placeBirthDoctor.isEmpty() && dateOfBirth.isEmpty() && singleDoctor.isEmpty())
                {
                    Utils.makeToast(this@DoctorActivity,getString(R.string.failed_add_toast))
                    dialog.dismiss()
                }else{
                    val modelDoctor = Doctor(0,nameDoctor,addressDoctor,specialistDoctor,numberKTPDoctor,
                        genderDoctor,placeBirthDoctor,dateOfBirth,singleDoctor)
                        lifecycleScope.launch {
                        doctorViewModel.insert(modelDoctor).also { dialog.dismiss() }
                        Utils.makeToast(this@DoctorActivity,getString(R.string.successfully_add_toast,"Ditambahkan"))
                    }
                }
            }
        }
        dialog.apply {
            setContentView(dialogBinding.root)
            show()
            window?.apply {
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                attributes.windowAnimations = R.style.DialogAnimation
                setGravity(Gravity.BOTTOM)
            }
        }
    }

    private fun showDialogUpdateDoctor(data: Doctor)
    {
        val dialog = Dialog(this@DoctorActivity)
        val dialogBinding = EditDoctorLayoutBinding.inflate(layoutInflater)
        dialogBinding.apply {
            toolbarDetailDoctor.apply {
                title = Constant.RSU
                subtitle = Constant.EDIT_DOCTOR
                setNavigationOnClickListener { dialog.dismiss() }
            }
            edtName.setText(data.name_doctor)
            edtSpesialis.setText(data.specialist_doctor)
            edtAddressDoctor.setText(data.address_doctor)
            edtNumberKtpDoctor.setText(data.no_ktp_doctor)
            edtGenderDoctor.setText(data.gender_doctor)
            edtPlaceOfBirthdayDoctor.setText(data.place_of_birhtday_doctor)
            edtDateOfBirth.setText(data.date_of_birth_doctor)
            edtSingleDoctor.setText(data.status_of_single_birth)
            mtvTitleBtn.text = Constant.EDIT_DOCTOR
            cardBtnConfirmation.setOnClickListener {
                val nameDoctor = tilInputName.editText?.text.toString().lowercase()
                val specialistDoctor = tilInputSpesialis.editText?.text.toString().lowercase()
                val addressDoctor = tilInputAddressDoctor.editText?.text.toString().lowercase()
                val numberKTPDoctor = tilInputNumberKtpDoctor.editText?.text.toString().lowercase()
                val genderDoctor = tilInputGenderDoctor.editText?.text.toString().lowercase()
                edtGenderDoctor.setOnClickListener { messageDialogGender(edtGenderDoctor) }
                edtSingleDoctor.setOnClickListener { messageStatusDoctor(edtSingleDoctor) }
                val placeBirthDoctor = tilInputPlaceOfBirthdayDoctor.editText?.text.toString().lowercase()
                val dateOfBirth = tilInputDateOfBirth.editText?.text.toString().lowercase()
                val singleDoctor = tilSingleDoctor.editText?.text.toString().lowercase()
                Utils.showCalendar(edtDateOfBirth,this@DoctorActivity)

                val modelDoctor = Doctor(data.id,nameDoctor,addressDoctor,specialistDoctor,numberKTPDoctor,
                    genderDoctor,placeBirthDoctor,dateOfBirth,singleDoctor)
                if (nameDoctor.isEmpty() && specialistDoctor.isEmpty() && addressDoctor.isEmpty() && numberKTPDoctor.isEmpty()
                    && genderDoctor.isEmpty() && placeBirthDoctor.isEmpty() && dateOfBirth.isEmpty() && singleDoctor.isEmpty())
                {
                    Log.d("TAG", "showDialogUpdateDoctor: $modelDoctor")
                    Utils.makeToast(this@DoctorActivity,getString(R.string.failed_add_toast))
                    dialog.dismiss()
                }else{
                    Log.d("TAG", "showDialogUpdateDoctor: $modelDoctor")
                    lifecycleScope.launch {
                        doctorViewModel.updateDoctor(modelDoctor).also { dialog.dismiss() }
                        Utils.makeToast(this@DoctorActivity,getString(R.string.successfully_add_toast,"Diubah"))
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

    private fun messageStatusDoctor(edtStatusDoctor: TextInputEditText)
    {
        var statusList = listStatus[selectedIndex]
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.gender))
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home)
        {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}