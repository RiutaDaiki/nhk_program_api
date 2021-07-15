package com.example.nhkprogramapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nhkprogramapi.entity.ProgramInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NhkViewModel : ViewModel() {
    object Repository {
        val repository = NhkRepository()
    }

    val serviceIdMap = mapOf<Int, String>(
        2131231064 to "g1",
        2131230879 to "e1",
        2131230808 to "s1",
        2131230809 to "s3"
    )

    private val programInfoList = MutableLiveData<List<ProgramInfo>>()
    val content: LiveData<List<ProgramInfo>> = programInfoList

    fun getProgramTitle(date: String, service: Int) {
        Log.d("date", date)
        viewModelScope.launch(Dispatchers.IO) {
            when (serviceIdMap[service]) {
                "g1" -> Repository.repository.getSougouProgramTitle(date)
                    .onSuccess {
                        programInfoList.value = it
                    }
                "e1" -> {
                    Repository.repository.getEteleProgramTitle(date)
                        .onSuccess {
                        }
                }
                "s1" -> {
                    Repository.repository.getBsProgramTitle(date)
                        .onSuccess {
                        }
                }
                "s3" -> {
                    Repository.repository.getBsPremiumProgramTitle(date)
                        .onSuccess {
                        }
                }
            }

        }
    }

    val serviceId = MutableLiveData<Int>()
}