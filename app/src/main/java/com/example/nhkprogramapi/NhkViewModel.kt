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
        2131231066 to "g1",
        2131230880 to "e1",
        2131230808 to "s1",
        2131230809 to "s3"
    )

    val programInfoList = MutableLiveData<List<ProgramInfo>>()

    fun getProgramTitle(date: String, service: Int) {
        println("0")
        viewModelScope.launch(Dispatchers.IO) {
            when (serviceIdMap[service]) {
                "g1" -> Repository.repository.getSougouProgramTitle(date)
                    .onSuccess {
                        programInfoList.postValue(it)
                    }
                    .onFailure {
                        println("error")
                    }
                "e1" -> {
                    Repository.repository.getEteleProgramTitle(date)
                        .onSuccess {
                            programInfoList.postValue(it)
                        }
                }
                "s1" -> {
                    Repository.repository.getBsProgramTitle(date)
                        .onSuccess {
                            programInfoList.postValue(it)
                        }
                }
                "s3" -> {
                    Repository.repository.getBsPremiumProgramTitle(date)
                        .onSuccess {
                            programInfoList.postValue(it)
                        }
                }
            }

        }
    }

    val serviceId = MutableLiveData<Int>()
}