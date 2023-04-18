package com.candra.rsu_royal_prima_app.ui.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import java.time.Duration
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.candra.rsu_royal_prima_app.R
import com.candra.rsu_royal_prima_app.data.Admisi
import com.candra.rsu_royal_prima_app.data.Payment
import com.candra.rsu_royal_prima_app.databinding.CallingQueueLayoutBinding
import com.candra.rsu_royal_prima_app.databinding.RegisterPatientLayoutBinding
import com.candra.rsu_royal_prima_app.helper.Constant
import com.candra.rsu_royal_prima_app.helper.Utils
import com.candra.rsu_royal_prima_app.ui.adapter.AdapterStatusQueue
import com.candra.rsu_royal_prima_app.ui.viewmodel.AdmisiViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class WaitingFragment: Fragment(){
    private lateinit var binding: CallingQueueLayoutBinding
    private val adapterWaiting by lazy { AdapterStatusQueue(::onClick,Constant.WAITING)}
    private val waitingViewModel by viewModels<AdmisiViewModel>()
    private lateinit var timeFormat: DateTimeFormatter
    private var timeCall = "0"
    private lateinit var mTTS: TextToSpeech
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CallingQueueLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        showAllAdmisiWhereStatusQueue()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            timeFormat =  DateTimeFormatter.ofPattern(Utils.TIME_FORMAT)
        }
    }

    private fun showAllAdmisiWhereStatusQueue()
    {
        waitingViewModel.showAllAdmisiWhereStatusQueue("%${Constant.WAITING}%").observe(viewLifecycleOwner)
        {
          setUpList(it)
        }
    }
    @SuppressLint("SupportAnnotationUsage")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun onClick(data: Admisi)
    {
        with(data)
        {
            timeCall = LocalDateTime.now().format(timeFormat)
            lifecycleScope.launch {
                waitingViewModel.updateAdmisi(
                    Admisi(id, number_queue, status_queue, counter, time_in_queue, timeCall, time_waiting, time_three, nmr_bpjs, date, name_of_patient, poli, name_of_doctor)
                )
            }
        }

        val dialog = Dialog(requireActivity())
        val dialogBinding = RegisterPatientLayoutBinding.inflate(layoutInflater)
        dialogBinding.apply {
            tvStatusQueue.text = data.status_queue
            mtvTitleBtn.text = Constant.WAITING_PAYMENT
            cardBtnCancel.visibility = View.VISIBLE
            cardBtnCallAgain.visibility = View.VISIBLE
            edtName.setText(data.name_of_patient)
            edtNumberBpjsPatient.setText(data.nmr_bpjs)
            tvStatusQueue.text = data.status_queue
            edtStartOclock.setText(data.time_in_queue)
            edtWaitingList.setText(data.time_waiting)
            edtCallQueue.setText(data.time_call_queue)
            edtPoly.setText(data.poli)
            tilInputNumberBpjsPatient.isEnabled = false
            edtNumberBpjsPatient.setText(data.nmr_bpjs)
            edtDate.setText(data.date)
            tilNameDoctor.isEnabled = false
            tvNumberQueue.text = data.number_queue
            edtNameDoctor.setText(data.name_of_doctor)
            tilInputDate.isEnabled = false
            tilInputNumberBpjsPatient.isEnabled = false
            btnClose.setOnClickListener { dialog.dismiss() }
            counter.text = "Loket ${data.counter}"
            rgItemChoiceCounter.visibility = View.GONE

            mTTS = TextToSpeech(requireActivity()){ status ->
                if (status == TextToSpeech.SUCCESS)
                {
                    val result = mTTS.setLanguage(Locale.getDefault())
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                    {
                        cardBtnCallAgain.isEnabled = false
                        Log.d("TAG", "setCallingPatient: $result")
                    }else{
                        cardBtnCallAgain.isEnabled = true
                    }
                }
            }
            mtvTitleBtn.text = Constant.DONE
            mtvTitleCallAgain.text = Constant.CALL
            cardBtnCallAgain.setOnClickListener {
                setCallingPatient(mTTS, "${data.number_queue}",data.counter)
            }
            cardBtnCancel.setOnClickListener {
                with(data)
                {
                    val localTimeThree: String = LocalDateTime.now().format(timeFormat)
                    val localTimeInQueue = LocalTime.parse(data.time_in_queue)
                    val localTimeInCalling = LocalTime.parse(data.time_call_queue)
                    var differenceTime = Duration.between(localTimeInQueue,localTimeInCalling)
                    val hours: Long = differenceTime.toHours()
                    differenceTime = differenceTime.minusHours(hours)
                    val minutes: Long = differenceTime.toMinutes()
                    differenceTime = differenceTime.minusMinutes(minutes)
                    btnClose.setOnClickListener {dialog.dismiss()}
                    val seconds: Long = differenceTime.seconds
                    val timeWaitingList = "$hours jam : $minutes menit : $seconds detik"
                    lifecycleScope.launch {
                        waitingViewModel.updateAdmisi(Admisi(
                            id,number_queue, Constant.CANCEL, counter, time_in_queue,time_call_queue,timeWaitingList, localTimeThree, nmr_bpjs, date, name_of_patient, poli, name_of_doctor
                        )).also {
                            dialog.dismiss()
                            adapterWaiting.notifyItemRemoved(id)
                        }
                    }
                }
            }
            cardBtnConfirmation.setOnClickListener {
                val doneTime: String = LocalDateTime.now().format(timeFormat)
                val localTimeInQueue = LocalTime.parse(data.time_in_queue)
                val localCallTimeQueue= LocalTime.parse(data.time_call_queue)
                var differenceTime = Duration.between(localTimeInQueue,localCallTimeQueue)
                val hours: Long = differenceTime.toHours()
                differenceTime = differenceTime.minusHours(hours)
                val minutes: Long = differenceTime.toMinutes()
                differenceTime = differenceTime.minusMinutes(minutes)
                val seconds: Long = differenceTime.seconds

                val timeWaitingList = "$hours jam : $minutes menit : $seconds detik"
                with(data)
                {
                    lifecycleScope.launch {
                        waitingViewModel.updateAdmisi(
                            Admisi(id,number_queue,Constant.DONE,counter,time_in_queue,time_call_queue,timeWaitingList,doneTime,nmr_bpjs, date, name_of_patient, poli, name_of_doctor)
                        ).also {
                            dialog.dismiss()
                            adapterWaiting.notifyItemRemoved(id)
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

    private fun setCallingPatient(mTTs: TextToSpeech,numberQueuePatient: String,counter: String)
    {
        mTTs.speak("$numberQueuePatient silahkan ke loket $counter",TextToSpeech.QUEUE_FLUSH,null,null)
    }

    private fun setUpList(it: List<Admisi>)
    {
        binding.apply {
            if(it.isEmpty())
            {
                tvListEmpty.apply {
                    text = getString(R.string.message_empty_list).uppercase()
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                }
                Utils.showTvEmpty(true,tvListEmpty,rvStatusQueue)
            }else{
                adapterWaiting.submitListData(it)
                Utils.showTvEmpty(false,tvListEmpty,rvStatusQueue)
            }
        }
    }
    private fun setAdapter()
    {
      binding.rvStatusQueue.apply {
          layoutManager = LinearLayoutManager(requireActivity())
          adapter = adapterWaiting
      }
    }
}