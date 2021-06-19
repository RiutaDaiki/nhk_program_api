package com.example.nhkprogramapi

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private val viewModel : NhkViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun set(s: String){
            val mainHandler = Handler(Looper.getMainLooper())
            val text = findViewById<TextView>(R.id.text)
            try {
                mainHandler.post { text.text = s }
            } catch (e: Exception) {
                Log.e("Exception", e.toString())
            }
        }
        viewModel.getProgramTitle(localDate()) {
            set(it)
        }

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun localDate(): String {
        return LocalDate.now().toString()
    }
}