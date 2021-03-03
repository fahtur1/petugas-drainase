package com.pcr.drainit.ui.profil

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
class ProfilViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel() {

    companion object {
        const val ACTION_PROFIL_TIMEOUT = "action_profil_timeout"
        const val ACTION_PROFIL_PENGATURAN = "action_profil_pengaturan"
    }

    val namaPetugas = MutableLiveData<String>()
    val handphonePetugas = MutableLiveData<String>()
    val emailPetugas = MutableLiveData<String>()
    val posisiPetugas = MutableLiveData<String>()
    val alamatPetugas = MutableLiveData<String>()

    fun getUserProfile() {
        loadingEnabled.value = true

        viewModelScope.launch {
            when (val response = repository.getPetugasProfile(Session.bearer ?: "")) {
                is Resource.Success -> {
                    when (response.statusCode) {
                        HttpURLConnection.HTTP_OK -> {
                            response.data?.let { dataPetugas ->
                                namaPetugas.postValue(dataPetugas.nama ?: "")
                                handphonePetugas.postValue(dataPetugas.noHp ?: "")
                                emailPetugas.postValue(dataPetugas.email ?: "")
                                posisiPetugas.postValue(dataPetugas.posisiPetugas ?: "")
                                alamatPetugas.postValue(dataPetugas.alamat ?: "")
                            }
                        }
                    }
                    loadingEnabled.postValue(false)
                }
                is Resource.Error -> {
                    action.postValue(ACTION_PROFIL_TIMEOUT)
                    loadingEnabled.postValue(false)
                }
            }
        }
    }

    fun pengaturanOnClick() {
        action.value = ACTION_PROFIL_PENGATURAN
    }

}