package com.tihendri.swingy.model.artifacts;

public class Armor extends Artifact {

    public int defence = 100;

    public Armor(String type) {
        super(type);
    }

    public int getDefence() {
        return this.defence;
    }
}
