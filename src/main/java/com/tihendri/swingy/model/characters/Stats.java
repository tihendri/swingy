package com.tihendri.swingy.model.characters;

public class Stats {

    public String type;
//    @PositiveOrZero(message = "attack must be positive or zero!")
    public int attack;
//    @PositiveOrZero(message = "defence must be positive or zero!")
    public int defence;
//    @PositiveOrZero(message = "HitPoints must be positive or zero!")
    public int hitPoints;
//    @Positive(message = "Level must be positive!")
    public int level;
//    @Positive(message = "Experience points must be positive!")
    public int xp;

    public Stats() {}

//    public Stats(Artifact artifact, int attack, int defence, int health, int level, int xp) {}

    public Stats(String type, int attack, int defence, int hitPoints, int level, int xp) {
        this.attack = attack;
        this.defence = defence;
        this.hitPoints = hitPoints;
        this.level = level;
        this.type = type;
        this.xp = xp;
    }

    public String getType() {
        return type;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefence() {
        return defence;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getLevel() {
        return level;
    }

    public int getXp() {
        return xp;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setXp(int xp) {
        this.xp += xp;
        if (this.xp < 1) {
            this.xp = xp;
        }
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints += hitPoints;
        if (this.hitPoints < 1) {
            this.hitPoints = 0;
        }
    }

    public void setDefence(int defence) {
        this.defence += defence;
        if (this.defence < 1) {
            this.defence = 0;
        }
    }

    public void setAttack(int attack) {
        this.attack += attack;
        if (this.attack < 1) {
            this.attack = 0;
        }
    }
}
