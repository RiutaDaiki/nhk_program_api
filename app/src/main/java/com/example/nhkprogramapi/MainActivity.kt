package com.example.nhkprogramapi

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nhkprogramapi.databinding.ActivityMainBinding
import com.example.nhkprogramapi.ui.ProgramAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private val viewModel: NhkViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val dayOfWeek = mapOf<String, String>(
            "MONDAY" to "月",
            "TUESDAY" to "火",
            "WEDNESDAY" to "水",
            "THURSDAY" to "木",
            "FRIDAY" to "金",
            "SATURDAY" to "土",
            "SUNDAY" to "日"
        )

        val date = LocalDate.now()
        binding.dateText.text =
            (date.monthValue).toString() + "/" + date.dayOfMonth + " " + dayOfWeek[date.dayOfWeek.toString()]

        binding.fab.setOnClickListener {
            com.example.nhkprogramapi.ui.BottomSheetDialog().show(supportFragmentManager, null)
        }

        viewModel.serviceId.observe(this) {
            when (it) {
                -1 -> Toast.makeText(this, "チャンネル選択せい", Toast.LENGTH_SHORT).show()
                else -> viewModel.getProgramTitle(localDate(), it)
            }
        }

        viewModel.programInfoList.observe(this) {
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            val adapter = ProgramAdapter(this, viewModel)
            binding.recyclerView.adapter = adapter
            ProgramAdapter(this, viewModel).notifyDataSetChanged()
            binding.recyclerView.background = resources.getDrawable(R.drawable.border)
        }
        lifecycleScope.launch {
            viewModel.isSearching.collect {
                println("collect")
                if (it == true) binding.progressBar.visibility = android.widget.ProgressBar.VISIBLE
                else if (it == false) binding.progressBar.visibility = android.widget.ProgressBar.INVISIBLE
                else {
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@MainActivity, "番組表の取得に失敗しました", Toast.LENGTH_SHORT).show()
                    }
                    binding.progressBar.visibility = android.widget.ProgressBar.INVISIBLE
                }
            }
        }

        val fab = binding.fab
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(binding.recyclerView, dx, dy)
                if (dy > 0 && fab.visibility === View.VISIBLE) {
                    fab.hide()
                } else if (dy < 0 && fab.visibility !== View.VISIBLE) {
                    fab.show()
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun localDate(): String {
        return LocalDate.now().toString()
    }
}