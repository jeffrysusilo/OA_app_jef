package com.example.sinauopencvkotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.core.cartesian.series.Line
import com.anychart.data.Mapping
import com.anychart.data.Set
import com.anychart.enums.Anchor
import com.anychart.enums.MarkerType
import com.anychart.enums.TooltipPositionMode
import com.anychart.graphics.vector.Stroke
import com.example.sinauopencvkotlin.auth.QnaActivity
import com.example.sinauopencvkotlin.databinding.ActivityChartBinding

class ChartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.chartView.setProgressBar(binding.progressBar)

        val data: ArrayList<Record>? = intent.getParcelableArrayListExtra("romData")
        val cartesian: Cartesian = AnyChart.line()

        cartesian.animation(true)
        cartesian.padding(10.0, 5.0, 5.0, 5.0)

        cartesian.crosshair().enabled(true)
        cartesian.crosshair().yLabel(true)
        cartesian.crosshair().yStroke(null as Stroke?, null, null, null as String?, null as String?)

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
        cartesian.title("Range of Motion")
        cartesian.xAxis(0).title("Time (s)")
        cartesian.yAxis(0).labels().padding(1.0, 1.0, 1.0, 1.0)

        val seriesData: MutableList<DataEntry> = ArrayList()

        data?.forEach {
            seriesData.add(CustomDataEntry(it.sec.toString(), it.angle))
        }

        val set: Set = Set.instantiate()
        set.data(seriesData)

        val drawLine: Mapping = set.mapAs("{ x: 'x', value: 'value' }")

        val series1: Line = cartesian.line(drawLine)
        series1.name("ROM")
        series1.hovered().markers().enabled(true)
        series1.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        series1.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)

        cartesian.legend().enabled(true)
        cartesian.legend().fontSize(13.0)
        cartesian.legend().padding(0.0, 0.0, 10.0, 0.0)

        binding.chartView.setChart(cartesian)

        val maxValue = data?.maxByOrNull { it.angle }?.angle ?: 0.0

        // Button untuk membuka QnA Activity
        binding.buttonQnaActivity.setOnClickListener {
            val intent = Intent(this, QnaActivity::class.java)
            intent.putExtra("maxValue", maxValue)
            startActivity(intent)
        }
    }

    private class CustomDataEntry(x: String, value: Double) : ValueDataEntry(x, value)
}