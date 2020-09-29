package com.tihendri.swingy.model.characters.monsters;

import com.tihendri.swingy.model.artifacts.Artifact;
import com.tihendri.swingy.model.characters.Monster;

public class Forktail extends Monster {
    public Forktail(Artifact artifact, int attack, int defence, int health, int level, int xp) {
        super(artifact, attack, defence, health, level, xp);
        int idType = 2;
        super.setIdType(idType);
        String name = "Forktail";
        super.setMonsterName(name);
    }
}
