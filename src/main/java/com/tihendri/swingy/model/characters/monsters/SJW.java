package com.tihendri.swingy.model.characters.monsters;

import com.tihendri.swingy.model.artifacts.Artifact;
import com.tihendri.swingy.model.characters.Monster;

public class SJW extends Monster {
    public SJW(Artifact artifact, int attack, int defence, int health, int level, int xp) {
        super(artifact, attack, defence, health, level, xp);
        int idType = 3;
        super.setIdType(idType);
        String name = "SJW";
        super.setMonsterName(name);
    }
}
