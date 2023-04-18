package com.candra.rsu_royal_prima_app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.candra.rsu_royal_prima_app.data.Doctor
import com.candra.rsu_royal_prima_app.databinding.ItemCardDoctorBinding

class AdapterDoctor(
    private val onClick: (Doctor) -> Unit,
    private val onDelete: (Doctor) -> Unit
): RecyclerView.Adapter<AdapterDoctor.ViewHolder>()
{
    inner class ViewHolder(
        private val binding: ItemCardDoctorBinding
    ): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(data: Doctor){
            with(binding){
                tvNameDoctor.text = data.name_doctor
                tvSpecialistDoctor.text = data.specialist_doctor
                tvAddressDoctor.text = data.address_doctor
                tvNumberKtpDoctor.text  = data.no_ktp_doctor

                cardEdit.setOnClickListener {
                    onClick(data)
                }
                cardDelete.setOnClickListener {
                    onDelete(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterDoctor.ViewHolder {
        return ViewHolder(
            ItemCardDoctorBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: AdapterDoctor.ViewHolder, position: Int) {
        holder.bind(data = differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    val differ = AsyncListDiffer(this,object: DiffUtil.ItemCallback<Doctor>(){
        override fun areItemsTheSame(oldItem: Doctor, newItem: Doctor): Boolean =
           oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: Doctor, newItem: Doctor): Boolean  =
            oldItem == newItem

    })

    fun submitListData(item: List<Doctor>)
    {
        differ.submitList(item)
    }

}