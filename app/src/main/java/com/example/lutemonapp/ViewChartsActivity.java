package com.example.lutemonapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.charts.Pie;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewChartsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_charts);

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();

        Map<Integer, Integer> wins = StatisticsManager.getInstance().getWinCounts();
        Map<Integer, Integer> battles = StatisticsManager.getInstance().getBattleCounts();
        List<Lutemon> allLutemons = Storage.getInstance().getAllLutemons();

        for (Lutemon l : allLutemons) {
            int id = l.getId();
            String name = l.getName();

            int winCount = wins.getOrDefault(id, 0);
            int battleCount = battles.getOrDefault(id, 0);
            double winRate = battleCount > 0 ? (double) winCount / battleCount * 100 : 0;

            data.add(new ValueDataEntry(name, winRate));
        }

        pie.data(data);
        pie.title("Lutemon Win Rates (%)");
        pie.labels().position("outside");
        pie.innerRadius("40%"); // 设置为圆环图

        anyChartView.setChart(pie);
    }
}


