package com.example.lutemonapp;

import java.util.HashMap;

public class StatisticsManager {

    private static StatisticsManager instance;

    private int totalCreated = 0;
    private int totalBattles = 0;

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

    // ✅ 创建数统计
    public void incrementCreated() {
        totalCreated++;
    }

    public int getTotalCreated() {
        return totalCreated;
    }

    // ✅ 战斗统计：每场记录双方参与 + 总场数 +1
    public void recordBattleBetween(int id1, int id2) {
        battleCounts.put(id1, battleCounts.getOrDefault(id1, 0) + 1);
        battleCounts.put(id2, battleCounts.getOrDefault(id2, 0) + 1);
        totalBattles++;
    }

    public int getBattles(int id) {
        return battleCounts.getOrDefault(id, 0);
    }

    public int getTotalBattles() {
        return totalBattles;
    }

    public HashMap<Integer, Integer> getBattleCounts() {
        return battleCounts;
    }

    // ✅ 胜利次数统计
    public void incrementWin(int id) {
        winCounts.put(id, winCounts.getOrDefault(id, 0) + 1);
    }

    public int getWins(int id) {
        return winCounts.getOrDefault(id, 0);
    }

    public HashMap<Integer, Integer> getWinCounts() {
        return winCounts;
    }

    // ✅ 训练次数统计
    public void incrementTraining(int id) {
        trainingCounts.put(id, trainingCounts.getOrDefault(id, 0) + 1);
    }

    public int getTraining(int id) {
        return trainingCounts.getOrDefault(id, 0);
    }

    public int getTotalTraining() {
        int sum = 0;
        for (int count : trainingCounts.values()) {
            sum += count;
        }
        return sum;
    }
}

