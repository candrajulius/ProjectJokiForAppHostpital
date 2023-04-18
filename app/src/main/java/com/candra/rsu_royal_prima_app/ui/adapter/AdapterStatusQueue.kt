package com.candra.rsu_royal_prima_app.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.candra.rsu_royal_prima_app.data.Admisi
import com.candra.rsu_royal_prima_app.databinding.ItemCardQueueLayoutBinding
import com.candra.rsu_royal_prima_app.helper.Constant

class AdapterStatusQueue(
    private val onClick: (Admisi) -> Unit,
    private val statusQueue: String
): RecyclerView.Adapter<AdapterStatusQueue.ViewHolder>()
{
    inner class ViewHolder(
        private val binding: ItemCardQueueLayoutBinding
    ): RecyclerView.ViewHolder(binding.root)
    {
        @SuppressLint("SetTextI18n")
        fun bind(data: Admisi)
        {
            with(binding)
            {
                when(statusQueue)
                {
                    Constant.WAITING -> {

                        tvCounter.visibility = View.GONE
                        tvNumberQueue.text = "Nomor Antrian : ${data.number_queue}"
                        tvNumberQueue.visibility = View.VISIBLE
                        tvQueueCreate.visibility = View.VISIBLE
                        tvQueueCreate.text = "Antrian Diambil Pada :${data.date} ${data.time_in_queue}"
                        tvCallQueue.visibility = View.GONE
                        mtvTitleBtn.text = Constant.CALL
                        tvThreeCallQueue.visibility = View.GONE
                    }
                    Constant.DONE -> {
                        mtvTitleBtn.text = Constant.PROCES
                        tvCounter.visibility = View.VISIBLE
                        tvCounter.text = data.number_queue
                        tvQueueCreate.visibility = View.VISIBLE
                        tvQueueCreate.text = "Antrian Diambil Pada: ${data.date} ${data.time_in_queue}"
                        tvCallQueue.visibility = View.VISIBLE
                        tvCallQueue.text = "Antrian Dipanggil Pada: ${data.date} ${data.time_call_queue}"
                        tvThreeCallQueue.visibility = View.VISIBLE
                        tvThreeCallQueue.text = "Antrian Selesai Pada: ${data.date} ${data.time_three}"
                    }
                    Constant.CANCEL -> {
                        tvCounter.visibility = View.VISIBLE
                        tvCounter.text = data.number_queue
                        tvQueueCreate.visibility = View.VISIBLE
                        tvQueueCreate.text = "Antrian Diambil Pada: ${data.date} ${data.time_in_queue}"
                        tvCallQueue.visibility = View.VISIBLE
                        tvCallQueue.text = "Antrian Dipanggil Pada: ${data.date} ${data.time_call_queue}"
                        tvThreeCallQueue.visibility = View.VISIBLE
                        tvThreeCallQueue.text = "Antrian Dibatalkan Pada: ${data.date} ${data.time_three}"
                    }
                }
                cardBtnConfirmation.setOnClickListener {
                    onClick(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterStatusQueue.ViewHolder = ViewHolder(ItemCardQueueLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: AdapterStatusQueue.ViewHolder, position: Int) {
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