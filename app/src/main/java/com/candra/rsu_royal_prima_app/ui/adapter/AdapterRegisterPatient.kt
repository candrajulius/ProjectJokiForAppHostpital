package com.candra.rsu_royal_prima_app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.candra.rsu_royal_prima_app.data.Admisi
import com.candra.rsu_royal_prima_app.databinding.ItemCardRegisterBinding
import com.candra.rsu_royal_prima_app.helper.Constant

class AdapterRegisterPatient(
    private val onDelete: (data: Admisi) -> Unit,
): RecyclerView.Adapter<AdapterRegisterPatient.ViewHolder>()
{
    inner class ViewHolder(
        private val binding: ItemCardRegisterBinding
    ): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(data: Admisi)
        {
            with(binding)
            {
                tvNamePatient.text = data.name_of_patient
                tvStatusQueue.text = data.status_queue
                tvPolyPatient.text = data.poli
                tvNumberBpjsPatient.text = data.nmr_bpjs
                if (data.status_queue == Constant.WAITING_PAYMENT)
                {
                    onDelete(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterRegisterPatient.ViewHolder  =
        ViewHolder(ItemCardRegisterBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: AdapterRegisterPatient.ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    private val differ = AsyncListDiffer(this,object: DiffUtil.ItemCallback<Admisi>(){
        override fun areItemsTheSame(oldItem: Admisi, newItem: Admisi): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Admisi, newItem: Admisi): Boolean =
            oldItem == newItem

    })

    override fun getItemCount(): Int = differ.currentList.size

    fun submitListData(it: List<Admisi>)
    {
        differ.submitList(it)
    }

}