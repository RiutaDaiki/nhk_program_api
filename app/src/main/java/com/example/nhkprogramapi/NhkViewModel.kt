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

    val serviceIdMap = mapOf<Int, String>(
        2131231064 to "g1",
        2131230879 to "e1",
        2131230808 to "s1",
        2131230809 to "s3"
    )

    fun getProgramTitle(date: String, service: Int,callBack: (String) -> Unit){
        Log.d("date", date)
        viewModelScope.launch(Dispatchers.IO) {
            when(serviceIdMap[service]){
                "g1" -> Repository.repository.getSougouProgramTitle(date)
                    .onSuccess {
                        callBack(it)
                    }
                "e1" ->{
                    Repository.repository.getEteleProgramTitle(date)
                        .onSuccess {
                            callBack(it)
                        }
                }
                "s1" -> {
                    Repository.repository.getBsProgramTitle(date)
                        .onSuccess {
                            callBack(it)
                        }
                }
                "s3" -> {
                    Repository.repository.getBsPremiumProgramTitle(date)
                        .onSuccess {
                            callBack(it)
                        }
                }
            }

        }
    }
    val serviceId = MutableLiveData<Int>()
}