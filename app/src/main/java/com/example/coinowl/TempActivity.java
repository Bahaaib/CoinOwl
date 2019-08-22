package com.example.coinowl;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import com.example.coinowl.Utils.CurrencyMarkerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.Random;

public class TempActivity extends AppCompatActivity {

    private LineChart mChart;
    private ArrayList<Entry> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        setupChartView();
        addDummyEntries();
        setData();

    }

    private void setupChartView() {
        MarkerView markerView = new CurrencyMarkerView(this, R.layout.chart_marker_view);

        mChart = findViewById(R.id.chart);
        mChart.setTouchEnabled(true);
        mChart.setPinchZoom(true);

        //Remove Labels
        mChart.getDescription().setText("");
        mChart.getLegend().setEnabled(false);

        markerView.setChartView(mChart);
        mChart.setMarker(markerView);

        mChart.setDrawGridBackground(false);

        //Set Animation
        mChart.animateX(1000);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setTouchEnabled(true);

        mChart.setHighlightPerDragEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        //Remove all Guidelines
        mChart.getXAxis().setDrawGridLines(false);
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getAxisRight().setDrawGridLines(false);
        mChart.getAxisLeft().setDrawAxisLine(false);
        mChart.getAxisLeft().setDrawLabels(false);
        mChart.getAxisRight().setDrawAxisLine(false);
        mChart.getAxisRight().setDrawLabels(false);
        mChart.getXAxis().setDrawLabels(false);
        mChart.getXAxis().setEnabled(false);

    }

    private void addDummyEntries() {
        values = new ArrayList<>();
        values.add(new Entry(1, generateRandomValue()));
        values.add(new Entry(2, generateRandomValue()));
        values.add(new Entry(3, generateRandomValue()));
        values.add(new Entry(4, generateRandomValue()));
        values.add(new Entry(5, generateRandomValue()));
        values.add(new Entry(6, generateRandomValue()));
        values.add(new Entry(7, generateRandomValue()));
        values.add(new Entry(8, generateRandomValue()));
        values.add(new Entry(9, generateRandomValue()));
        values.add(new Entry(10, generateRandomValue()));

        values.add(new Entry(11, generateRandomValue()));
        values.add(new Entry(12, generateRandomValue()));
        values.add(new Entry(13, generateRandomValue()));
        values.add(new Entry(14, generateRandomValue()));
        values.add(new Entry(15, generateRandomValue()));
        values.add(new Entry(16, generateRandomValue()));
        values.add(new Entry(17, generateRandomValue()));
        values.add(new Entry(18, generateRandomValue()));
        values.add(new Entry(19, generateRandomValue()));
        values.add(new Entry(20, generateRandomValue()));

        values.add(new Entry(21, generateRandomValue()));
        values.add(new Entry(22, generateRandomValue()));
        values.add(new Entry(23, generateRandomValue()));
        values.add(new Entry(24, generateRandomValue()));
        values.add(new Entry(25, generateRandomValue()));
        values.add(new Entry(26, generateRandomValue()));
        values.add(new Entry(27, generateRandomValue()));
        values.add(new Entry(28, generateRandomValue()));
        values.add(new Entry(29, generateRandomValue()));
        values.add(new Entry(30, generateRandomValue()));
    }

    private void setData(){
        LineDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();

        } else {
            set1 = new LineDataSet(values, "Currency Values");
            set1.setDrawIcons(false);
            set1.setDrawCircles(false);
            set1.setDrawValues(false);
            set1.setColor(getResources().getColor(R.color.colorPrimary));
            set1.setLineWidth(1f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);

            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.chart_gradient);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.DKGRAY);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            mChart.setData(data);
        }
    }

    private int generateRandomValue(){
        Random random = new Random();
        return random.nextInt(101);
    }

}
