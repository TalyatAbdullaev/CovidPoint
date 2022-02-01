package com.iwgroup.covidpoint.presentation.fragments.splash

import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.view.*
import androidx.navigation.fragment.findNavController
import com.iwgroup.covidpoint.R
import com.iwgroup.covidpoint.databinding.FragmentSplashBinding
import com.iwgroup.covidpoint.di.App
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureSplashLogoPosition()
    }

    private fun configureSplashLogoPosition() {
        val splashLogo = requireActivity().findViewById<ImageView>(R.id.ivSplashLogo)
        Log.d("TAG", "mess")

        ViewCompat.setOnApplyWindowInsetsListener(splashLogo) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())
            val params = ViewGroup.MarginLayoutParams(
                ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                ViewGroup.MarginLayoutParams.WRAP_CONTENT,
            )

            Log.d("TAG", "bot ${insets.bottom}")
            params.setMargins(0, insets.bottom, 0, 0)
            view.setLayoutParams(params)

            WindowInsetsCompat.CONSUMED
        }
    }
}