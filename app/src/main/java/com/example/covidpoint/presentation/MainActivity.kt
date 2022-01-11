package com.example.covidpoint.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.covidpoint.R
import com.example.covidpoint.databinding.ActivityMainBinding
import com.example.covidpoint.di.App

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_CovidPoint)
        App.graph.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}