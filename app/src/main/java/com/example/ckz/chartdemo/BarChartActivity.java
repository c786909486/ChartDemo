package com.example.ckz.chartdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BarChartActivity extends AppCompatActivity {

    private BarChart mBarChart;
    private String[] lableArray = new String[]{"低压","平均压","高压"};
    private String[] titleArray = new String[]{"09-20","09-24","09-25","09-30","10-01","10-12","11-02"};
    private Float[] va1 = new Float[]{11f,20f,5f,14f,6f,19f,23f};
    private Float[] va2 = new Float[]{28f,40f,31f,37f,44f,38f,40f};
    private Float[] va3 = new Float[]{60f,70f,54f,66f,61f,51f,75f};
    List<String> lables = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchart);
        initView();
    }

    private void initView() {
        mBarChart = (BarChart) findViewById(R.id.bar_chart);

        List<String> titles = new ArrayList<>();
        titles.addAll(Arrays.asList(titleArray));
        lables.addAll(Arrays.asList(lableArray));
        List<List<Float>> yValues = new ArrayList<>();
        List<Float> v1 = new ArrayList<>();
        v1.addAll(Arrays.asList(va1));
        List<Float> v2 = new ArrayList<>();
        v2.addAll(Arrays.asList(va2));
        List<Float> v3 = new ArrayList<>();
        v3.addAll(Arrays.asList(va3));
        yValues.add(v1);
        yValues.add(v2);
        yValues.add(v3);

        List<Float> xValue = new ArrayList<>();
        for (int i=0;i<titles.size();i++){
            xValue.add((float) i);
        }
        int[] colors = new int[]{Color.RED,Color.BLUE,Color.GREEN};
        List<Integer> color = new ArrayList<>();
        for (int c :colors){
            color.add(c);
        }
        BarChartManager manager = new BarChartManager(mBarChart);
        manager.showBarChart(xValue,yValues,lables,color);
        manager.setXTitle(titles);
        manager.setXAxis(xValue.size(),0f,xValue.size());
    }
}
