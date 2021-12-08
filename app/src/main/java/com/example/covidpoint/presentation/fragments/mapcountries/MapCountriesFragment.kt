package com.example.covidpoint.presentation.fragments.mapcountries

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.engine.Resource
import com.example.covidpoint.R
import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.data.network.utils.Urls
import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.databinding.FragmentCountiresMapBinding
import com.example.covidpoint.di.App
import com.example.covidpoint.presentation.fragments.listcountries.ListCountriesPresenter
import com.example.covidpoint.utils.AppUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import moxy.MvpAppCompatFragment
import moxy.MvpPresenter
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class MapCountriesFragment : MvpAppCompatFragment(), MapCountriesInterface {
    private var _binding: FragmentCountiresMapBinding? = null
    private val binding get() = _binding!!

    private var yandexMap: MapView? = null

    @Inject
    lateinit var presenterProvider: Provider<MapCountiresPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountiresMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.graph.inject(this)
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey(AppUtils.YANDEX_API_KEY)
        MapKitFactory.initialize(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        yandexMap = binding.yandexMap
        yandexMap!!.map.move(
            CameraPosition(Point(39.32783121110484, 29.01118537580683), 3.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0F),
            null
        )

        yandexMap.let {
            it!!.map.mapObjects.addTapListener(object : MapObjectTapListener {
                override fun onMapObjectTap(p0: MapObject, p1: Point): Boolean {
                    presenter.getCountryByPoint(p1)
                    return true
                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        yandexMap?.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        super.onStop()
        yandexMap?.onStop()
        MapKitFactory.getInstance().onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showCountryStatistic(country: Country) {

    }

    override fun showCountries(countries: List<CountryEntity>) {

        val points: List<Point> = countries.map {
            Point(it.latitude, it.longitude)
        }

        yandexMap.let {
            it!!.map.mapObjects.addPlacemarks(
                points, ImageProvider.fromResource(context, R.drawable.ic_mark), IconStyle()
            )
        }

    }


}