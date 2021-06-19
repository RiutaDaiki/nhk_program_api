package com.example.nhkprogramapi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NhkViewModel: ViewModel() {
    object Repository{
        val repository = NhkRepository()
    }

    fun getProgramTitle(callBack: (String) -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            Repository.repository.getProgramTitle()
                .onSuccess {
                    Log.d("viewModel", it)
                    callBack(it)
                }
                .onFailure {
                    Log.d("viewModel", it.toString())
                }
        }
    }

}