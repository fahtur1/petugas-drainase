package com.pcr.drainit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pcr.drainit.databinding.ItemListRiwayatBinding
import com.pcr.drainit.model.enitity.Pengaduan
import com.pcr.drainit.ui.riwayat.RiwayatViewModel


class RecyclerViewRiwayatAdapter(
    private val riwayatViewModel: RiwayatViewModel
) : RecyclerView.Adapter<RecyclerViewRiwayatAdapter.ViewHolder>() {

    var listPengaduan: List<Pengaduan>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemListRiwayatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, itemPosition: Int) {
        val currentItem = listPengaduan?.get(itemPosition)

        holder.binding.apply {
            viewModel = riwayatViewModel
            position = itemPosition
            data = currentItem
        }
    }

    override fun getItemCount(): Int = listPengaduan?.size ?: 0

    inner class ViewHolder(val binding: ItemListRiwayatBinding) :
        RecyclerView.ViewHolder(binding.root)

}