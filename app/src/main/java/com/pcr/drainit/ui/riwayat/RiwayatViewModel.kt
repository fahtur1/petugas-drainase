package com.pcr.drainit.ui.riwayat

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
class RiwayatViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel() {

    companion object {
        const val ACTION_RIWAYAT_ITEMCLICK = "action_riwayat_itemclick"
        const val ACTION_RIWAYAT_TIMEOUT = "action_riwayat_timeout"
        const val ACTION_RIWAYAT_LISTUPDATE = "action_riwayat_listupdate"
    }

    val listRiwayat = ArrayList<Pengaduan>()
    val areListEmpty = MutableLiveData<Boolean>()
    val itemPosition = MutableLiveData<Int>()

    init {
        areListEmpty.value = true
    }

    fun getListRiwayat() {
        loadingEnabled.value = true
        viewModelScope.launch {
            when (val response = repository.getPengaduanAssign(Session.bearer ?: "")) {
                is Resource.Success -> {
                    when (response.statusCode) {
                        HttpURLConnection.HTTP_OK -> {
                            listRiwayat.clear()
                            response.data?.forEach { dataPengaduan ->
                                listRiwayat.add(dataPengaduan)
                            }
                            areListEmpty.postValue(listRiwayat.isEmpty())
                            action.postValue(ACTION_RIWAYAT_LISTUPDATE)
                        }
                    }
                }
                is Resource.Error -> {
                    action.postValue(ACTION_RIWAYAT_TIMEOUT)
                }
            }
            loadingEnabled.postValue(false)
        }
    }

    fun itemListPengaduanOnClick(position: Int) {
        itemPosition.value = position
        action.value = ACTION_RIWAYAT_ITEMCLICK
    }
}