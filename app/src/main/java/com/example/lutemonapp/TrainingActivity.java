package com.example.lutemonapp;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class TrainingActivity extends AppCompatActivity {

    private ImageView imgLutemon;
    private TextView txtTrainName, txtTrainStats;
    private Button btnTrainNow, btnMoveHome, btnSelect;

    private Lutemon selectedLutemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        imgLutemon = findViewById(R.id.imgLutemon);
        txtTrainName = findViewById(R.id.txtTrainName);
        txtTrainStats = findViewById(R.id.txtTrainStats);
        btnTrainNow = findViewById(R.id.btnTrainExp);
        btnMoveHome = findViewById(R.id.btnMoveHome);
        btnSelect = findViewById(R.id.btnTrainSelect);

        List<Lutemon> trainingLutemons = Storage.getInstance().getLutemonsByArea("training");

        if (trainingLutemons.size() == 0) {
            txtTrainName.setText("No Lutemons in training");
            txtTrainStats.setText("");
            btnTrainNow.setVisibility(View.GONE);
            btnSelect.setVisibility(View.GONE);
            return;
        } else if (trainingLutemons.size() == 1) {
            selectedLutemon = trainingLutemons.get(0);
            showLutemonDetails(selectedLutemon);
            btnTrainNow.setVisibility(View.VISIBLE);
            btnSelect.setVisibility(View.GONE);
        } else {
            btnTrainNow.setVisibility(View.GONE);
            btnSelect.setVisibility(View.VISIBLE);
        }

        btnTrainNow.setOnClickListener(v -> {
            if (selectedLutemon != null) {
                selectedLutemon.gainExperience();
                StatisticsManager.getInstance().incrementTraining(selectedLutemon.getId());
                showLutemonDetails(selectedLutemon);
                Toast.makeText(this, "Training +1", Toast.LENGTH_SHORT).show();
            }
        });

        btnMoveHome.setOnClickListener(v -> {
            if (selectedLutemon != null) {
                Storage.getInstance().moveLutemon(selectedLutemon.getId(), "training", "home");
                Toast.makeText(this, "Moved to Home", Toast.LENGTH_SHORT).show();
                finish(); // 返回上一页
            }
        });

        btnSelect.setOnClickListener(v -> showSelectionDialog());
    }

    private void showSelectionDialog() {
        List<Lutemon> trainingLutemons = Storage.getInstance().getLutemonsByArea("training");
        String[] lutemonNames = new String[trainingLutemons.size()];
        for (int i = 0; i < trainingLutemons.size(); i++) {
            lutemonNames[i] = trainingLutemons.get(i).getName() + " (" + trainingLutemons.get(i).getColor() + ")";
        }

        new AlertDialog.Builder(this)
                .setTitle("Select Lutemon to Train")
                .setItems(lutemonNames, (dialog, which) -> {
                    selectedLutemon = trainingLutemons.get(which);
                    showLutemonDetails(selectedLutemon);
                    btnTrainNow.setVisibility(View.VISIBLE);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showLutemonDetails(Lutemon l) {
        txtTrainName.setText(l.getName() + " (" + l.getColor() + ")");
        txtTrainStats.setText("ATK: " + l.getAttack() + " | DEF: " + l.getDefense() +
                " | HP: " + l.getHealth() + "/" + l.getMaxHealth() +
                " | EXP: " + l.getExperience());
        setLutemonImage(imgLutemon, l.getColor());
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




