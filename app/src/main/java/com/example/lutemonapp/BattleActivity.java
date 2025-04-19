package com.example.lutemonapp;

import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;
import androidx.appcompat.app.AlertDialog;

public class BattleActivity extends AppCompatActivity {

    private TextView txtL1Name, txtL2Name, txtL1Stats, txtL2Stats, txtBattleLog;
    private ImageView imgL1, imgL2, imgSword;
    private Button btnAttack, btnEnd;
    private Lutemon l1, l2;
    private Random random = new Random();
    private StringBuilder log = new StringBuilder();
    private boolean isL1Turn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        txtL1Name = findViewById(R.id.txtL1Name);
        txtL2Name = findViewById(R.id.txtL2Name);
        txtL1Stats = findViewById(R.id.txtL1Stats);
        txtL2Stats = findViewById(R.id.txtL2Stats);
        txtBattleLog = findViewById(R.id.txtBattleLog);
        imgL1 = findViewById(R.id.imgL1);
        imgL2 = findViewById(R.id.imgL2);
        imgSword = findViewById(R.id.imgSword);
        btnAttack = findViewById(R.id.btnNextAttack);
        btnEnd = findViewById(R.id.btnEndBattle);

        List<Lutemon> fighters = Storage.getInstance().getLutemonsByArea("battle");

        if (fighters.size() < 2) {
            txtBattleLog.setText("Not enough Lutemons for battle.");
            btnAttack.setEnabled(false);
            return;
        }

        showLutemonSelectionDialog(fighters);

        btnAttack.setOnClickListener(v -> doBattleTurn());

        btnEnd.setOnClickListener(v -> {
            Storage.getInstance().moveLutemon(l1.getId(), "battle", "home");
            Storage.getInstance().moveLutemon(l2.getId(), "battle", "home");
            l1.restoreHealth();
            l2.restoreHealth();
            Toast.makeText(this, "Battle ended", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void showLutemonSelectionDialog(List<Lutemon> battleLutemons) {
        String[] names = new String[battleLutemons.size()];
        boolean[] checked = new boolean[battleLutemons.size()];

        for (int i = 0; i < battleLutemons.size(); i++) {
            Lutemon l = battleLutemons.get(i);
            names[i] = l.getName() + " (" + l.getColor() + ")";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select 2 Lutemons for Battle");
        builder.setMultiChoiceItems(names, checked, (dialog, which, isChecked) -> {
            checked[which] = isChecked;
        });

        builder.setPositiveButton("Start", (dialog, which) -> {
            List<Lutemon> selected = new ArrayList<>();
            for (int i = 0; i < checked.length; i++) {
                if (checked[i]) {
                    selected.add(battleLutemons.get(i));
                }
            }

            if (selected.size() == 2) {
                l1 = selected.get(0);
                l2 = selected.get(1);
                updateUI();
            } else {
                Toast.makeText(this, "Please select exactly 2 Lutemons", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> finish());
        builder.show();
    }

    private void updateUI() {
        txtL1Name.setText(l1.getName());
        txtL2Name.setText(l2.getName());

        txtL1Stats.setText("HP: " + l1.getHealth() + "/" + l1.getMaxHealth() +
                "  ATK: " + l1.getAttack() + " DEF: " + l1.getDefense() + " EXP: " + l1.getExperience());

        txtL2Stats.setText("HP: " + l2.getHealth() + "/" + l2.getMaxHealth() +
                "  ATK: " + l2.getAttack() + " DEF: " + l2.getDefense() + " EXP: " + l2.getExperience());

        txtBattleLog.setText(log.toString());

        setLutemonImage(imgL1, l1.getColor());
        setLutemonImage(imgL2, l2.getColor());
    }

    private void doBattleTurn() {
        if (l1 == null || l2 == null || l1.isDead() || l2.isDead()) return;

        Lutemon attacker = isL1Turn ? l1 : l2;
        Lutemon defender = isL1Turn ? l2 : l1;

        playAttackAnimation(isL1Turn);
        attack(attacker, defender);

        if (defender.isDead()) {
            btnAttack.setEnabled(false);
            attacker.gainExperience();
            StatisticsManager.getInstance().incrementWin(attacker.getId());


            StatisticsManager.getInstance().recordBattleBetween(l1.getId(), l2.getId());


            log.append("\n").append(attacker.getName()).append(" wins and gains EXP!");
        }

        isL1Turn = !isL1Turn;
        updateUI();
    }

    private void attack(Lutemon attacker, Lutemon defender) {
        double baseAttack = attacker.attack() + Math.random() * 3;
        boolean isCrit = Math.random() < 0.05;
        int finalAttack = (int) (isCrit ? baseAttack * 3 : baseAttack);

        defender.defense(finalAttack);

        log.append(attacker.getColor()).append("(").append(attacker.getName()).append(")")
                .append(" attacks ")
                .append(defender.getColor()).append("(").append(defender.getName()).append(")");

        if (isCrit) {
            log.append(" ⚡Critical Hit!");
        }

        log.append(" [Damage: ").append(finalAttack).append("] \n")
                .append(defender.getName()).append(" HP: ").append(defender.getHealth())
                .append("\n");
    }

    private void setLutemonImage(ImageView imageView, String color) {
        switch (color.toLowerCase()) {
            case "white":
                imageView.setImageResource(R.drawable.white);
                break;
            case "green":
                imageView.setImageResource(R.drawable.green);
                break;
            case "pink":
                imageView.setImageResource(R.drawable.pink);
                break;
            case "orange":
                imageView.setImageResource(R.drawable.orange);
                break;
            case "black":
                imageView.setImageResource(R.drawable.black);
                break;
            default:
                imageView.setImageResource(R.drawable.ic_launcher_foreground);
        }
    }

    private void playAttackAnimation(boolean fromLeft) {
        imgSword.setVisibility(View.VISIBLE);
        float fromX = fromLeft ? -500f : 500f;
        float toX = 0f;
        imgSword.setRotation(fromLeft ? 0 : 180);
        TranslateAnimation animation = new TranslateAnimation(fromX, toX, 0, 0);
        animation.setDuration(500);
        animation.setFillAfter(false);
        imgSword.startAnimation(animation);
        animation.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
            @Override public void onAnimationStart(android.view.animation.Animation animation) {}
            @Override public void onAnimationRepeat(android.view.animation.Animation animation) {}
            @Override public void onAnimationEnd(android.view.animation.Animation animation) {
                imgSword.setVisibility(View.GONE);
            }
        });
    }
}






