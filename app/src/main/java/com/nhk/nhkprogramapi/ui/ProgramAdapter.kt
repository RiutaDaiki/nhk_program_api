package com.nhk.nhkprogramapi.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.nhk.nhkprogramapi.NhkViewModel
import com.nhk.nhkprogramapi.databinding.ItemRecyclerBinding
import kotlinx.android.synthetic.main.item_recycler.view.*

class ProgramAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: NhkViewModel
    ): RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder>() {
    private lateinit var binding: ItemRecyclerBinding

    inner class ProgramViewHolder(val binding: ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        binding = ItemRecyclerBinding.inflate(inflater, parent, false)
        binding.lifecycleOwner = lifecycleOwner
        return ProgramViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        val rowData = viewModel.programInfoList.value?.get(position)
        val programTime = rowData?.start?.substring(11..15) + " ~ " + rowData?.end?.substring(11..15)
        holder.binding.timeText.text = programTime
        holder.binding.titleText.text = rowData?.title

    }

    override fun getItemCount(): Int {
        return viewModel.programInfoList.value?.size ?: 0
    }
}