package com.pcr.drainit.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pcr.drainit.model.enitity.Petugas
import com.pcr.drainit.repository.MainRepository
import com.pcr.drainit.ui.BaseViewModel
import com.pcr.drainit.utill.Resource
import com.pcr.drainit.utill.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel() {

    companion object {
        const val ACTION_LOGIN_SUCCESS = "action_login_success"
        const val ACTION_LOGIN_FAILED = "action_login_failed"
        const val ACTION_LOGIN_TIMEOUT = "action_login_timeout"
        const val ACTION_LOGIN_FORM_BLANK = "action_login_form_blank"
        const val ACTION_LOGIN_USER_LOGGEDIN = "action_login_user_loggedin"
        const val ACTION_LOGIN_INVALID = "action_login_invalid"
    }

    val emailPetugas = MutableLiveData<String>()
    val passPetugas = MutableLiveData<String>()

    fun loginOnClick() {
        loadingEnabled.value = true
        if (!emailPetugas.value.isNullOrEmpty() && !passPetugas.value.isNullOrEmpty()) {
            viewModelScope.launch {
                val petugas = Petugas(
                    email = emailPetugas.value ?: "",
                    password = passPetugas.value ?: ""
                )

                when (val result = repository.petugasLogin(petugas)) {
                    is Resource.Success -> {
                        when (result.statusCode) {
                            HttpURLConnection.HTTP_OK -> {
                                Session.bearer = result.data?.accessToken
                                action.postValue(ACTION_LOGIN_SUCCESS)
                            }
                            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                                action.postValue(ACTION_LOGIN_FAILED)
                            }
                            HttpURLConnection.HTTP_FORBIDDEN -> {
                                action.postValue(ACTION_LOGIN_INVALID)
                            }
                        }
                        loadingEnabled.postValue(false)
                    }
                    is Resource.Error -> {
                        action.postValue(ACTION_LOGIN_TIMEOUT)
                        loadingEnabled.postValue(false)
                    }
                }
            }
        } else {
            action.value = ACTION_LOGIN_FORM_BLANK
            loadingEnabled.value = false
        }
    }

    fun checkSession() {
        viewModelScope.launch {
            delay(500L)

            loadingEnabled.postValue(true)

            if (Session.bearer != "Bearer null") {
                action.postValue(ACTION_LOGIN_USER_LOGGEDIN)
            } else {
                loadingEnabled.postValue(false)
            }
        }
    }

}