package com.pcr.drainit.ui.profil.edit.password

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
class EditPasswordViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel() {

    companion object {
        const val ACTION_EDIT_PASSWORD_SUCCESS = "action_edit_password_success"
        const val ACTION_EDIT_PASSWORD_TIMEOUT = "action_edit_password_timeout"
        const val ACTION_EDIT_PASSWORD_NOT_SAME = "action_edit_password_not_same"
        const val ACTION_EDIT_PASSWORD_OLD_WRONG = "action_edit_password_old_wrong"
    }

    val oldPass = MutableLiveData<String>()
    val confirmPass = MutableLiveData<String>()
    val newPass = MutableLiveData<String>()

    fun buttonEditPasswordOnClick() {
        loadingEnabled.value = true
        viewModelScope.launch {

            if (confirmPass.value.equals(newPass.value)) {
                when (val response = repository.changePassword(
                    Session.bearer ?: "",
                    oldPass.value ?: "",
                    newPass.value ?: ""
                )) {
                    is Resource.Success -> {
                        when (response.statusCode) {
                            HttpURLConnection.HTTP_OK -> action.postValue(
                                ACTION_EDIT_PASSWORD_SUCCESS
                            )
                            HttpURLConnection.HTTP_UNAUTHORIZED -> action.postValue(
                                ACTION_EDIT_PASSWORD_OLD_WRONG
                            )
                        }
                    }
                    is Resource.Error -> {
                        action.postValue(ACTION_EDIT_PASSWORD_TIMEOUT)
                    }
                }
                loadingEnabled.postValue(false)
            } else {
                action.postValue(ACTION_EDIT_PASSWORD_NOT_SAME)
            }
        }
    }

}