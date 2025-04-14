package com.example.lutemonapp;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

public class BattleActivity extends AppCompatActivity {

    private TextView txtL1Name, txtL2Name, txtL1Stats, txtL2Stats, txtBattleLog;
    private ImageView imgL1, imgL2;
    private Button btnAttack, btnEnd;
    private Lutemon l1, l2;
    private Random random = new Random();
    private StringBuilder log = new StringBuilder();

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
        btnAttack = findViewById(R.id.btnNextAttack);
        btnEnd = findViewById(R.id.btnEndBattle);

        List<Lutemon> fighters = Storage.getInstance().getLutemonsByArea("battle");

        if (fighters.size() < 2) {
            txtBattleLog.setText("Not enough Lutemons for battle.");
            btnAttack.setEnabled(false);
            return;
        }

        Collections.shuffle(fighters);
        l1 = fighters.get(0);
        l2 = fighters.get(1);

        // ✅ 记录参战次数
        StatisticsManager.getInstance().incrementBattle(l1.getId());
        StatisticsManager.getInstance().incrementBattle(l2.getId());

        updateUI();

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

    private void updateUI() {
        txtL1Name.setText(l1.getName());
        txtL2Name.setText(l2.getName());

        txtL1Stats.setText("HP: " + l1.getHealth() + "/" + l1.getMaxHealth() +
                "  ATK: " + l1.getAttack() + " DEF: " + l1.getDefense() + " EXP: " + l1.getExperience());

        txtL2Stats.setText("HP: " + l2.getHealth() + "/" + l2.getMaxHealth() +
                "  ATK: " + l2.getAttack() + " DEF: " + l2.getDefense() + " EXP: " + l2.getExperience());

        txtBattleLog.setText(log.toString());

        // ✅ 设置头像
        setLutemonImage(imgL1, l1.getColor());
        setLutemonImage(imgL2, l2.getColor());
    }

    private void doBattleTurn() {
        Lutemon first = (l1.getSpeed() >= l2.getSpeed()) ? l1 : l2;
        Lutemon second = (first == l1) ? l2 : l1;

        attack(first, second);
        if (!second.isDead()) {
            attack(second, first);
        }

        if (l1.isDead() || l2.isDead()) {
            btnAttack.setEnabled(false);
            Lutemon winner = l1.isDead() ? l2 : l1;
            winner.gainExperience();

            // ✅ 记录胜利次数
            StatisticsManager.getInstance().incrementWin(winner.getId());

            log.append("\n").append(winner.getName()).append(" wins and gains EXP!");
        }

        updateUI();
    }

    private void attack(Lutemon attacker, Lutemon defender) {
        double baseAttack = attacker.attack() + Math.random() * 3;
        boolean isCrit = Math.random() < 0.05;  // ✅ 5% 暴击概率
        int finalAttack = (int) (isCrit ? baseAttack * 3 : baseAttack);

        defender.defense(finalAttack);

        log.append(attacker.getColor()).append("(").append(attacker.getName()).append(")")
                .append(" attacks ")
                .append(defender.getColor()).append("(").append(defender.getName()).append(")");

        if (isCrit) {
            log.append(" ⚡Critical Hit!");
        }

        log.append(" [Damage: ").append(finalAttack).append("]")
                .append("\n")
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
}



