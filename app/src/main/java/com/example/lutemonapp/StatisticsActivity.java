package com.example.lutemonapp;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;
import android.content.Intent;
import android.view.View;


public class StatisticsActivity extends AppCompatActivity {

    private TextView txtGeneralStats;
    private LinearLayout layoutLutemonStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        txtGeneralStats = findViewById(R.id.txtGeneralStats);
        layoutLutemonStats = findViewById(R.id.layoutLutemonStats);

        // 显示总统计
        StatisticsManager sm = StatisticsManager.getInstance();
        txtGeneralStats.setText("Total Lutemons Created: " + sm.getTotalCreated() + "\n" +
                "Total Battles: " + sm.getTotalBattles() + "\n" +
                "Total Training Sessions: " + sm.getTotalTraining());

        // 显示每只 Lutemon 的数据
        List<Lutemon> all = Storage.getInstance().getAllLutemons();

        for (Lutemon l : all) {
            LinearLayout box = new LinearLayout(this);
            box.setOrientation(LinearLayout.HORIZONTAL);
            box.setPadding(8, 8, 8, 8);
            box.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            ImageView avatar = new ImageView(this);
            avatar.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            setLutemonImage(avatar, l.getColor());
            box.addView(avatar);

            TextView info = new TextView(this);
            info.setText(
                    l.getName() + " (" + l.getColor() + ")\n" +
                            "Battles: " + sm.getBattles(l.getId()) + "   " +
                            "Wins: " + sm.getWins(l.getId()) + "   " +
                            "Training: " + sm.getTraining(l.getId())
            );
            info.setPadding(16, 0, 0, 0);
            box.addView(info);

            layoutLutemonStats.addView(box);
        }

        // 点击按钮查看图表（可选）
        Button btnViewCharts = findViewById(R.id.btnViewCharts);
        btnViewCharts.setOnClickListener(v -> {
            Intent intent = new Intent(StatisticsActivity.this, ViewChartsActivity.class);
            startActivity(intent);
        });

    }

    private void setLutemonImage(ImageView view, String color) {
        switch (color.toLowerCase()) {
            case "white":
                view.setImageResource(R.drawable.white);
                break;
            case "green":
                view.setImageResource(R.drawable.green);
                break;
            case "pink":
                view.setImageResource(R.drawable.pink);
                break;
            case "orange":
                view.setImageResource(R.drawable.orange);
                break;
            case "black":
                view.setImageResource(R.drawable.black);
                break;
            default:
                view.setImageResource(R.drawable.ic_launcher_foreground);
        }
    }


}
