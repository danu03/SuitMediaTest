package com.danusuhendra.suitmediatest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.danusuhendra.suitmediatest.databinding.ItemEventBinding
import com.danusuhendra.suitmediatest.model.Event

class EventAdapter: RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    private val list = ArrayList<Event?>()
    private var itemCallback: OnItemClickCallback? = null

    fun setClickCallback(ItemClickCallback: OnItemClickCallback) {
        this.itemCallback = ItemClickCallback
    }

    fun setList(event: List<Event?>) {
        list.clear()
        list.addAll(event)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            binding.root.setOnClickListener {
                itemCallback?.onItemClicked(event)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(event.image)
                    .centerCrop()
                    .into(ivEvent)
                titleEvent.text = event.name
                descEvent.text = event.desc
                dateEvent.text = event.date
                timeEvent.text = event.time
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder((view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list[position]?.let { holder.bind(it) }

    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Event)
    }
}