package com.example.lutemonapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class TrainingActivity extends AppCompatActivity {

    private TextView txtTrainName, txtTrainStats;
    private ImageView imgLutemon;
    private Button btnTrain, btnMoveHome;
    private List<Lutemon> trainingLutemons;
    private int currentIndex = 0; // 当前训练的 Lutemon 索引

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        txtTrainName = findViewById(R.id.txtTrainName);
        txtTrainStats = findViewById(R.id.txtTrainStats);
        imgLutemon = findViewById(R.id.imgLutemon);
        btnTrain = findViewById(R.id.btnTrainExp);
        btnMoveHome = findViewById(R.id.btnMoveHome);

        trainingLutemons = Storage.getInstance().getLutemonsByArea("training");

        if (trainingLutemons.isEmpty()) {
            Toast.makeText(this, "No Lutemons in training area.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        showCurrentLutemon();

        btnTrain.setOnClickListener(v -> {
            Lutemon lutemon = trainingLutemons.get(currentIndex);
            lutemon.gainExperience();
            StatisticsManager.getInstance().incrementTrain(lutemon.getId());
            Toast.makeText(this, "+1 Experience", Toast.LENGTH_SHORT).show();
            showCurrentLutemon(); // 更新UI
        });

        btnMoveHome.setOnClickListener(v -> {
            Lutemon lutemon = trainingLutemons.get(currentIndex);
            Storage.getInstance().moveLutemon(lutemon.getId(), "training", "home");
            Toast.makeText(this, "Moved to Home", Toast.LENGTH_SHORT).show();

            trainingLutemons.remove(currentIndex);
            if (trainingLutemons.isEmpty()) {
                finish(); // 没有可训练的了
            } else {
                if (currentIndex >= trainingLutemons.size()) {
                    currentIndex = 0;
                }
                showCurrentLutemon();
            }
        });
    }

    private void showCurrentLutemon() {
        if (trainingLutemons.isEmpty()) return;

        Lutemon l = trainingLutemons.get(currentIndex);
        txtTrainName.setText(l.getName() + " (" + l.getColor() + ")");
        txtTrainStats.setText(
                "ATK: " + l.getAttack() +
                        " | DEF: " + l.getDefense() +
                        " | HP: " + l.getHealth() + "/" + l.getMaxHealth() +
                        " | EXP: " + l.getExperience()
        );

        // 设置图片，根据颜色切换
        switch (l.getColor()) {
            case "White":
                imgLutemon.setImageResource(R.drawable.white);
                break;
            case "Green":
                imgLutemon.setImageResource(R.drawable.green);
                break;
            case "Pink":
                imgLutemon.setImageResource(R.drawable.pink);
                break;
            case "Orange":
                imgLutemon.setImageResource(R.drawable.orange);
                break;
            case "Black":
                imgLutemon.setImageResource(R.drawable.black);
                break;
            default:
                imgLutemon.setImageResource(R.drawable.ic_launcher_foreground);
                break;
        }
    }
}


