package com.example.covidpoint.presentation

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.covidpoint.R
import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.data.network.utils.Urls
import com.example.covidpoint.databinding.CountryItemBinding
import com.example.covidpoint.utils.DateConverter
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class ViewDrawer {

    fun drawView(country: CountryEntity, view: CountryItemBinding) {

        view.tvCountryName.text = country.country

        val confirmed = country.confirmed
        val deaths = country.deaths
        val recovered = country.recovered

        view.tvConfirmedNum.text = confirmed.toString()
        view.tvDeathsNum.text = deaths.toString()
        view.tvRecoveredNum.text = recovered.toString()

        val sum = confirmed + deaths + recovered

        view.pbConfirmed.progress = progressValue(confirmed, sum)
        view.pbDeaths.progress = progressValue(deaths, sum)
        view.pbRecovered.progress = progressValue(recovered, sum)

        val flagUrl: String =
            String.format(
                Urls.COUNTRY_FLAG_URL,
                country.countryCode.lowercase()
            )

        setFlagIntoIV(view.ivFlag, flagUrl)

        country.confirmedStats?.let {

            val dynamicConfirmedKeys: List<String> = it.keys.toList()
            val dynamicConfirmedValues: List<Int> = it.values.toList()

            view.tvDateStart.text =
                DateConverter.convertDate(dynamicConfirmedKeys[dynamicConfirmedKeys.size - 31])
            view.tvDateEnd.text =
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
            barDataSet.color = ContextCompat.getColor(view.root.context, R.color.column_graph)

            val data = BarData(barDataSet)
            val graph = view.confirmedGraph

            setupGraph(graph, data)
            view.confirmedGraph.visibility = View.VISIBLE

        } ?: run { view.confirmedGraph.visibility = View.GONE }
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
}