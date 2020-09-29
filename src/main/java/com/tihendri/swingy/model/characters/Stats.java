package com.tihendri.swingy.model.characters;

public class Stats {

    public String type;
    public int attack;
    public int defence;
    public int hitPoints;
    public int level;
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
//        if (this.xp < 1) {
//            this.xp = xp; // might not be necessary
//        }
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints += hitPoints;
        if (this.hitPoints < 1) {
            this.hitPoints = 0; // Come back to put exception for death
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
