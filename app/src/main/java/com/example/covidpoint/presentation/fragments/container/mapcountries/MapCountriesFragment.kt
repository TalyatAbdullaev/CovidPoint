package com.example.covidpoint.presentation.fragments.container.mapcountries

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import com.example.covidpoint.R
import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.databinding.FragmentCountiresMapBinding
import com.example.covidpoint.databinding.MarkerItemBinding
import com.example.covidpoint.di.App
import com.example.covidpoint.presentation.fragments.container.BasePresenterInterface
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

class MapCountriesFragment : MvpAppCompatFragment(), BasePresenterInterface,
    ClusterListener, ClusterTapListener, MapObjectTapListener {
    private var _binding: FragmentCountiresMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var yandexMap: MapView
    private lateinit var bottomSheet: BottomSheetBehavior<CardView>
    private var progressAlertDialog: AlertDialog? = null

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

        getLocation()
        setupYandexMap(AppUtils.DEFAULT_POINT)
        setupBottomSheet()
    }

    override fun showCountryStatistic(country: CountryEntity) {
        progressAlertDialog?.dismiss()
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

    override fun showAlertDialog(message: String, countryId: Int) {
        progressAlertDialog?.dismiss()

        AlertDialog.Builder(context)
            .setTitle(getString(R.string.label_error))
            .setMessage(message)
            .setPositiveButton(
                getString(R.string.label_repeat),
                object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        showProgressBar()
                        presenter.onPositiveButtonClick(countryId)
                    }
                })
            .setNegativeButton(
                getString(R.string.label_close),
                object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {

                    }
                })
            .show()
    }

    override fun showProgressBar() {
        progressAlertDialog = AlertDialog.Builder(context)
            .setCancelable(false)
            .setView(ProgressBar(context))
            .show()

        progressAlertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun setupYandexMap(point: Point) {
        yandexMap = binding.yandexMap
        yandexMap.map.move(
            CameraPosition(point, 5.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0.5F),
            null
        )
        yandexMap.map.isRotateGesturesEnabled = false
    }

    private fun setupBottomSheet() {
        bottomSheet = BottomSheetBehavior.from(binding.bottomSheet.root)
        binding.bottomSheet.childLayout.isExpanded = true
        binding.bottomSheet.btnDetailed.visibility = View.GONE
    }

    private fun getLocation() {
        val manager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            ActivityCompat.requestPermissions(requireActivity(), permissions, 1000)
        } else {
            manager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0L,
                0F
            ) { location -> setupYandexMap(Point(location.latitude, location.longitude)) }
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}