package com.iwgroup.covidpoint.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.iwgroup.covidpoint.R
import com.iwgroup.covidpoint.databinding.ActivityMainBinding
import com.iwgroup.covidpoint.databinding.FragmentContainerBinding
import com.iwgroup.covidpoint.di.App
import com.iwgroup.covidpoint.presentation.fragments.container.ContainerFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_CovidPoint)
        App.graph.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setWindowFullScreen()
    }

    private fun setWindowFullScreen() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    override fun onBackPressed() {
        val containerFragment = supportFragmentManager.fragments[0].childFragmentManager.fragments[0] as ContainerFragment
        val currentFragment = containerFragment.viewPager.currentItem

        if(currentFragment == 0)
            this.finish()
        else
            containerFragment.viewPager.currentItem = 0

        Log.d("TAG", "back stack - " + supportFragmentManager.fragments[0].childFragmentManager.fragments[0])
    }
}