package com.farenas.challengeml.ui.splash

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.farenas.challengeml.R
import com.farenas.challengeml.databinding.FragmentSplashBinding
import com.farenas.challengeml.ui.base.BaseFragment

class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSplashBinding.bind(view)

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.splashTimeOut.observe(viewLifecycleOwner, {
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
        })
    }
}