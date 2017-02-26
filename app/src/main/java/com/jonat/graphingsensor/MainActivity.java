package com.jonat.graphingsensor;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.LineChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this is one line of data
        XYSeries series = new XYSeries("test chart");
        series.add(0, 1);
        series.add(1,2);
        series.add(2,3);
        series.add(3,4);
        series.add(4,5);

        //have to add all the line of data
        XYMultipleSeriesDataset mSeries = new XYMultipleSeriesDataset();
        mSeries.addSeries(series);

        //line graphical settings
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setLineWidth(2);
        renderer.setColor(Color.RED);
        renderer.setDisplayChartValues(true);
        renderer.setPointStyle(PointStyle.CIRCLE);

        //add the settings to a multi renderer
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);

        //chart settings
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // Disable Pan on two axis
        mRenderer.setPanEnabled(false, false);
        mRenderer.setYAxisMax(35);
        mRenderer.setYAxisMin(0);
        mRenderer.setShowGrid(true); // we show the grid

        //the graph view
        GraphicalView chartView = ChartFactory.
                getLineChartView(getApplicationContext(),mSeries,mRenderer);

        //handle to the linearlayout
        LinearLayout chartLyt = (LinearLayout) findViewById(R.id.chart);

        //add the chart to the view
        chartLyt.addView(chartView,0);
    }
}
