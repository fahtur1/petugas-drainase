package com.pcr.drainit.ui.map

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
class MapDrainaseViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel() {

    companion object {
        const val ACTION_MAP_TIMEOUT = "action_map_timeout"
        const val ACTION_MAP_FILTER_ONCLICK = "action_map_filter_onclick"
        const val ACTION_MAP_TYPE_SEMUA = "action_map_type_semua"
        const val ACTION_MAP_TYPE_PENGADUAN = "action_map_type_pengaduan"
        const val ACTION_MAP_TYPE_RIWAYAT = "action_map_type_riwayat"
        const val ACTION_MAP_LIST_READY = "action_map_list_ready"

        private const val TYPE_SEMUA = "Semua"
        private const val TYPE_PENGADUAN = "Pengaduan"
        private const val TYPE_RIWAYAT = "Riwayat"

        val LIST_ITEM_TYPE = arrayOf(TYPE_SEMUA, TYPE_PENGADUAN, TYPE_RIWAYAT)
    }

    val listTitikPengaduan = ArrayList<Pengaduan>()
    val listTitikRiwayat = ArrayList<Pengaduan>()
    val areListReady = MutableLiveData<Boolean>()

    init {
        areListReady.value = false
    }

    fun getSemuaTitik() {
        loadingEnabled.value = true
        viewModelScope.launch {
            if (getTitikPengaduan() && getTitikRiwayat()) {
                areListReady.postValue(true)
                action.postValue(ACTION_MAP_LIST_READY)
            } else {
                areListReady.postValue(false)
            }
        }
        loadingEnabled.value = false
    }

    private suspend fun getTitikPengaduan(): Boolean {
        return when (val response = repository.getAllPengaduan(Session.bearer ?: "")) {
            is Resource.Success -> {
                when (response.statusCode) {
                    HttpURLConnection.HTTP_OK -> {
                        response.data?.forEach { listTitikPengaduan.add(it) }
                        true
                    }
                    else -> false
                }
            }
            is Resource.Error -> {
                action.postValue(ACTION_MAP_TIMEOUT)
                false
            }
            else -> false
        }
    }

    private suspend fun getTitikRiwayat(): Boolean {
        return when (val response = repository.getPengaduanAssign(Session.bearer ?: "")) {
            is Resource.Success -> {
                when (response.statusCode) {
                    HttpURLConnection.HTTP_OK -> {
                        response.data?.forEach { listTitikRiwayat.add(it) }
                        true
                    }
                    else -> false
                }
            }
            is Resource.Error -> {
                action.postValue(ACTION_MAP_TIMEOUT)
                false
            }
            else -> false
        }
    }

    fun filterData(type: String) {
        when (type) {
            TYPE_SEMUA -> action.value = ACTION_MAP_TYPE_SEMUA
            TYPE_PENGADUAN -> action.value = ACTION_MAP_TYPE_PENGADUAN
            TYPE_RIWAYAT -> action.value = ACTION_MAP_TYPE_RIWAYAT
        }
    }

    fun filterButtonOnClick() {
        action.value = ACTION_MAP_FILTER_ONCLICK
    }

}