package com.iwgroup.covidpoint.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.iwgroup.covidpoint.R
import com.iwgroup.covidpoint.databinding.ActivityMainBinding
import com.iwgroup.covidpoint.di.App
import com.iwgroup.covidpoint.presentation.fragments.container.ContainerFragment
import com.iwgroup.covidpoint.utils.extentions.hideKeyboard


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_CovidPoint)
        App.graph.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onBackPressed() {
        val containerFragment =
            supportFragmentManager.fragments[0].childFragmentManager.fragments[0] as ContainerFragment
        val currentFragment = containerFragment.viewPager.currentItem

        if (currentFragment == 0)
            this.finish()
        else
            containerFragment.viewPager.currentItem = 0
    }
}