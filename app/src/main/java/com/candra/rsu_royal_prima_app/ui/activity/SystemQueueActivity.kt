package com.candra.rsu_royal_prima_app.ui.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.candra.rsu_royal_prima_app.R
import com.candra.rsu_royal_prima_app.data.Admisi
import com.candra.rsu_royal_prima_app.databinding.ActivitySystemQueueBinding
import com.candra.rsu_royal_prima_app.databinding.ContainerDisplayBinding
import com.candra.rsu_royal_prima_app.helper.Constant
import com.candra.rsu_royal_prima_app.helper.Utils
import com.candra.rsu_royal_prima_app.ui.adapter.AdapterDisplayAdmisi
import com.candra.rsu_royal_prima_app.ui.viewmodel.AdmisiViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SystemQueueActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySystemQueueBinding
    private val adapterAdmisi by lazy { AdapterDisplayAdmisi(::onDelete) }
    private val admisiViewModel by viewModels<AdmisiViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySystemQueueBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Utils.setToolbar(Constant.RSU,getString(R.string.system_queuing_online),supportActionBar,0)
        setOnClickListenerContainer()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if  (item.itemId == android.R.id.home)
        {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setOnClickListenerContainer(){
        binding.apply {
            containerRegisterPatient.setOnClickListener {
                startActivity(Intent(this@SystemQueueActivity,RegisterActivity::class.java))
                    .also { finish() }
            }
            containerDisplayQueue.setOnClickListener {
                showDialogListAdmisiDisplay()
            }
            containerStatusQueue.setOnClickListener {
                startActivity(Intent(this@SystemQueueActivity,StatusQueue::class.java))
                    .also { finish() }
            }
        }
    }

    private fun onDelete(data: Admisi)
    {
        with(data){
            lifecycleScope.launch(Dispatchers.IO){
                admisiViewModel.deleteAdmisi(
                    Admisi(id, number_queue, status_queue, counter, time_in_queue, time_call_queue, time_waiting, time_three, nmr_bpjs, date, name_of_patient, poli, name_of_doctor)
                ).also {
                    lifecycleScope.launch(Dispatchers.Main){
                        adapterAdmisi.notifyItemRemoved(data.id)
                    }
                }
            }
        }
    }

    private fun showDialogListAdmisiDisplay()
    {
        val dialog = Dialog(this@SystemQueueActivity)
        val dialogBinding = ContainerDisplayBinding.inflate(layoutInflater)
        dialogBinding.apply {
            rvDisplay.apply {
                layoutManager = LinearLayoutManager(this@SystemQueueActivity)
                adapter = adapterAdmisi
            }
            toolbarDisplay.apply {
                title = Constant.RSU
                subtitle = Constant.DISPLAY_QUEUE
                setNavigationOnClickListener {
                    dialog.dismiss()
                }
            }
            admisiViewModel.showAllAdmisiPatient.observe(this@SystemQueueActivity){
                if (it.isEmpty())
                {
                    tvListEmpty.text = getString(R.string.message_empty_list).uppercase()
                    Utils.showTvEmpty(true,tvListEmpty,rvDisplay)
                }else{
                    adapterAdmisi.submitListData(it)
                    Utils.showTvEmpty(false,tvListEmpty,rvDisplay)
                }
            }
        }
        dialog.apply {
            setContentView(dialogBinding.root)
            show()
            window?.apply {
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                setBackgroundDrawable(ColorDrawable(Color.WHITE))
                attributes.windowAnimations = R.style.DialogAnimation
                setGravity(Gravity.BOTTOM)
            }
        }
    }
}