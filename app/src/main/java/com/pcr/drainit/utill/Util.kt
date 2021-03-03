package com.pcr.drainit.utill

import com.google.android.gms.maps.model.LatLng
import org.json.JSONObject

object Util {

    fun geoToLatLong(json: String?): LatLng {
        val geometry = json?.let { JSONObject(it) }
        val coord = geometry?.getJSONArray("coordinates")

        return LatLng(coord?.get(0) as Double, coord.get(1) as Double)
    }

}