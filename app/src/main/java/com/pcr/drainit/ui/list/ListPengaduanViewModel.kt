package com.pcr.drainit.ui.list

import androidx.lifecycle.MutableLiveData
import com.pcr.drainit.model.Pengaduan
import com.pcr.drainit.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListPengaduanViewModel @Inject constructor(

) : BaseViewModel() {

    val listPengaduanItem = ArrayList<Pengaduan>()
    val areListEmpty = MutableLiveData<Boolean>()

    init {
        areListEmpty.value = listPengaduanItem.isEmpty()
    }

}