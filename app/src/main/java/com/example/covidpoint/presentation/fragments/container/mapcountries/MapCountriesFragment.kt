package com.example.covidpoint.presentation.fragments.container.mapcountries

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.example.covidpoint.R
import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.databinding.FragmentCountiresMapBinding
import com.example.covidpoint.databinding.MarkerItemBinding
import com.example.covidpoint.di.App
import com.example.covidpoint.presentation.ViewDrawer
import com.example.covidpoint.utils.AppUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.*
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.ui_view.ViewProvider
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class MapCountriesFragment : MvpAppCompatFragment(), MapCountriesInterface,
    ClusterListener, ClusterTapListener, MapObjectTapListener {
    private var _binding: FragmentCountiresMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var yandexMap: MapView

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
        MapKitFactory.initialize(context)

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        yandexMap = binding.yandexMap

        setupYandexMap()
        setupBottomSheet()
    }

    override fun showCountryStatistic(country: CountryEntity) {

        val bottomSheet = BottomSheetBehavior.from<CardView>(binding.bottomSheet.root)
        bottomSheet.state = STATE_EXPANDED

        ViewDrawer().drawView(country, binding.bottomSheet)
    }

    override fun showCountries(countries: List<CountryEntity>) {

        val pointCollection = yandexMap.map.mapObjects.addClusterizedPlacemarkCollection(this)

        countries.forEach {
            val point = Point(it.latitude, it.longitude)
            val data = UserData(mapOf(AppUtils.ID_KEY to it.id.toString()))

            val placemark = pointCollection.addPlacemark(point)
            val placemarkView = MarkerItemBinding.inflate(layoutInflater)
            placemarkView.tvConfirmedValue.text = it.confirmed.toString()
            placemark.setView(ViewProvider(placemarkView.root))
            placemark.userData = data
            placemark.addTapListener(this)
        }

        pointCollection.clusterPlacemarks(60.0, 15)
    }

    private fun setupYandexMap() {
        yandexMap.map.move(
            CameraPosition(Point(39.32783121110484, 29.01118537580683), 3.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0F),
            null
        )

        yandexMap.map.isRotateGesturesEnabled = false
    }

    private fun setupBottomSheet() {
        binding.bottomSheet.childLayout.isExpanded = true
        binding.bottomSheet.btnDetailed.visibility = View.GONE
    }

    override fun onClusterAdded(p0: Cluster) {
        p0.appearance.setIcon(ImageProvider.fromResource(context, R.drawable.ic_mark))
        p0.addClusterTapListener(this)
    }

    override fun onClusterTap(p0: Cluster): Boolean {
        Log.d("TAG", "cluster tap")

        yandexMap.map.move(
            CameraPosition(
                p0.appearance.geometry,
                yandexMap.map.cameraPosition.zoom + 1, 0.0f, 0.0f
            ),
            Animation(Animation.Type.SMOOTH, 0.5f),
            null
        )
        return true
    }

    override fun onMapObjectTap(p0: MapObject, p1: Point): Boolean {
        Log.d("TAG", "tap")

        val userData = p0.userData as UserData
        val countryId = userData.data.getValue(AppUtils.ID_KEY).toInt()
        presenter.getCountryStatistic(countryId)

        return true
    }

    override fun onStart() {
        super.onStart()
        yandexMap.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        super.onStop()
        yandexMap.onStop()
        MapKitFactory.getInstance().onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}