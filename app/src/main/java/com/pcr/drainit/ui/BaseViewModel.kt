package com.pcr.drainit.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val action = MutableLiveData<String>()
    val loadingEnabled = MutableLiveData<Boolean>()

}