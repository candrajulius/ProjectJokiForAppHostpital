package com.candra.rsu_royal_prima_app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.candra.rsu_royal_prima_app.data.Admisi
import com.candra.rsu_royal_prima_app.databinding.ItemCardDisplayBinding
import com.candra.rsu_royal_prima_app.helper.Constant


class AdapterDisplayAdmisi(
    private val onDelete: (Admisi) -> Unit
):RecyclerView.Adapter<AdapterDisplayAdmisi.ViewHolder>()
{
    inner class ViewHolder(
        private val binding: ItemCardDisplayBinding
    ): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(data: Admisi)
        {
            with(binding)
            {
                tvNumberQueue.text = "${data.number_queue}"
                tvStatusQueue.text = data.status_queue
                counter.text = "Loket ${data.counter}"
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
    ): AdapterDisplayAdmisi.ViewHolder {
        return ViewHolder(
            ItemCardDisplayBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: AdapterDisplayAdmisi.ViewHolder, position: Int) {
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