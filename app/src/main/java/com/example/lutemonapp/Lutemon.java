package com.example.lutemonapp;

public abstract class Lutemon {
    protected String name;
    protected String color;
    protected int attack;
    protected int defense;
    protected int experience;
    protected int health;
    protected int maxHealth;
    protected int speed;
    protected int id;

    private static int idCounter = 0;

    public Lutemon(String name, String color, int attack, int defense, int maxHealth, int speed) {
        this.name = name;
        this.color = color;
        this.attack = attack;
        this.defense = defense;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.experience = 0;
        this.speed = speed;
        this.id = idCounter++;
    }

    // 攻击力（包含经验加成）
    public int attack() {
        return attack + experience;
    }

    // 受到攻击，计算伤害
    public void defense(int attackValue) {
        int damage = attackValue - defense;
        if (damage > 0) {
            health -= damage;
        }
    }

    // 判断是否死亡
    public boolean isDead() {
        return health <= 0;
    }

    // 胜利后增加经验
    public void gainExperience() {
        experience++;
    }

    // 返回 Home 时恢复生命
    public void restoreHealth() {
        this.health = maxHealth;
    }

    // ------------------------------
    // 🔓 Getter 方法，供外部访问属性
    // ------------------------------
    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getExperience() {
        return experience;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getSpeed() {
        return speed;
    }

    public int getId() {
        return id;
    }
}

