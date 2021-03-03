package com.pcr.drainit.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pcr.drainit.R
import com.pcr.drainit.databinding.FragmentMapBinding
import com.pcr.drainit.ui.pengaduan.ListPengaduanViewModel
import com.pcr.drainit.utill.Constant.COORD_PEKANBARU
import com.pcr.drainit.utill.Util
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var dataBinding: FragmentMapBinding
    private val mapDrainaseViewModel: MapDrainaseViewModel by viewModels()

    private val listRiwayat = ArrayList<Marker>()
    private val listPengaduan = ArrayList<Marker>()

    private var checkedItem = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.apply {
            lifecycleOwner = this@MapFragment
            viewModel = mapDrainaseViewModel
            mapView.apply {
                onCreate(savedInstanceState)
                onResume()
                getMapAsync(this@MapFragment)
            }
        }

        mapDrainaseViewModel.action.observe(viewLifecycleOwner, { action ->
            when (action) {
                MapDrainaseViewModel.ACTION_MAP_FILTER_ONCLICK -> filterOnClick()
                MapDrainaseViewModel.ACTION_MAP_TYPE_SEMUA -> filterSemua()
                MapDrainaseViewModel.ACTION_MAP_TYPE_PENGADUAN -> filterPengaduan()
                MapDrainaseViewModel.ACTION_MAP_TYPE_RIWAYAT -> filterRiwayat()
            }
        })
        mapDrainaseViewModel.getSemuaTitik()
    }

    override fun onResume() {
        super.onResume()
        mapDrainaseViewModel.getSemuaTitik()
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let { googleMap ->
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(COORD_PEKANBARU, 13.5f))
            googleMap.setOnMapLoadedCallback {
                mapDrainaseViewModel.apply {
                    if (areListReady.value == true) {
                        listTitikRiwayat.forEach { item ->
                            val color = BitmapDescriptorFactory.defaultMarker(
                                BitmapDescriptorFactory.HUE_AZURE
                            )
                            val marker = googleMap.addMarker(
                                MarkerOptions()
                                    .position(Util.geoToLatLong(item.geometry))
                                    .title("Titik ${item.tipePengaduan}")
                                    .snippet(item.namaJalan?.substring(0, 30))
                                    .icon(color)
                            )

                            this@MapFragment.listRiwayat.add(marker)
                        }

                        listTitikPengaduan.forEach { item ->
                            val marker = googleMap.addMarker(
                                MarkerOptions()
                                    .position(Util.geoToLatLong(item.geometry))
                                    .title("Titik ${item.tipePengaduan}")
                                    .snippet(item.namaJalan?.substring(0, 30))
                            )

                            this@MapFragment.listPengaduan.add(marker)
                        }
                    }
                }
            }
        }
    }

    private fun filterOnClick() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Pilih Filter")
            .setNeutralButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("OK") { _, _ ->
                mapDrainaseViewModel.filterData(MapDrainaseViewModel.LIST_ITEM_TYPE[checkedItem])
            }
            .setSingleChoiceItems(MapDrainaseViewModel.LIST_ITEM_TYPE, checkedItem) { _, which ->
                checkedItem = which
            }
            .show()
    }

    private fun filterSemua() {
        listRiwayat.forEach { it.isVisible = true }
        listPengaduan.forEach { it.isVisible = true }
    }

    private fun filterPengaduan() {
        listPengaduan.forEach { it.isVisible = true }
        listRiwayat.forEach { it.isVisible = false }
    }

    private fun filterRiwayat() {
        listPengaduan.forEach { it.isVisible = false }
        listRiwayat.forEach { it.isVisible = true }
    }

}