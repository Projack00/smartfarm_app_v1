package com.example.kuhas.smartfarm_04.page;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kuhas.smartfarm_04.models.Graph_DHT_;
import com.example.kuhas.smartfarm_04.models.PointValue;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.example.kuhas.smartfarm_04.R;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class Grapg extends AppCompatActivity implements OnChartGestureListener, OnChartValueSelectedListener {

    public FirebaseDatabase database;
    public DatabaseReference reference;

    public GraphView graph;
    public LineGraphSeries seriesTemp, seriesHumid;
    private LineChart mChart;

    ArrayList<String> arrTime = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_grapg);

        getSupportActionBar().setTitle("กราฟ");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("logDHT");

//        setGraph_ver01();
        setGraph_v_2();

    }

    private void setGraph_v_2() {

        mChart = (LineChart) findViewById(R.id.linechart);
        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        setData();
    }

    private void setData() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int index = 0;
                ArrayList<Entry> Entrytemperature = new ArrayList<Entry>();
                ArrayList<Entry> Entryhumidity = new ArrayList<Entry>();
                ArrayList<String> xVals = new ArrayList<String>();
                ArrayList<Integer> arrTemp = new ArrayList<>();


                for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()) {
                    Graph_DHT_ graphDht = myDataSnapshot.getValue(Graph_DHT_.class);

                    Entrytemperature.add(new Entry(graphDht.getTemperature(), index));
                    Entryhumidity.add(new Entry(graphDht.getHumidity(), index));

                    xVals.add(graphDht.getTime());

                    index++;
                }


                LineDataSet temp, humidity;

                if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {

                    Toast.makeText(Grapg.this, "if mChart success", Toast.LENGTH_SHORT).show();


                    temp = (LineDataSet) mChart.getData().getDataSetByIndex(0);
                    humidity = (LineDataSet) mChart.getData().getDataSetByIndex(1);

                    mChart.getData().notifyDataChanged();
                    mChart.notifyDataSetChanged();
                } else {
                    Toast.makeText(Grapg.this, "in Else mChart success", Toast.LENGTH_SHORT).show();

                    // create a dataset and give it a type
                    temp = new LineDataSet(Entrytemperature, "temperature");

                    temp.setColor(Color.RED);
                    temp.setCircleColor(Color.RED);

                    temp.setValueTextSize(10);
                    temp.setLineWidth(3);
                    temp.setCircleRadius(4);

                    humidity = new LineDataSet(Entryhumidity, "humidity");

                    humidity.setColor(Color.BLUE);
                    humidity.setCircleColor(Color.BLUE);

                    humidity.setValueTextSize(10);
                    humidity.setLineWidth(3);
                    humidity.setCircleRadius(4);
                }

                ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                dataSets.add(temp);
                dataSets.add(humidity);

                LineData data = new LineData(xVals, dataSets);

                mChart.setData(data);
                mChart.invalidate();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setGraph_ver01() {

//        graph = (GraphView) findViewById(R.id.graphView);
        seriesTemp = new LineGraphSeries();
        seriesHumid = new LineGraphSeries();

        graph.addSeries(seriesTemp);
        graph.addSeries(seriesHumid);
        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling

        seriesHumid.setDrawDataPoints(true);
        seriesHumid.setDataPointsRadius(10);
        seriesHumid.setThickness(8);

        seriesTemp.setDrawDataPoints(true);
        seriesTemp.setDataPointsRadius(10);
        seriesTemp.setThickness(8);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataPoint[] dptemp = new DataPoint[(int) dataSnapshot.getChildrenCount()];
                DataPoint[] dphumid = new DataPoint[(int) dataSnapshot.getChildrenCount()];

                int index = 0;

                for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()) {
                    Graph_DHT_ conGraphDht = myDataSnapshot.getValue(Graph_DHT_.class);
                    dptemp[index] = new DataPoint(index, conGraphDht.getTemperature());
                    dphumid[index] = new DataPoint(index, conGraphDht.getHumidity());

                    index++;
                }
                seriesTemp.setTitle("อุณหภูมิ");
                seriesTemp.resetData(dptemp);
                seriesTemp.setColor(Color.RED);

                seriesHumid.setTitle("ความชื้น");
                seriesHumid.resetData(dphumid);
                seriesHumid.setColor(Color.BLUE);

                graph.getLegendRenderer().setVisible(true);
                graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
