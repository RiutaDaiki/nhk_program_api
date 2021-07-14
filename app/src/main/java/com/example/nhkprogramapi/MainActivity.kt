package com.example.nhkprogramapi

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.nhkprogramapi.databinding.ActivityMainBinding
import com.example.nhkprogramapi.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.net.ConnectException
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private val viewModel: NhkViewModel by viewModels()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fun set(s: String) {
            val mainHandler = Handler(Looper.getMainLooper())
            val text = binding.text
            try {
                mainHandler.post { text.text = s }
            } catch (e: Exception) {
                Log.e("Exception", e.toString())
            }
        }

        binding.fab.setOnClickListener {
            com.example.nhkprogramapi.ui.BottomSheetDialog().show(supportFragmentManager, null)
        }

//        findViewById<Button>(R.id.button).setOnClickListener{
//            viewModel.getProgramTitle(localDate()) {
//                set(it)
//            }
//        }

        viewModel.serviceId.observe(this){
            println("acti")
            println(it.toString())
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun localDate(): String {
        return LocalDate.now().toString()
    }
}