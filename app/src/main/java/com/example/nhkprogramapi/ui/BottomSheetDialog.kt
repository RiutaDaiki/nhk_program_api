package com.example.nhkprogramapi.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.nhkprogramapi.NhkViewModel
import com.example.nhkprogramapi.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.example.nhkprogramapi.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDialog: BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetBinding
    private val viewModel: NhkViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet, container, false)
        binding.lifecycleOwner = this

        binding.cancelButton.setOnClickListener {
            this.dismiss()
        }

        binding.searchButton.setOnClickListener {
            val id = binding.segment.checkedRadioButtonId
            viewModel.serviceId.value = id
            println(id.toString())
            this.dismiss()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setDialogMatchParent()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(
            requireContext(),
            theme
        )
        dialog.setOnShowListener { dialog ->
            val bottomSheet = binding.designBottomSheet

            bottomSheet?.let{
                BottomSheetBehavior.from(bottomSheet).apply {
                    state = BottomSheetBehavior.STATE_COLLAPSED
                    skipCollapsed = false
                    isHideable = true
                }
            }
        }
        return dialog
    }

    private fun setDialogMatchParent() {
        dialog?.window?.let {
            val params = it.attributes
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height = ViewGroup.LayoutParams.MATCH_PARENT
            it.attributes = params
            it.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }
}