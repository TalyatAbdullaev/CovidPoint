package com.example.covidpoint.presentation.fragments.container.mapcountries

import android.app.AlertDialog
import android.content.DialogInterface
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
import com.example.covidpoint.utils.AppUtils
import com.example.covidpoint.utils.extentions.drawCountryIntoView
import com.google.android.material.bottomsheet.BottomSheetBehavior
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
    private lateinit var bottomSheet: BottomSheetBehavior<CardView>

    @Inject
    lateinit var presenterProvider: Provider<MapCountriesPresenter>
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

        setupYandexMap()
        setupBottomSheet()
    }

    override fun showCountryStatistic(country: CountryEntity) {
        bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
        binding.bottomSheet.drawCountryIntoView(country)
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

    override fun showAlertDialog(message: String) {
        AlertDialog.Builder(context)
            .setTitle("Ошибка")
            .setMessage(message)
            .setPositiveButton("OK", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {

                }
            })
            .show()
    }

    private fun setupYandexMap() {
        yandexMap = binding.yandexMap
        yandexMap.map.move(
            CameraPosition(Point(39.32783121110484, 29.01118537580683), 3.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0F),
            null
        )
        yandexMap.map.isRotateGesturesEnabled = false
    }

    private fun setupBottomSheet() {
        bottomSheet = BottomSheetBehavior.from(binding.bottomSheet.root)
        binding.bottomSheet.childLayout.isExpanded = true
        binding.bottomSheet.btnDetailed.visibility = View.GONE
    }

    override fun onClusterAdded(cluster: Cluster) {
        cluster.appearance.setIcon(ImageProvider.fromResource(context, R.drawable.ic_mark))
        cluster.addClusterTapListener(this)
    }

    override fun onClusterTap(cluster: Cluster): Boolean {
        Log.d("TAG", "cluster tap")

        yandexMap.map.move(
            CameraPosition(
                cluster.appearance.geometry,
                yandexMap.map.cameraPosition.zoom + 1, 0.0f, 0.0f
            ),
            Animation(Animation.Type.SMOOTH, 0.5f),
            null
        )
        return true
    }

    override fun onMapObjectTap(mapObject: MapObject, point: Point): Boolean {
        Log.d("TAG", "tap")
        presenter.onPlacemarkTap(mapObject)
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