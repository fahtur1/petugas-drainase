package com.pcr.drainit.ui.detail.riwayat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pcr.drainit.repository.MainRepository
import com.pcr.drainit.ui.BaseViewModel
import com.pcr.drainit.utill.Resource
import com.pcr.drainit.utill.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import javax.inject.Inject

@HiltViewModel
class DetailRiwayatViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel() {

    companion object {
        const val ACTION_DETAIL_RIWAYAT_BACK = "action_detail_riwayat"
        const val ACTION_DETAIL_BACK_WITHCHANGES = "action_detail_back_withchanges"
        const val ACTION_DETAIL_CANCEL_EDIT = "action_detail_cancel_edit"
        const val ACTION_DETAIL_TIMEOUT = "action_detail_timeout"
        const val ACTION_DETAIL_RIWAYAT_UPDATED = "action_detail_riwayat_updated"
    }

    val idLaporan = MutableLiveData<String>()
    val editEnabled = MutableLiveData<Boolean>()
    val laporanPetugas = MutableLiveData<String>()
    var tempValue: String? = ""

    init {
        editEnabled.value = false
    }

    fun onBackButtonClick() {
        if (tempValue != "" && tempValue != laporanPetugas.value) {
            action.value = ACTION_DETAIL_BACK_WITHCHANGES
        } else {
            action.value = ACTION_DETAIL_RIWAYAT_BACK
        }
    }

    fun onEditButtonClick() {
        if (editEnabled.value == true) {
            if (tempValue != "" && tempValue != laporanPetugas.value) {
                action.value = ACTION_DETAIL_CANCEL_EDIT
                laporanPetugas.value = tempValue
            }
        } else {
            editEnabled.value = editEnabled.value?.let { !it }
        }
    }

    fun submitEditButtonOnClick() {
        loadingEnabled.value = true
        viewModelScope.launch {
            when (val response = repository.updatePengaduan(
                Session.bearer ?: "",
                idLaporan.value ?: "",
                laporanPetugas.value ?: ""
            )) {
                is Resource.Success -> {
                    when (response.statusCode) {
                        HttpURLConnection.HTTP_OK -> {
                            action.postValue(ACTION_DETAIL_RIWAYAT_UPDATED)
                        }
                    }
                }
                is Resource.Error -> {
                    action.postValue(ACTION_DETAIL_TIMEOUT)
                }
            }
            loadingEnabled.postValue(false)
        }
    }

}