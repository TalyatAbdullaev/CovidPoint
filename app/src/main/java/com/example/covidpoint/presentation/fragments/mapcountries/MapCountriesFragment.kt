package com.example.covidpoint.presentation.fragments.mapcountries

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.covidpoint.R
import com.example.covidpoint.R.drawable
import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.data.network.utils.Urls
import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.databinding.FragmentCountiresMapBinding
import com.example.covidpoint.di.App
import com.example.covidpoint.utils.AppUtils
import com.example.covidpoint.utils.DateConverter
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.*
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider


class MapCountriesFragment : MvpAppCompatFragment(), MapCountriesInterface {
    private var _binding: FragmentCountiresMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var yandexMap: MapView
    private val placemarksList = arrayListOf<PlacemarkMapObject>()

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

        setupBottomSheet()

        yandexMap = binding.yandexMap
        yandexMap.map.move(
            CameraPosition(Point(39.32783121110484, 29.01118537580683), 3.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0F),
            null
        )

        val tapListener = MapObjectTapListener { p0, p1 ->
            Log.d("TAG", "tap")

            val userData = p0.userData as UserData
            val countryId = userData.data.getValue(AppUtils.ID_KEY).toInt()
            presenter.getCountryStatistic(countryId)

            true
        }

        yandexMap.map.mapObjects.addTapListener(tapListener)
    }

    override fun showCountryStatistic(country: Country) {

        val bottomSheetView = binding.bottomSheet
        val bottomSheet = BottomSheetBehavior.from<CardView>(bottomSheetView.root)
        bottomSheet.state = STATE_EXPANDED

        bottomSheetView.tvCountryName.text = country.country

        val confirmed = country.latest.confirmed
        val deaths = country.latest.deaths
        val recovered = country.latest.recovered

        bottomSheetView.tvConfirmedNum.text = confirmed.toString()
        bottomSheetView.tvDeathsNum.text = deaths.toString()
        bottomSheetView.tvRecoveredNum.text = recovered.toString()

        val sum = confirmed + deaths + recovered

        bottomSheetView.pbConfirmed.progress = progressValue(confirmed, sum)
        bottomSheetView.pbDeaths.progress = progressValue(deaths, sum)
        bottomSheetView.pbRecovered.progress = progressValue(recovered, sum)

        val flagUrl: String =
            String.format(
                Urls.COUNTRY_FLAG_URL,
                country.countryCode.lowercase()
            )

        setFlagIntoIV(bottomSheetView.ivFlag, flagUrl)

        val dynamicConfirmedKeys: List<String> = country.timelines.let {
            it!!.confirmed.timeline.keys.toList()
        }
        val dynamicConfirmedValues: List<Int> = country.timelines.let {
            it!!.confirmed.timeline.values.toList()
        }

        bottomSheetView.tvDateStart.text =
            DateConverter.convertDate(dynamicConfirmedKeys[dynamicConfirmedKeys.size - 31])
        bottomSheetView.tvDateEnd.text =
            DateConverter.convertDate(dynamicConfirmedKeys[dynamicConfirmedKeys.size - 1])

        val entries = arrayListOf<BarEntry>()

        for (i in 1..30) {
            entries.add(
                BarEntry(
                    i.toFloat(),
                    dynamicConfirmedValues[dynamicConfirmedValues.size - 31 + i].toFloat()
                )
            )
        }

        val barDataSet = BarDataSet(entries, "")
        barDataSet.setDrawValues(false)
        barDataSet.color = ContextCompat.getColor(requireContext(), R.color.column_graph)

        val data = BarData(barDataSet)
        val graph = binding.bottomSheet.confirmedGraph

        setupGraph(graph, data)
    }

    private fun setupGraph(graph: BarChart, data: BarData) {

        data.barWidth = 0.9f
        graph.data = data

        graph.setTouchEnabled(false)
        graph.isClickable = false
        graph.isDoubleTapToZoomEnabled = false
        graph.isDoubleTapToZoomEnabled = false

        graph.setDrawBorders(false)
        graph.setDrawGridBackground(false)

        graph.description.isEnabled = false
        graph.legend.isEnabled = false

        graph.axisLeft.setDrawGridLines(false)
        graph.axisLeft.setDrawLabels(false)
        graph.axisLeft.setDrawAxisLine(false)

        graph.xAxis.setDrawGridLines(false)
        graph.xAxis.setDrawLabels(false)
        graph.xAxis.setDrawAxisLine(false)

        graph.axisRight.setDrawGridLines(false)
        graph.axisRight.setDrawLabels(false)
        graph.axisRight.setDrawAxisLine(false)
        graph.invalidate()
    }

    override fun showCountries(countries: List<CountryEntity>) {

        val pointCollection = yandexMap.map.mapObjects.addClusterizedPlacemarkCollection {
            it.appearance.setIcon(ImageProvider.fromResource(context, drawable.ic_mark))
            it.addClusterTapListener {

                true
            }
        }

        countries.forEach {
            val point = Point(it.latitude, it.longitude)
            val data = UserData(mapOf(AppUtils.ID_KEY to it.id.toString()))

            val placemark = pointCollection.addPlacemark(point)
            placemark.setIcon(ImageProvider.fromResource(context, drawable.ic_mark))
            placemark.userData = data
            placemarksList.add(placemark)
        }

        pointCollection.clusterPlacemarks(60.0, 15)
    }

    private fun setupBottomSheet() {
        binding.bottomSheet.childLayout.isExpanded = true
        binding.bottomSheet.btnDetailed.visibility = View.GONE
    }

    private fun setFlagIntoIV(imageView: ImageView, photoUrl: String) {
        Glide.with(imageView.context)
            .asBitmap()
            .load(photoUrl)
            .circleCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

    private fun progressValue(value: Int, sum: Int): Int {
        val value1 = value.toDouble()
        val value2 = sum.toDouble()

        val progress: Double = value1 / value2 * 100
        return progress.toInt()
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