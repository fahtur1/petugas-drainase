package com.pcr.drainit.ui.pengaduan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pcr.drainit.model.enitity.Pengaduan
import com.pcr.drainit.repository.MainRepository
import com.pcr.drainit.ui.BaseViewModel
import com.pcr.drainit.utill.Resource
import com.pcr.drainit.utill.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import javax.inject.Inject

@HiltViewModel
class ListPengaduanViewModel @Inject constructor(
    val repository: MainRepository
) : BaseViewModel() {

    companion object {
        const val ACTION_PENGADUAN_ITEM_ONCLICK = "action_pengaduan_item_onclick"
        const val ACTION_PENGADUAN_TIMEOUT = "action_pengaduan_timeout"
        const val ACTION_PENGADUAN_LISTUPDATE = "action_pengaduan_listupdate"
        const val ACTION_PENGADUAN_FILTER_ONCLICK = "action_pengaduan_filter_onclick"
        const val ACTION_PENGADUAN_DATA_FILTERED = "action_pengaduan_data_filtered"

        const val TYPE_SEMUA = "Semua"
        const val TYPE_BANJIR = "Banjir"
        const val TYPE_TERSUMBAT = "Tersumbat"

        val SINGLE_ITEM = arrayOf(TYPE_SEMUA, TYPE_BANJIR, TYPE_TERSUMBAT)
    }

    val listTempItem = ArrayList<Pengaduan>()
    val listPengaduanItem = ArrayList<Pengaduan>()
    val areListEmpty = MutableLiveData<Boolean>()
    val itemPosition = MutableLiveData<Int>()

    init {
        areListEmpty.value = listPengaduanItem.isEmpty()
    }

    fun getListPengaduan() {
        loadingEnabled.value = true
        viewModelScope.launch {
            when (val response = repository.getAllPengaduan(Session.bearer ?: "")) {
                is Resource.Success -> {
                    when (response.statusCode) {
                        HttpURLConnection.HTTP_OK -> {
                            listPengaduanItem.clear()
                            listTempItem.clear()

                            response.data?.forEach { item ->
                                listPengaduanItem.add(item)
                                listTempItem.add(item)
                            }

                            areListEmpty.postValue(listPengaduanItem.isEmpty())
                            action.postValue(ACTION_PENGADUAN_LISTUPDATE)
                        }
                    }
                    loadingEnabled.postValue(false)
                }
                is Resource.Error -> {
                    loadingEnabled.postValue(false)
                    action.postValue(ACTION_PENGADUAN_TIMEOUT)
                }
            }
        }
    }

    fun filterData(type: String) {
        loadingEnabled.value = true

        when (type) {
            TYPE_SEMUA -> {
                listPengaduanItem.clear()
                listTempItem.forEach {
                    listPengaduanItem.add(it)
                }

                action.value = ACTION_PENGADUAN_LISTUPDATE
            }
            TYPE_BANJIR -> {
                listPengaduanItem.clear()
                listTempItem.forEach {
                    if (it.tipePengaduan == TYPE_BANJIR) {
                        listPengaduanItem.add(it)
                    }
                }

                action.value = ACTION_PENGADUAN_DATA_FILTERED
            }
            TYPE_TERSUMBAT -> {
                listPengaduanItem.clear()
                listTempItem.forEach {
                    if (it.tipePengaduan == TYPE_TERSUMBAT) {
                        listPengaduanItem.add(it)
                    }
                }
                action.value = ACTION_PENGADUAN_DATA_FILTERED
            }
        }
        loadingEnabled.value = false
    }

    fun filterOnClick() {
        action.value = ACTION_PENGADUAN_FILTER_ONCLICK
    }

    fun itemListPengaduanOnClick(position: Int) {
        itemPosition.value = position
        action.value = ACTION_PENGADUAN_ITEM_ONCLICK
    }

}