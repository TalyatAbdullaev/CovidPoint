package com.example.covidpoint.presentation.fragments.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.covidpoint.R
import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.databinding.FragmentSplashBinding
import com.example.covidpoint.di.App
import com.example.covidpoint.utils.AppUtils
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class SplashFragment : MvpAppCompatFragment(), SplashInterface {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var presenterProvider: Provider<SplashPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.graph.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun navigateToApp() {
        findNavController().navigate(R.id.containerFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}