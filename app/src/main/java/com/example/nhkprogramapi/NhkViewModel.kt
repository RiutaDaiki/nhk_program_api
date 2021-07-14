package com.example.nhkprogramapi

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NhkViewModel: ViewModel() {
    object Repository{
        val repository = NhkRepository()
    }

    fun getProgramTitle(date: String,callBack: (String) -> Unit){
        Log.d("date", date)
        viewModelScope.launch(Dispatchers.IO) {
            Repository.repository.getProgramTitle(date)
                .onSuccess {
                    Log.d("viewModel", it)
                    callBack(it)
                }
                .onFailure {
                    Log.d("viewModel", it.toString())
                }
        }
    }

    val isBottomSheetExpanded = MutableLiveData<Boolean>()

}