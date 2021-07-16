package com.example.nhkprogramapi.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.example.nhkprogramapi.R
import com.example.nhkprogramapi.databinding.DialogLiveInBinding

class LiveInDialog: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogLiveInBinding.inflate(layoutInflater)
//        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = ArrayAdapter<String>(
            this.requireContext(),
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.live_in_array)
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.liveInSpinner.adapter = adapter
        val builder = AlertDialog.Builder(context)
            .setView(binding.root)
            .setMessage("居住地を選択")
            .setPositiveButton("OK"){dialog, which ->

            }
            .setNegativeButton("キャンセル"){dialog, which ->
                dialog.dismiss()
            }

        return builder.create()
    }
}