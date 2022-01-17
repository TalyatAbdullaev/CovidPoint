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
import com.example.covidpoint.utils.AppUtils
import com.example.covidpoint.utils.extentions.drawCountryIntoView
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

class MapCountriesFragment : MvpAppCompatFragment(), IMapCountriesPresenter,
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

    private fun getLocation() {


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
                            if (ActivityCompat.checkSelfPermission(
                                    requireContext(),
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    requireContext(),
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED
                            ) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return
                            }
                            val manager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                            manager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                0L,
                                0F,
                                object : LocationListener {
                                    override fun onLocationChanged(location: Location) {
                                        val point = Point(location.latitude, location.longitude)
                                        moveCameraToPoint(point, yandexMap.map.cameraPosition.zoom)
                                    }
                                })
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            })
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}