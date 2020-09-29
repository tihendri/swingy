package com.tihendri.swingy.model.artifacts;

public class Weapon extends Artifact{

    public int attack = 80;

    public Weapon(String type) {
        super(type);
    }

    public int getAttack() {
        return this.attack;
    }
}
