package com.iwgroup.covidpoint.utils.extentions

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.iwgroup.covidpoint.R
import com.iwgroup.covidpoint.data.database.CountryEntity
import com.iwgroup.covidpoint.data.network.utils.Urls
import com.iwgroup.covidpoint.databinding.CountryItemBinding
import com.iwgroup.covidpoint.utils.DateConverter
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

fun CountryItemBinding.drawCountryIntoView(country: CountryEntity) {

    tvCountryName.text = country.country

    val confirmed = country.confirmed
    val deaths = country.deaths
    val recovered = country.recovered

    tvConfirmedNum.text = confirmed.toString()
    tvDeathsNum.text = deaths.toString()
    tvRecoveredNum.text = recovered.toString()

    val sum = confirmed + deaths + recovered

    pbConfirmed.progress = progressValue(confirmed, sum)
    pbDeaths.progress = progressValue(deaths, sum)
    pbRecovered.progress = progressValue(recovered, sum)

    val flagUrl: String =
        String.format(
            Urls.COUNTRY_FLAG_URL,
            country.countryCode.lowercase()
        )

    setFlagIntoIV(ivFlag, flagUrl)

    country.confirmedStats?.let {

        val dynamicConfirmedKeys: List<String> = it.keys.toList()
        val dynamicConfirmedValues: List<Int> = it.values.toList()

        tvDateStart.text =
            DateConverter.convertDate(dynamicConfirmedKeys[dynamicConfirmedKeys.size - 31])
        tvDateEnd.text =
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
        barDataSet.color = ContextCompat.getColor(root.context, R.color.column_graph)

        val data = BarData(barDataSet)
        val graph = confirmedGraph

        setupGraph(graph, data)
        confirmedGraph.visibility = View.VISIBLE

    } ?: run { confirmedGraph.visibility = View.GONE }
}

private fun setupGraph(graph: BarChart, data: BarData) {

    data.barWidth = 0.9f
    graph.data = data

    with(graph) {

        setTouchEnabled(false)
        isClickable = false
        isDoubleTapToZoomEnabled = false
        isDoubleTapToZoomEnabled = false

        setDrawBorders(false)
        setDrawGridBackground(false)

        description.isEnabled = false
        legend.isEnabled = false

        axisLeft.setDrawGridLines(false)
        axisLeft.setDrawLabels(false)
        axisLeft.setDrawAxisLine(false)

        xAxis.setDrawGridLines(false)
        xAxis.setDrawLabels(false)
        xAxis.setDrawAxisLine(false)

        axisRight.setDrawGridLines(false)
        axisRight.setDrawLabels(false)
        axisRight.setDrawAxisLine(false)
        invalidate()
    }
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