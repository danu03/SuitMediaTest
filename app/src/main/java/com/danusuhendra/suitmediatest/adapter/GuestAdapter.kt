package com.danusuhendra.suitmediatest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.danusuhendra.suitmediatest.databinding.ItemGuestBinding
import com.danusuhendra.suitmediatest.model.response.Data

class GuestAdapter: RecyclerView.Adapter<GuestAdapter.ViewHolder>() {

    private val list = ArrayList<Data?>()
    private var itemCallback: OnItemClickCallback? = null

    fun setClickCallback(ItemClickCallback: OnItemClickCallback) {
        this.itemCallback = ItemClickCallback
    }

    fun setList(guest: List<Data?>) {
        list.clear()
        list.addAll(guest)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemGuestBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(guest: Data) {
            binding.root.setOnClickListener {
                itemCallback?.onItemClicked(guest)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(guest.avatar)
                    .centerCrop()
                    .into(ivGuest)
                tvNameGuest.text = guest.firstName + " " + guest.lastName
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemGuestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder((view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list[position]?.let { holder.bind(it) }

    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Data)
    }
}