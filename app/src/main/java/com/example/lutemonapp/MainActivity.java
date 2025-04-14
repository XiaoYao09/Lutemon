package com.example.lutemonapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView txtHomeCount, txtTrainingCount, txtBattleCount;
    private Button btnViewHome, btnViewTraining, btnViewBattle, btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtHomeCount = findViewById(R.id.txtHomeCount);
        txtTrainingCount = findViewById(R.id.txtTrainingCount);
        txtBattleCount = findViewById(R.id.txtBattleCount);

        btnViewHome = findViewById(R.id.btnViewHome);
        btnViewTraining = findViewById(R.id.btnViewTraining);
        btnViewBattle = findViewById(R.id.btnViewBattle);
        btnCreate = findViewById(R.id.btnCreate);

        // 更新首页数量显示
        updateCounts();

        // 点击跳转
        btnCreate.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateLutemonActivity.class);
            startActivity(intent);
        });

        btnViewHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, LutemonListActivity.class);
            intent.putExtra("area", "home");
            startActivity(intent);
        });

        btnViewTraining.setOnClickListener(v -> {
            Intent intent = new Intent(this, TrainingActivity.class);
            startActivity(intent);
        });

        btnViewBattle.setOnClickListener(v -> {
            Intent intent = new Intent(this, BattleActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCounts(); // 每次回来都刷新统计
    }

    private void updateCounts() {
        int home = Storage.getInstance().getLutemonsByArea("home").size();
        int training = Storage.getInstance().getLutemonsByArea("training").size();
        int battle = Storage.getInstance().getLutemonsByArea("battle").size();

        txtHomeCount.setText("You have " + home + " Lutemons at home");
        txtTrainingCount.setText("You have " + training + " Lutemon training");
        txtBattleCount.setText("You have " + battle + " Lutemons ready");
    }
}


