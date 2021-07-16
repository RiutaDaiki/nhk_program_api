package com.example.nhkprogramapi.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.nhkprogramapi.NhkViewModel
import com.example.nhkprogramapi.R
import com.example.nhkprogramapi.databinding.DialogLiveInBinding

class LiveInDialog : DialogFragment() {
    private val viewModel: NhkViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogLiveInBinding.inflate(layoutInflater)

        val adapter = ArrayAdapter<String>(
            this.requireContext(),
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.live_in_array)
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.liveInSpinner.adapter = adapter
        binding.liveInSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val spinnerParent = parent as Spinner
                val item = spinnerParent.selectedItem as String
                println(item)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        val builder = AlertDialog.Builder(context)
            .setView(binding.root)
            .setMessage("居住地を選択")
            .setPositiveButton("OK") { dialog, which ->

            }
            .setNegativeButton("キャンセル") { dialog, which ->
                dialog.dismiss()
            }

        return builder.create()
    }
}