package com.pcr.drainit.ui.profil.edit.profil

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pcr.drainit.model.enitity.Petugas
import com.pcr.drainit.repository.MainRepository
import com.pcr.drainit.ui.BaseViewModel
import com.pcr.drainit.utill.Resource
import com.pcr.drainit.utill.Session
import com.pcr.drainit.utill.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel() {

    companion object {
        const val ACTION_EDIT_PROFILE_SUCCESS = "action_edit_profile_success"
        const val ACTION_EDIT_PROFILE_FAILED = "action_edit_profile_failed"
        const val ACTION_EDIT_PROFILE_FORMBLANK = "action_edit_profile_formblank"
        const val ACTION_EDIT_PROFILE_TIMEOUT = "action_edit_profile_timeout"
        const val ACTION_EDIT_PROFILE_FOTO_CLICK = "action_edit_profile_foto_click"
    }

    val namaPetugas = MutableLiveData<String>()
    val alamatPetugas = MutableLiveData<String>()
    val fotoPetugas = MutableLiveData<Uri>()
    val fotoBitmap = MutableLiveData<String>()
    val fotoAwal = MutableLiveData<String>()

    fun fotoPetugasOnClick() {
        action.value = ACTION_EDIT_PROFILE_FOTO_CLICK
    }

    fun setPetugasData() {
        viewModelScope.launch {
            val petugas = getProfilePetugas()

            namaPetugas.postValue(petugas?.nama ?: "")
            alamatPetugas.postValue(petugas?.alamat ?: "")
            fotoAwal.postValue(petugas?.foto ?: "")
        }
    }

    fun buttonEditProfileOnClick() {
        loadingEnabled.value = true
        viewModelScope.launch {
            val petugas = getProfilePetugas()

            if (!namaPetugas.value.isNullOrEmpty() && !alamatPetugas.value.isNullOrEmpty()) {
                petugas?.alamat = alamatPetugas.value
                petugas?.nama = namaPetugas.value
                petugas?.foto = fotoBitmap.value

                when (val response = repository.updatePetugasProfile(
                    Session.bearer ?: "",
                    petugas ?: Petugas()
                )) {
                    is Resource.Success -> {
                        when (response.statusCode) {
                            HttpURLConnection.HTTP_OK -> action.postValue(
                                ACTION_EDIT_PROFILE_SUCCESS
                            )
                            else -> action.postValue(ACTION_EDIT_PROFILE_FAILED)
                        }
                    }
                    is Resource.Error -> {
                        action.postValue(ACTION_EDIT_PROFILE_TIMEOUT)
                    }
                }
                loadingEnabled.postValue(false)
            } else {
                action.postValue(ACTION_EDIT_PROFILE_FORMBLANK)
                loadingEnabled.postValue(false)
            }
        }
    }

    suspend fun getProfilePetugas(): Petugas? {
        return when (val response = repository.getPetugasProfile(Session.bearer ?: "")) {
            is Resource.Success -> when (response.statusCode) {
                HttpURLConnection.HTTP_OK -> response.data
                else -> response.data
            }
            is Resource.Error -> response.data
        }
    }

}