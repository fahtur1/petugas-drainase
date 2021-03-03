package com.pcr.drainit.ui.profil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.pcr.drainit.R
import com.pcr.drainit.databinding.FragmentProfilBinding
import com.pcr.drainit.ui.activity.MainActivity
import com.pcr.drainit.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfilFragment : Fragment() {

    private val profilViewModel: ProfilViewModel by viewModels()
    private lateinit var dataBinding: FragmentProfilBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profil, container, false)
        profilViewModel.getUserProfile()
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dataBinding.apply {
            lifecycleOwner = this@ProfilFragment
            viewModel = profilViewModel
        }

        profilViewModel.action.observe(viewLifecycleOwner, { action ->
            when (action) {
                ProfilViewModel.ACTION_PROFIL_TIMEOUT -> connectionTimeout()
                ProfilViewModel.ACTION_PROFIL_PENGATURAN -> pengaturanOnClick()
            }
        })
    }

    private fun pengaturanOnClick() {
        startActivity(Intent(requireContext(), ProfilPengaturanActivity::class.java))
    }

    private fun connectionTimeout() {
    }

}