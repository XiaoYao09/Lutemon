package com.example.lutemonapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Storage {
    private static Storage instance = null;

    // 区域：home、training、battle
    private HashMap<Integer, Lutemon> home = new HashMap<>();
    private HashMap<Integer, Lutemon> training = new HashMap<>();
    private HashMap<Integer, Lutemon> battle = new HashMap<>();

    private Storage() {}

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    // 添加 Lutemon 到 home 区域
    public void addLutemonToHome(Lutemon lutemon) {
        home.put(lutemon.getId(), lutemon);
    }

    // 移动 Lutemon 到其他区域
    public void moveLutemon(int id, String from, String to) {
        Lutemon l = null;
        if (from.equals("home")) {
            l = home.remove(id);
        } else if (from.equals("training")) {
            l = training.remove(id);
        } else if (from.equals("battle")) {
            l = battle.remove(id);
        }

        if (l == null) return;

        if (to.equals("home")) {
            home.put(id, l);
        } else if (to.equals("training")) {
            training.put(id, l);
        } else if (to.equals("battle")) {
            battle.put(id, l);
        }
    }

    // 根据区域获取 Lutemon 列表
    public List<Lutemon> getLutemonsByArea(String area) {
        switch (area) {
            case "home":
                return new ArrayList<>(home.values());
            case "training":
                return new ArrayList<>(training.values());
            case "battle":
                return new ArrayList<>(battle.values());
            default:
                return new ArrayList<>();
        }
    }

    // 根据 ID 获取 Lutemon（任意区域）
    public Lutemon getLutemon(int id) {
        if (home.containsKey(id)) return home.get(id);
        if (training.containsKey(id)) return training.get(id);
        if (battle.containsKey(id)) return battle.get(id);
        return null;
    }

    // 删除 Lutemon（任意区域）
    public void removeLutemon(int id) {
        home.remove(id);
        training.remove(id);
        battle.remove(id);
    }

    // ✅ 返回所有 Lutemon（用于统计页面）
    public List<Lutemon> getAllLutemons() {
        List<Lutemon> all = new ArrayList<>();
        all.addAll(home.values());
        all.addAll(training.values());
        all.addAll(battle.values());
        return all;
    }
}


