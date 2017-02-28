package com.jonat.graphingsensor;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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
import org.achartengine.tools.PanListener;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private XYSeries series;
    private GraphicalView chartView;
    private double counter = 0;
    private XYMultipleSeriesRenderer mRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        //this is one line of data
        series = new XYSeries("Accelerometer");

        //have to add all the line of data
        XYMultipleSeriesDataset mSeries = new XYMultipleSeriesDataset();
        mSeries.addSeries(series);

        //line graphical settings
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setLineWidth(2);
        renderer.setColor(Color.RED);
        renderer.setPointStyle(PointStyle.CIRCLE);

        //add the settings to a multi renderer
        mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);

        //chart settings
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // Disable Pan on two axis
        mRenderer.setPanEnabled(false,false);
        mRenderer.setYAxisMax(35);
        mRenderer.setYAxisMin(-35);
        mRenderer.setShowGrid(true); // we show the grid

        //the graph view
        chartView = ChartFactory.
                getLineChartView(getApplicationContext(),mSeries,mRenderer);
        chartView.addPanListener(new PanListener() {
            @Override
            public void panApplied() {

            }
        });

        //handle to the linearlayout
        LinearLayout chartLyt = (LinearLayout) findViewById(R.id.chart);

        //add the chart to the view
        chartLyt.addView(chartView,0);

    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, accelerometer,1000);
        if(chartView != null){
            chartView.repaint();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        synchronized (this) {
            double xA = sensorEvent.values[0] + counter;
            double yA = sensorEvent.values[1];
//            mRenderer.setXAxisMin(counter);
            counter++;
            series.add(xA, yA);
            chartView.repaint();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
