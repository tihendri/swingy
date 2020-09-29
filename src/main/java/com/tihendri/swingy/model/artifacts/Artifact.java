package com.tihendri.swingy.model.artifacts;

import java.util.Random;

public abstract class Artifact {
    public String type;
    public static final String[] ARTIFACTS = {"Armor", "Helm", "Weapon"};

    public static String randomArtifact() {
        Random random = new Random();
        return(ARTIFACTS[random.nextInt(3)]);
    }

    public Artifact(String type) {
        this.type = type;
    }

    public static  String[] getArtifacts() {
        return ARTIFACTS;
    }

    public String getType() {
        return this.type;
    }
}
