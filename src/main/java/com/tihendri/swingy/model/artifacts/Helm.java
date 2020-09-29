package com.tihendri.swingy.model.artifacts;

public class Helm extends Artifact {

    public int hitPoints = 90;

    public Helm(String type) {
        super(type);
    }

    public int getHitPoints() {
        return this.hitPoints;
    }
}
