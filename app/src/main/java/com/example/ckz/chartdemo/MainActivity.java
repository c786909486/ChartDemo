package com.example.ckz.chartdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LineChart lineChart;
    private LineChartManager manager;

    private String[] titles = new String[]{"09-20","09-24","09-25","09-30"};
    List<String> xTitle = new ArrayList<>();
    List<Float> xVlaue = new ArrayList<>();
    List<List<Float>> yvalues = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        lineChart = (LineChart) findViewById(R.id.line_chart);
        manager = new LineChartManager(lineChart);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<titles.length;i++){
                    xTitle.add(titles[i]);
                    xVlaue.add((float) i);
                }

                for (int i=0;i<3;i++){
                    List<Float> yValue = new ArrayList<>();
                    for (int j=0;j<8;j++){
                        float v = i*j;
                        yValue.add(v);
                    }
                    yvalues.add(yValue);
                }
            }
        }).start();
        Integer[] colors = new Integer[]{Color.RED,Color.GREEN,Color.BLACK,Color.YELLOW};
        List<Integer> color  = new ArrayList<>();
        color.addAll(Arrays.asList(colors));
        manager.showLineChart(xVlaue,yvalues,xTitle,color);
    }
}
