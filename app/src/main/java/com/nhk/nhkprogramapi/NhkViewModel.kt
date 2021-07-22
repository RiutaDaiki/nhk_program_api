package com.nhk.nhkprogramapi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhk.nhkprogramapi.entity.ProgramInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class NhkViewModel : ViewModel() {
    object Repository {
        val repository = NhkRepository()
    }

    val serviceIdMap = mapOf<Int, String>(
        2131296609 to "g1",
        2131296417 to "e1",
        2131296344 to "s1",
        2131296345 to "s3"
    )

    val serviceNameMap = mapOf<Int, String>(
        2131296609 to "総合",
        2131296417 to "Eテレ",
        2131296344 to "BS1",
        2131296345 to "BSプレミアム"
    )


    val residenceMap = mapOf<String, String>(
        "札幌" to "010",
        "函館" to "011",
        "旭川" to "012",
        "帯広" to "013",
        "釧路" to "014",
        "北見" to "015",
        "室蘭" to "016",
        "青森" to "020",
        "盛岡" to "030",
        "仙台" to "040",
        "秋田" to "050",
        "山形" to "060",
        "福島" to "070",
        "水戸" to "080",
        "宇都宮" to "090",
        "前橋" to "100",
        "さいたま" to "110",
        "千葉" to "120",
        "東京" to "130",
        "横浜" to "140",
        "新潟" to "150",
        "富山" to "160",
        "金沢" to "170",
        "福井" to "180",
        "甲府" to "190",
        "長野" to "200",
        "岐阜" to "210",
        "静岡" to "220",
        "名古屋" to "230",
        "津" to "240",
        "大津" to "250",
        "京都" to "260",
        "大阪" to "270",
        "神戸" to "280",
        "奈良" to "290",
        "和歌山" to "300",
        "鳥取" to "310",
        "松江" to "320",
        "岡山" to "330",
        "広島" to "340",
        "山口" to "350",
        "福島" to "360",
        "高松" to "370",
        "松山" to "380",
        "高知" to "390",
        "福岡" to "400",
        "北九州" to "401",
        "佐賀" to "410",
        "長崎" to "420",
        "熊本" to "430",
        "大分" to "440",
        "宮崎" to "450",
        "鹿児島" to "460",
        "沖縄" to "470"
    )


    val programInfoList = MutableLiveData<List<ProgramInfo>>()

    fun getProgramTitle(date: String, service: Int) {
        println("0")

        viewModelScope.launch(Dispatchers.IO) {
            when (serviceIdMap[service]) {
                "g1" -> Repository.repository.getSougouProgramTitle(date)
                    .onSuccess {
                        println("1")
                        updateIsSearching(false)
                        programInfoList.postValue(it)
                    }
                    .onFailure {
                        updateIsSearching(null)
                    }
                "e1" -> {
                    Repository.repository.getEteleProgramTitle(date)
                        .onSuccess {
                            updateIsSearching(false)
                            programInfoList.postValue(it)
                        }
                }
                "s1" -> {
                    Repository.repository.getBsProgramTitle(date)
                        .onSuccess {
                            updateIsSearching(false)
                            programInfoList.postValue(it)
                        }
                }
                "s3" -> {
                    Repository.repository.getBsPremiumProgramTitle(date)
                        .onSuccess {
                            updateIsSearching(false)
                            programInfoList.postValue(it)
                        }
                }
            }

        }
    }

    val serviceId = MutableLiveData<Int>()

    val userResidence = MutableLiveData<String>()

    private val _isSearching = MutableSharedFlow<Boolean?>()
    val isSearching = _isSearching
    fun updateIsSearching(bool: Boolean?){
        viewModelScope.launch {
            _isSearching.emit(bool)
        }
    }

}