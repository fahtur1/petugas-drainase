package com.pcr.drainit.binding

import android.R
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.adapters.AdapterViewBindingAdapter

@BindingAdapter("android:setEntries")
fun Spinner.setEntries(entries: List<Any>?) {
    val adapter = entries?.let {
        ArrayAdapter(context, R.layout.simple_spinner_item, entries)
    }
    adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

    setAdapter(adapter)
}

@BindingAdapter("android:selectedItemPosition")
fun AppCompatSpinner.setSelectedItemPosition(position: Int) {
    if (selectedItemPosition != position) {
        setSelection(position)
    }
}

@BindingAdapter(
    value = ["android:onItemSelected", "android:onNothingSelected", "android:selectedItemPositionAttrChanged"],
    requireAll = false
)
fun setOnItemSelectedListener(
    view: AdapterView<*>,
    selected: AdapterViewBindingAdapter.OnItemSelected?,
    nothingSelected: AdapterViewBindingAdapter.OnNothingSelected?,
    attrChanged: InverseBindingListener?
) {
    if (selected == null && nothingSelected == null && attrChanged == null) {
        view.setOnItemSelectedListener(null)
    } else {
        view.setOnItemSelectedListener(
            AdapterViewBindingAdapter.OnItemSelectedComponentListener(
                selected,
                nothingSelected,
                attrChanged
            )
        )
    }
}