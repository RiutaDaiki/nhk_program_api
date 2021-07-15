package com.example.nhkprogramapi

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nhkprogramapi.databinding.ActivityMainBinding
import com.example.nhkprogramapi.ui.ProgramAdapter
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private val viewModel: NhkViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val date = LocalDate.now()
        binding.dateText.text = (date.monthValue + 1).toString()+ " / " + date.dayOfMonth

        binding.fab.setOnClickListener {
            com.example.nhkprogramapi.ui.BottomSheetDialog().show(supportFragmentManager, null)
        }

        viewModel.serviceId.observe(this){
            when(it){
                -1 -> Toast.makeText(this, "チャンネル選択せい", Toast.LENGTH_SHORT).show()
                else -> viewModel.getProgramTitle(localDate(), it)
            }
        }

        viewModel.programInfoList.observe(this){
            println("3")
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            val adapter = ProgramAdapter(this, viewModel)
            binding.recyclerView.adapter = adapter
            ProgramAdapter(this, viewModel).notifyDataSetChanged()
         }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun localDate(): String {
        return LocalDate.now().toString()
    }
}