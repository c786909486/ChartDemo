package com.example.ckz.chartdemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/21.
 */

public class LineChartManager {

    private LineChart lineChart;
    private Context context;

    public LineChartManager(Context context, LineChart lineChart){
        this.context = context;
        this.lineChart = lineChart;
        initLineChart();
    }

    private void initLineChart() {
        Description description =new Description();
        description.setText("");
        description.setTextColor(Color.RED);
        description.setTextSize(20);
        lineChart.setDescription(description);//设置图表描述信息
        lineChart.setNoDataText("没有数据熬");//没有数据时显示的文字
        lineChart.setNoDataTextColor(Color.BLUE);//没有数据时显示文字的颜色
        lineChart.setDrawGridBackground(false);//chart 绘图区后面的背景矩形将绘制
        lineChart.setDrawBorders(false);//禁止绘制图表边框的线
        lineChart.animateX(500);
        //lineChart.setBorderColor(); //设置 chart 边框线的颜色。
        //lineChart.setBorderWidth(); //设置 chart 边界线的宽度，单位 dp。
        //lineChart.setLogEnabled(true);//打印日志
        //lineChart.notifyDataSetChanged();//刷新数据
        //lineChart.invalidate();//重绘

        setLegend();
    }


    public void addData(List<String> tables, List<String> xLables, List<List<Float>> yValues, int[] colors) {

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        for (int i=0;i<tables.size();i++){

            List<Entry> value = new ArrayList<>();

            for (int j = 0;j<xLables.size();j++){
                Entry entry = new Entry(j,yValues.get(i).get(j));
                value.add(entry);
            }

            LineDataSet set = new LineDataSet(value,tables.get(i));

            set.setColor(colors[i%colors.length]);
            set.setCircleColor(colors[i%colors.length]);
            set.setLineWidth(1f);//设置线宽
            set.setCircleRadius(3f);//设置焦点圆心的大小
            set.enableDashedHighlightLine(10f, 5f, 0f);//点击后的高亮线的显示样式
            set.setHighlightLineWidth(1f);//设置点击交点后显示高亮线宽
            set.setHighlightEnabled(true);//是否禁用点击高亮线
            set.setHighLightColor(colors[i]);//设置点击交点后显示交高亮线的颜色
            set.setValueTextSize(9f);//设置显示值的文字大小
            set.setDrawFilled(false);//设置禁用范围背景填充

            //格式化显示数据
            final DecimalFormat mFormat = new DecimalFormat("###,###,##0");
            set.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return mFormat.format((int)value);
                }
            });
            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.fade_red);
                set.setFillDrawable(drawable);//设置范围背景填充
            } else {
                set.setFillColor(Color.BLACK);
            }
            dataSets.add(set);

        }
//            //创建LineData对象 属于LineChart折线图的数据集合
        LineData data = new LineData(dataSets);
        // 添加到图表中
        lineChart.setData(data);
        //绘制图表
        lineChart.invalidate();

        setX(xLables);
        setY();
//        addMarker();

//        }
    }

    private void setX(final List<String> lables){
        XAxis xAxis = lineChart.getXAxis();       //获取x轴线
        xAxis.setDrawAxisLine(true);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setTextSize(12f);//设置文字大小
//        xAxis.setAxisMinimum(0f);//设置x轴的最小值 //`
//        xAxis.setAxisMaximum(30f);//设置最大值 //
//        xAxis.setLabelCount(10);  //设置X轴的显示个数
        xAxis.setAvoidFirstLastClipping(false);//图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
        xAxis.setAxisLineColor(Color.BLACK);//设置x轴线颜色
        xAxis.setAxisLineWidth(0.5f);//设置x轴线宽度
        xAxis.setEnabled(true);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return lables.get((int) value%lables.size());
            }
        });

        xAxis.setGranularity(1f);
    }

    private void setY(){
        YAxis leftAxis = lineChart.getAxisLeft();
        YAxis axisRight = lineChart.getAxisRight();
        leftAxis.enableGridDashedLine(10f, 10f, 0f);  //设置Y轴网格线条的虚线，参1 实线长度，参2 虚线长度 ，参3 周期
        leftAxis.setGranularity(20f); // 网格线条间距
        axisRight.setEnabled(false);   //设置是否使用 Y轴右边的
        leftAxis.setEnabled(true);     //设置是否使用 Y轴左边的
        leftAxis.setGridColor(Color.parseColor("#7189a9"));  //网格线条的颜色
        leftAxis.setDrawLabels(true);        //是否显示Y轴刻度
        leftAxis.setStartAtZero(true);        //设置Y轴数值 从零开始
        leftAxis.setDrawGridLines(true);      //是否使用 Y轴网格线条
        leftAxis.setTextSize(12f);            //设置Y轴刻度字体
        leftAxis.setTextColor(Color.BLACK);   //设置字体颜色
        leftAxis.setAxisLineColor(Color.BLACK); //设置Y轴颜色
        leftAxis.setAxisLineWidth(0.5f);
        leftAxis.setDrawAxisLine(true);//是否绘制轴线
//        leftAxis.setMinWidth(0f);
        leftAxis.setMaxWidth(200f);
        leftAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
    }

    private void setLegend(){
        Legend l = lineChart.getLegend();//图例
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);//设置图例的位置
        l.setTextSize(10f);//设置文字大小
        l.setForm(Legend.LegendForm.LINE);//正方形，圆形或线
        l.setFormSize(20f); // 设置Form的大小
        l.setWordWrapEnabled(false);//是否支持自动换行 目前只支持BelowChartLeft, BelowChartRight, BelowChartCenter

    }

//    private void addMarker(){
//        MyMarkerView mv = new MyMarkerView(context, R.layout.custom_marker_view);
//        mv.setChartView(lineChart);
//        lineChart.setMarker(mv);
//    }
//
//    public class MyMarkerView extends MarkerView {
//
//        private TextView tvContent;
//
//        public MyMarkerView(Context context, int layoutResource) {
//            super(context, layoutResource);
//
//            tvContent= (TextView) findViewById(R.id.tvContent);
//        }
//
//        @Override
//        public void refreshContent(Entry e, Highlight highlight) {
//
//            if (e instanceof CandleEntry) {
//
//                CandleEntry ce = (CandleEntry) e;
//
//                tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
//            } else {
//
//                tvContent.setText("" + Utils.formatNumber(e.getY(), 0, true));
//            }
//
//            super.refreshContent(e, highlight);
//        }
//
//        @Override
//        public MPPointF getOffset() {
//            return new MPPointF(-(getWidth() / 2), -getHeight());
//        }
//    }


}
