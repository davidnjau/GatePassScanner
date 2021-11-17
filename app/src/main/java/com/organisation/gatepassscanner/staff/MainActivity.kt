package com.organisation.gatepassscanner.staff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.organisation.gatepassscanner.R
import com.jjoe64.graphview.series.LineGraphSeries


class MainActivity : AppCompatActivity() {

    private lateinit var graphView: GraphView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        graphView = findViewById(R.id.idGraphView);
        val series: LineGraphSeries<DataPoint> = LineGraphSeries(
            arrayOf( // on below line we are adding
                // each point on our x and y axis.

                DataPoint(0.0, 1.0),
                DataPoint(1.0, 3.0),
                DataPoint(2.0, 4.0),
                DataPoint(3.0, 9.0),
                DataPoint(4.0, 6.0),
                DataPoint(5.0, 3.0),
                DataPoint(6.0, 6.0),
                DataPoint(7.0, 1.0),
                DataPoint(8.0, 2.0)
            )
        )

        // after adding data to our line graph series.
        // on below line we are setting
        // title for our graph view.
        graphView.title = "My Graph View";

        // on below line we are setting
        // text color to our graph view.
        graphView.titleColor = R.color.purple_200;

        // on below line we are setting
        // our title text size.
//        graphView.setTitleTextSize(18);

        // on below line we are adding
        // data series to our graph view.
        graphView.addSeries(series);

    }


}