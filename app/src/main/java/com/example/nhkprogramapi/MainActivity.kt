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
import com.example.nhkprogramapi.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
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

        val bottomSheet = findViewById<ConstraintLayout>(R.id.bottomSheet)
        bottomSheet.findViewById<Button>(R.id.ca)

        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomSheet))

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> viewModel.isBottomSheetExpanded.value = true
                    BottomSheetBehavior.STATE_COLLAPSED -> viewModel.isBottomSheetExpanded.value = false
                    else -> {}
                }
            }
        })


        binding.fab.setOnClickListener {
            val state =
                if (viewModel.isBottomSheetExpanded.value == true)
                    BottomSheetBehavior.STATE_COLLAPSED
                else
                    BottomSheetBehavior.STATE_EXPANDED
            bottomSheetBehavior.state = state
        }
//        findViewById<Button>(R.id.button).setOnClickListener{
//            viewModel.getProgramTitle(localDate()) {
//                set(it)
//            }
//        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun localDate(): String {
        return LocalDate.now().toString()
    }
}