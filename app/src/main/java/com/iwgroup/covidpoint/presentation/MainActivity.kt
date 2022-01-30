package com.iwgroup.covidpoint.presentation

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.*
import com.iwgroup.covidpoint.R
import com.iwgroup.covidpoint.databinding.ActivityMainBinding
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

        val activityView = findViewById<FrameLayout>(R.id.mainActivity)
        hideStatusBar(activityView)
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

    private fun hideStatusBar(view: View) {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        ViewCompat.setOnApplyWindowInsetsListener(view) { mainView, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())
            // Apply the insets as padding to the view. Here we're setting all of the
            // dimensions, but apply as appropriate to your layout. You could also
            // update the views margin if more appropriate.
            mainView.updatePadding(0,0,0, insets.bottom)

            // Return CONSUMED if we don't want the window insets to keep being passed
            // down to descendant views.
            WindowInsetsCompat.CONSUMED
        }
    }
}