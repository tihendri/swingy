package com.tihendri.swingy.model.characters.monsters;

import com.tihendri.swingy.model.artifacts.Artifact;
import com.tihendri.swingy.model.characters.Monster;

public class Basilisk extends Monster {
    public Basilisk(Artifact artifact, int attack, int defence, int hitPoints, int level, int xp) {
        super(artifact, attack, defence, hitPoints, level, xp);
        int idType = 1;
        super.setIdType(idType);
        String name = "Basilisk";
        super.setMonsterName(name);
    }
}
