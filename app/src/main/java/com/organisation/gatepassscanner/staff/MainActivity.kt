package com.organisation.gatepassscanner.staff

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.cardview.widget.CardView
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.organisation.gatepassscanner.R
import com.jjoe64.graphview.series.LineGraphSeries
import com.organisation.gatepassscanner.helperclass.Formatter
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var graphView: GraphView
    private lateinit var cardViewShowBadge: CardView
    private var formatter = Formatter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        formatter.customMainToolbar(this)
        formatter.customBottomNavigation(this)


        cardViewShowBadge = findViewById(R.id.cardViewShowBadge)
        graphView = findViewById(R.id.idGraphView)

        cardViewShowBadge.setOnClickListener {

            val intent = Intent(this, BadgeDetails::class.java)
            startActivity(intent)

        }

        getGraphDetails()


    }

    private fun getGraphDetails() {


//        val formatter = SimpleDateFormat("dd")
//        val date = Date()
//        val now = formatter.format(date)
//
//        println(now.toDouble())
//        Log.e("-*-*- " , now)

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

        graphView.title = "My Graph View";
        graphView.titleColor = R.color.purple_200;

//        graphView.setTitleTextSize(18);

        graphView.addSeries(series);


    }


}