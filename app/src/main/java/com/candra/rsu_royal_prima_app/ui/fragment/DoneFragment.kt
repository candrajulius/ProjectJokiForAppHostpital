package com.candra.rsu_royal_prima_app.ui.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

@AndroidEntryPoint
class DoneFragment : Fragment()
{
    private lateinit var binding: CallingQueueLayoutBinding
    private val adapterDone by lazy { AdapterStatusQueue(::onClick,Constant.DONE) }
    private val admisiViewModel by viewModels<AdmisiViewModel>()
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
        showAllListAdmisiWhereStatus()
    }

    private fun showAllListAdmisiWhereStatus()
    {
        admisiViewModel.showAllAdmisiWhereStatusQueue("%${Constant.DONE}%").observe(viewLifecycleOwner){
            setUpList(it)
        }
    }

    private fun setUpList(it: List<Admisi>)
    {
        binding.apply {
            if (it.isEmpty())
            {
                tvListEmpty.text = getString(R.string.message_empty_list).uppercase()
                Utils.showTvEmpty(true,tvListEmpty,rvStatusQueue)
            }else{
                adapterDone.submitListData(it)
                Utils.showTvEmpty(false,tvListEmpty,rvStatusQueue)
            }
        }
    }

    private fun onClick(data: Admisi)
    {
        showAddDialog(data)
    }

    private fun showAddDialog(data: Admisi)
    {
        val dialog = Dialog(requireActivity())
        val dialogBinding = RegisterPatientLayoutBinding.inflate(layoutInflater)
        dialogBinding.apply {
            tvStatusQueue.text = data.status_queue
            mtvTitleBtn.text = Constant.WAITING_PAYMENT
            cardBtnCancel.visibility = View.GONE
            cardBtnCallAgain.visibility = View.GONE
            edtName.setText(data.name_of_patient)
            tilInputNumberBpjsPatient.isEnabled = false
            edtNumberBpjsPatient.setText(data.nmr_bpjs)
            tvStatusQueue.text = data.status_queue
            edtStartOclock.setText(data.time_in_queue)
            edtWaitingList.setText(data.time_waiting)
            edtCallQueue.setText(data.time_call_queue)
            edtPoly.setText(data.poli)
            edtNameDoctor.setText(data.name_of_doctor)
            edtDate.setText(data.date)
            counter.text = "Loket ${data.counter}"
            rgItemChoiceCounter.visibility = View.GONE
            tilNameDoctor.isEnabled = false
            tilInputDate.isEnabled = false
            tilInputNumberBpjsPatient.isEnabled = false
            rgItemChoiceCounter.isEnabled = false
            edtNumberBpjsPatient.setText(data.nmr_bpjs)
            btnClose.setOnClickListener {dialog.dismiss()}
            mtvTitleBtn.text = Constant.DONE
            cardBtnConfirmation.setOnClickListener {
                with(data){
                    lifecycleScope.launch {
                        admisiViewModel.updateAdmisi(
                            Admisi(id, number_queue, Constant.WAITING_PAYMENT, counter, time_in_queue, time_call_queue, time_waiting, time_three, nmr_bpjs, date, name_of_patient, poli, name_of_doctor)
                        ).also {
                            admisiViewModel.insertPayment(
                                Payment(
                                    0, data.name_of_patient, data.number_queue, data.poli, data.nmr_bpjs
                                )
                            ).also {
                                dialog.dismiss()
                                adapterDone.notifyItemRemoved(id)
                            }
                        }
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


    private fun setAdapter()
    {
        binding.rvStatusQueue.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = adapterDone
        }
    }
}