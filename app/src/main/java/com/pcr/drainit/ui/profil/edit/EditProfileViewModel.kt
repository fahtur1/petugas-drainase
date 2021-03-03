package com.pcr.drainit.ui.profil.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pcr.drainit.model.enitity.Petugas
import com.pcr.drainit.repository.MainRepository
import com.pcr.drainit.ui.BaseViewModel
import com.pcr.drainit.utill.Resource
import com.pcr.drainit.utill.Session
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
    }

    val namaPetugas = MutableLiveData<String>()
    val alamatPetugas = MutableLiveData<String>()

    fun buttonEditProfileOnClick() {
        viewModelScope.launch {
            val petugas = getProfilePetugas()


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