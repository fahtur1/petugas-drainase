package com.pcr.drainit.ui.riwayat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pcr.drainit.R
import com.pcr.drainit.adapter.RecyclerViewRiwayatAdapter
import com.pcr.drainit.databinding.FragmentRiwayatBinding
import com.pcr.drainit.ui.detail.riwayat.DetailRiwayatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RiwayatFragment : Fragment() {

    private lateinit var dataBinding: FragmentRiwayatBinding
    private lateinit var riwayatAdapter: RecyclerViewRiwayatAdapter
    private val riwayatViewModel: RiwayatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_riwayat, container, false)
        riwayatAdapter = RecyclerViewRiwayatAdapter(riwayatViewModel)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        dataBinding.apply {
            lifecycleOwner = this@RiwayatFragment
            viewModel = riwayatViewModel
        }

        riwayatViewModel.action.observe(viewLifecycleOwner, { action ->
            when (action) {
                RiwayatViewModel.ACTION_RIWAYAT_LISTUPDATE -> onListUpdate()
                RiwayatViewModel.ACTION_RIWAYAT_ITEMCLICK -> onItemClick()
            }
        })
        riwayatViewModel.getListRiwayat()
    }

    override fun onResume() {
        super.onResume()
        riwayatViewModel.getListRiwayat()
    }

    private fun onItemClick() {
        val item = riwayatViewModel.listRiwayat[riwayatViewModel.itemPosition.value ?: 0]

        val intent = Intent(requireContext(), DetailRiwayatActivity::class.java).apply {
            putExtra(DetailRiwayatActivity.EXTRA_DETAIL_RIWAYAT_PARCEL, item)
        }

        startActivity(intent)
    }

    private fun onListUpdate() {
        riwayatAdapter.listPengaduan = riwayatViewModel.listRiwayat
        riwayatAdapter.notifyDataSetChanged()
    }

    private fun setupRecyclerView() {
        dataBinding.rvRiwayat.apply {
            adapter = riwayatAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}