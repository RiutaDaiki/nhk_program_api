package com.example.nhkprogramapi

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private val viewModel : NhkViewModel by viewModels()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

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

        //#2 Initializing the BottomSheetBehavior
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        //#3 Listening to State Changes of BottomSheet
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                buttonBottomSheetPersistent.text = when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> "Close Persistent Bottom Sheet"
                    BottomSheetBehavior.STATE_COLLAPSED -> "Open Persistent Bottom Sheet"
                    else -> "Persistent Bottom Sheet"
                }
            }
        })


        //#4 Changing the BottomSheet State on ButtonClick
        buttonBottomSheetPersistent.setOnClickListener {
            val state =
                if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
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