package com.pcr.drainit.ui.detail.pengaduan

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
class DetailPengaduanViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel() {

    companion object {
        const val ACTION_DETAIL_BACK = "action_detail_back"
        const val ACTION_DETAIL_ASSIGN = "action_detail_assign"
        const val ACTION_DETAIL_TIMEOUT = "action_detail_timeout"
    }

    val idPengaduan = MutableLiveData<String>()

    fun backBtnOnClick() {
        action.value = ACTION_DETAIL_BACK
    }

    fun assignButtonClick() {
        loadingEnabled.value = true
        viewModelScope.launch {
            when (val response = repository.assignPengaduan(
                Session.bearer ?: "",
                idPengaduan.value ?: ""
            )) {
                is Resource.Success -> {
                    when (response.statusCode) {
                        HttpURLConnection.HTTP_OK -> {
                            action.postValue(ACTION_DETAIL_ASSIGN)
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