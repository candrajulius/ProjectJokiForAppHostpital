package com.candra.rsu_royal_prima_app.ui.activity

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.candra.rsu_royal_prima_app.R
import com.candra.rsu_royal_prima_app.data.Admisi
import com.candra.rsu_royal_prima_app.databinding.ActivityRegisterBinding
import com.candra.rsu_royal_prima_app.databinding.RegisterPatientLayoutBinding
import com.candra.rsu_royal_prima_app.helper.Constant
import com.candra.rsu_royal_prima_app.helper.Utils
import com.candra.rsu_royal_prima_app.ui.adapter.AdapterRegisterPatient
import com.candra.rsu_royal_prima_app.ui.viewmodel.AdmisiViewModel
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val adapterRegister by lazy { AdapterRegisterPatient(::onDelete) }
    private val registerViewModel by viewModels<AdmisiViewModel>()
    private var selectedCounter = ""
    private var numberQueue: Int = 0
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Utils.setToolbar(Constant.RSU,Constant.REGISTER_PATIENT.uppercase(),supportActionBar,0)
        onClickAdd()
        setAdapter()
        observerAdmisi()
    }

    private fun observerAdmisi()
    {
        registerViewModel.showAllAdmisiPatient.observe(this@RegisterActivity,this::setUpList)
    }

    private fun setUpList(it: List<Admisi>)
    {
        binding.tvTotalRegisterPatient.text = it.size.toString()
        if (it.isEmpty())
        {
            binding.tvListEmpty.apply {
                text = getString(R.string.message_empty_list).uppercase()
                textAlignment = View.TEXT_ALIGNMENT_CENTER
            }
            binding.tvTotalRegisterPatient.visibility = View.GONE
            Utils.showTvEmpty(true,binding.tvListEmpty,binding.rvListRegister)
        }else{
            binding.tvTotalRegisterPatient.apply {
                text = it.size.toString()
                textAlignment = View.TEXT_ALIGNMENT_CENTER
                visibility = View.VISIBLE
            }
            adapterRegister.submitListData(it)
            Utils.showTvEmpty(false,binding.tvListEmpty,binding.rvListRegister)
        }
    }

    private fun setAdapter()
    {
        binding.rvListRegister.apply {
            layoutManager = LinearLayoutManager(this@RegisterActivity)
            adapter = adapterRegister
        }
    }

    private fun onDelete(data: Admisi)
    {
        with(data){
            lifecycleScope.launch(Dispatchers.IO){
                registerViewModel.deleteAdmisi(
                    Admisi(id, number_queue, status_queue, counter, time_in_queue, time_call_queue, time_waiting, time_three, nmr_bpjs, date, name_of_patient, poli, name_of_doctor)
                ).also {
                    lifecycleScope.launch(Dispatchers.Main){
                        adapterRegister.notifyItemRemoved(data.id)
                    }
                }
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun onClickAdd()
    {
        binding.btnAdd.setOnClickListener {
            numberQueue++
            showAddDialog()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showAddDialog()
    {
        val dialog = Dialog(this@RegisterActivity)
        val dialogBinding = RegisterPatientLayoutBinding.inflate(layoutInflater)
        dialogBinding.apply {
            tvNumberQueue.text = "A-$numberQueue"
            tvStatusQueue.text = Constant.STILL_QUEUE
            mtvTitleBtn.text = Constant.REGISTER_PATIENT_TEXT
            edtDate.setText(Utils.formatDate)
            val timeFormat = DateTimeFormatter.ofPattern(Utils.TIME_FORMAT)
            val timeStart = LocalDateTime.now().format(timeFormat)
            edtStartOclock.setText(timeStart)
            rbCounter1.isChecked = true
            tilWaitingList.visibility = View.GONE
            tilCallQueue.visibility = View.GONE
            btnClose.setOnClickListener {
                dialog.dismiss()
            }
            cardBtnConfirmation.setOnClickListener {

                val namePatient = tilInputName.editText?.text.toString()
                val numberBPJS = tilInputNumberBpjsPatient.editText?.text.toString()
                val statusQueue = tvStatusQueue.text.toString()
                val timeInStart = tilInputTimeStart.editText?.text.toString()
                val poly = tilInputPoly.editText?.text.toString()
                val nameDoctor = tilNameDoctor.editText?.text.toString()
                val date = tilInputDate.editText?.text.toString()
                val numberQueueText = tvNumberQueue.text.toString()
                selectedCounter = when(rgItemChoiceCounter.checkedRadioButtonId){
                    R.id.rb_counter_1 -> Constant.ONE
                    R.id.rb_counter_2 -> Constant.TWO
                    R.id.rb_counter_3 -> Constant.THREE
                    else -> Constant.NOTHING_DATA
                }

                if (namePatient.isEmpty() || statusQueue.isEmpty() || timeInStart.isEmpty() ||
                        poly.isEmpty() || nameDoctor.isEmpty() || date.isEmpty())
                {
                    Utils.makeToast(this@RegisterActivity,getString(R.string.toast_empty_data))
                }else{
                    val admisi = Admisi(0,numberQueueText,Constant.WAITING," $selectedCounter",
                        timeInStart,"0","0","0",numberBPJS,date,namePatient,poly,nameDoctor)
                    Log.d("TAG", "showAddDialog: $admisi").also { dialog.dismiss() }
                    lifecycleScope.launch { registerViewModel.insertAdmisi(admisi).also { dialog.dismiss() } }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
        {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}