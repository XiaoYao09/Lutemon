package com.example.lutemonapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView txtHomeCount, txtTrainingCount, txtBattleCount;

    @Override
    protected void onResume() {
        super.onResume();
        updateCounts();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtHomeCount = findViewById(R.id.txtHomeCount);
        txtTrainingCount = findViewById(R.id.txtTrainingCount);
        txtBattleCount = findViewById(R.id.txtBattleCount);

        Button btnViewHome = findViewById(R.id.btnViewHome);
        Button btnViewTraining = findViewById(R.id.btnViewTraining);
        Button btnViewBattle = findViewById(R.id.btnViewBattle);
        Button btnCreate = findViewById(R.id.btnCreate);
        Button btnViewStatistics = findViewById(R.id.btnViewStatistics);

        btnViewHome.setOnClickListener(v -> startActivity(new Intent(this, LutemonListActivity.class)));

        btnViewTraining.setOnClickListener(v -> startActivity(new Intent(this, TrainingActivity.class)));

        btnViewBattle.setOnClickListener(v -> startActivity(new Intent(this, BattleActivity.class)));

        btnCreate.setOnClickListener(v -> startActivity(new Intent(this, CreateLutemonActivity.class)));

        btnViewStatistics.setOnClickListener(v -> startActivity(new Intent(this, StatisticsActivity.class)));

        updateCounts();
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


