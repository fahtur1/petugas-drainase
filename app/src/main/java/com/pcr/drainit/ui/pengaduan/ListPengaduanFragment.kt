package com.pcr.drainit.ui.pengaduan

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pcr.drainit.R
import com.pcr.drainit.adapter.RecyclerViewPengaduanAdapter
import com.pcr.drainit.databinding.FragmentListPengaduanBinding
import com.pcr.drainit.ui.activity.MainActivity
import com.pcr.drainit.ui.detail.pengaduan.DetailPengaduanActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListPengaduanFragment : Fragment() {

    private lateinit var dataBinding: FragmentListPengaduanBinding
    private lateinit var pengaduanAdapter: RecyclerViewPengaduanAdapter
    private val listPengaduanViewModel: ListPengaduanViewModel by viewModels()

    private lateinit var launcher: ActivityResultLauncher<Intent>

    private var checkedItem = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)

        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                (activity as MainActivity).dataBinding.navbarMain.setItemSelected(R.id.nav_riwayat)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_list_pengaduan, container, false)
        pengaduanAdapter = RecyclerViewPengaduanAdapter(listPengaduanViewModel)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        dataBinding.apply {
            lifecycleOwner = this@ListPengaduanFragment
            viewModel = listPengaduanViewModel
        }

        listPengaduanViewModel.action.observe(viewLifecycleOwner, { action ->
            when (action) {
                ListPengaduanViewModel.ACTION_PENGADUAN_LISTUPDATE -> onListUpdate()
                ListPengaduanViewModel.ACTION_PENGADUAN_ITEM_ONCLICK -> onItemClick()
                ListPengaduanViewModel.ACTION_PENGADUAN_FILTER_ONCLICK -> onFilterBtnClick()
                ListPengaduanViewModel.ACTION_PENGADUAN_DATA_FILTERED -> onDataFiltered()
            }
        })
        listPengaduanViewModel.getListPengaduan()
    }

    override fun onResume() {
        super.onResume()
        listPengaduanViewModel.getListPengaduan()
        listPengaduanViewModel.filterData(ListPengaduanViewModel.SINGLE_ITEM[checkedItem])
    }

    private fun onDataFiltered() {
        pengaduanAdapter.notifyDataSetChanged()
    }

    private fun onFilterBtnClick() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Pilih Filter")
            .setNeutralButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("OK") { _, _ ->
                listPengaduanViewModel.filterData(ListPengaduanViewModel.SINGLE_ITEM[checkedItem])
            }
            .setSingleChoiceItems(ListPengaduanViewModel.SINGLE_ITEM, checkedItem) { _, which ->
                checkedItem = which
            }
            .show()
    }

    private fun onItemClick() {
        val itemClicked =
            listPengaduanViewModel.listPengaduanItem[listPengaduanViewModel.itemPosition.value ?: 0]

        val intent = Intent(requireContext(), DetailPengaduanActivity::class.java)
        intent.putExtra(DetailPengaduanActivity.DETAIL_EXTRA_PARCEL, itemClicked)

        launcher.launch(intent)
    }

    private fun onListUpdate() {
        pengaduanAdapter.apply {
            listPengaduan = listPengaduanViewModel.listPengaduanItem
            pengaduanAdapter.notifyDataSetChanged()
        }
    }

    private fun setupRecyclerView() {
        dataBinding.rvPengaduan.apply {
            adapter = pengaduanAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}