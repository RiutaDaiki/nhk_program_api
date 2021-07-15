package com.example.nhkprogramapi

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import android.widget.PopupMenu
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
        binding.dateText.text = (date.monthValue).toString()+ "/" + date.dayOfMonth + " " + dayOfWeek[date.dayOfWeek.toString()]

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
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            val adapter = ProgramAdapter(this, viewModel)
            binding.recyclerView.adapter = adapter
            ProgramAdapter(this, viewModel).notifyDataSetChanged()
         }

        binding.root.post(Runnable {
            showPopup(binding.root)
        })
    }

    fun showPopup(v : View){
        val popup = PopupMenu(this, v)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.menu_main, popup.menu)
        popup.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.live_in-> {
                    println("ナイス")
                }
            }
            true
        }
        popup.show()
    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item)
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun localDate(): String {
        return LocalDate.now().toString()
    }
}