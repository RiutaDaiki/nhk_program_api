package com.nhk.nhkprogramapi.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.nhk.nhkprogramapi.NhkViewModel
import com.nhk.nhkprogramapi.databinding.ItemRecyclerBinding

class ProgramAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: NhkViewModel
    ): RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder>() {
    private lateinit var binding: ItemRecyclerBinding

    inner class ProgramViewHolder(val binding: ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.cardView.setOnClickListener {
                binding.expandable.toggle()
            }
        }

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
        if (rowData?.subtitle != "　") holder.binding.expandText.text = rowData?.subtitle
        else holder.binding.expandText.text = "放送休止"
    }

    override fun getItemCount(): Int {
        return viewModel.programInfoList.value?.size ?: 0
    }
}