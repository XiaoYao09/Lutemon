package com.example.lutemonapp;

import java.util.HashMap;

public class StatisticsManager {

    private static StatisticsManager instance;

    private int totalCreated = 0;
    private HashMap<Integer, Integer> battleCounts = new HashMap<>();
    private HashMap<Integer, Integer> winCounts = new HashMap<>();
    private HashMap<Integer, Integer> trainingCounts = new HashMap<>();

    private StatisticsManager() {}

    public static StatisticsManager getInstance() {
        if (instance == null) {
            instance = new StatisticsManager();
        }
        return instance;
    }

    // 创建数统计
    public void incrementCreated() {
        totalCreated++;
    }

    public int getTotalCreated() {
        return totalCreated;
    }

    // 战斗次数统计
    public void incrementBattle(int id) {
        battleCounts.put(id, battleCounts.getOrDefault(id, 0) + 1);
    }

    public int getBattles(int id) {
        return battleCounts.getOrDefault(id, 0);
    }

    public int getTotalBattles() {
        int sum = 0;
        for (int count : battleCounts.values()) {
            sum += count;
        }
        return sum;
    }

    // 胜利次数统计
    public void incrementWin(int id) {
        winCounts.put(id, winCounts.getOrDefault(id, 0) + 1);
    }

    public int getWins(int id) {
        return winCounts.getOrDefault(id, 0);
    }

    // 训练次数统计
    public void incrementTrain(int id) {
        trainingCounts.put(id, trainingCounts.getOrDefault(id, 0) + 1);
    }

    public int getTraining(int id) {
        return trainingCounts.getOrDefault(id, 0);
    }
    public void incrementTraining(int id) {
        if (!trainingCounts.containsKey(id)) {
            trainingCounts.put(id, 1);
        } else {
            trainingCounts.put(id, trainingCounts.get(id) + 1);
        }
    }


    public int getTotalTraining() {
        int sum = 0;
        for (int count : trainingCounts.values()) {
            sum += count;
        }
        return sum;
    }
}

