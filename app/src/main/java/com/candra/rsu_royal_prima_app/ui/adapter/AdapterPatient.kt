package com.candra.rsu_royal_prima_app.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.candra.rsu_royal_prima_app.data.Patient
import com.candra.rsu_royal_prima_app.databinding.ItemCardPatientBinding
import com.candra.rsu_royal_prima_app.helper.Constant
import com.candra.rsu_royal_prima_app.helper.Constant.CODE_NUMBER

class AdapterPatient(
    private val onClick: (Patient) -> Unit,
    private val onDelete: (Patient) -> Unit
) : RecyclerView.Adapter<AdapterPatient.ViewHolder>()
{
    inner class ViewHolder(
        private val binding: ItemCardPatientBinding
    ): RecyclerView.ViewHolder(
        binding.root
    )
    {
        @SuppressLint("SetTextI18n")
        fun bind(data: Patient)
        {
            with(binding){
                tvNamePatient.text = data.name_patient
                tvAddressPatient.text = data.address_patient
                tvNumberBpjsPatient.text = data.nmr_bpjs_patient
                tvBloodGroupPatient.text = data.blood_group_patient
                tvDateOfBirth.text = data.date_of_birth
                tvGenderPatient.text = data.gender_patient
                tvJobPatient.text = data.job_patient
                tvPlaceOfBirthdayPatient.text = data.place_of_birthday_patient
                tvStatusPatient.text = data.status_single
                tvTelephonePatient.text = data.telephone_patient
                tvNumberKtpPatient.text = data.no_ktp
                cardEdit.setOnClickListener {
                    onClick(data)
                }
                cardDelete.setOnClickListener {
                    onDelete(data)
                }
            }
        }
    }

    val differ = AsyncListDiffer(this,object: DiffUtil.ItemCallback<Patient>(){
        override fun areItemsTheSame(oldItem: Patient, newItem: Patient): Boolean =
            oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Patient, newItem: Patient): Boolean =
            oldItem == newItem
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPatient.ViewHolder =
        ViewHolder(ItemCardPatientBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: AdapterPatient.ViewHolder, position: Int){
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitListData(item: List<Patient>){
        differ.submitList(item)
    }

}