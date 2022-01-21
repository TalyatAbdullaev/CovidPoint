package com.iwgroup.covidpoint.presentation.fragments.container.mapcountries

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import com.iwgroup.covidpoint.R
import com.iwgroup.covidpoint.data.database.CountryEntity
import com.iwgroup.covidpoint.databinding.FragmentCountriesMapBinding
import com.iwgroup.covidpoint.databinding.MarkerItemBinding
import com.iwgroup.covidpoint.di.App
import com.iwgroup.covidpoint.utils.AppUtils
import com.iwgroup.covidpoint.utils.extentions.drawCountryIntoView
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
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
    private var _binding: FragmentCountriesMapBinding? = null
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
    ): View {
        _binding = FragmentCountriesMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.graph.inject(this)
        MapKitFactory.initialize(context)

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupYandexMap(AppUtils.DEFAULT_POINT)
        setupBottomSheet()
        getPermissions()
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

    override fun hideProgressBar() {
        progressAlertDialog?.dismiss()
    }

    private fun setupYandexMap(point: Point) {
        yandexMap = binding.yandexMap
        yandexMap.map.isRotateGesturesEnabled = false
        moveCameraToPoint(point, 4.5F)
    }

    private fun moveCameraToPoint(point: Point, zoom: Float) {
        binding.yandexMap.map.move(
            CameraPosition(point, zoom, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0.5F),
            null
        )
    }

    private fun setupBottomSheet() {
        bottomSheet = BottomSheetBehavior.from(binding.bottomSheet.root)
        binding.bottomSheet.childLayout.isExpanded = true
        binding.bottomSheet.btnDetailed.visibility = View.GONE
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                if (it != null) {
                    val point = Point(it.latitude, it.longitude)
                    moveCameraToPoint(point, yandexMap.map.cameraPosition.zoom)
                }
            }
    }

    private fun getPermissions() {
        Dexter.withContext(context)
            .withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            getLocation()
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) { token?.continuePermissionRequest() }
            })
            .check()
    }

    override fun onClusterAdded(cluster: Cluster) {
        cluster.appearance.setIcon(ImageProvider.fromResource(context, R.drawable.ic_mark))
        cluster.addClusterTapListener(this)
    }

    override fun onClusterTap(cluster: Cluster): Boolean {
        Log.d("TAG", "cluster tap")
        moveCameraToPoint(cluster.appearance.geometry, yandexMap.map.cameraPosition.zoom + 1)
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