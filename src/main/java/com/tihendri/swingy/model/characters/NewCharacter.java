package com.tihendri.swingy.model.characters;

import com.tihendri.swingy.model.artifacts.Armor;
import com.tihendri.swingy.model.artifacts.Artifact;
import com.tihendri.swingy.model.artifacts.Helm;
import com.tihendri.swingy.model.artifacts.Weapon;
import com.tihendri.swingy.model.characters.heroes.Crusader;
import com.tihendri.swingy.model.characters.heroes.Druid;
import com.tihendri.swingy.model.characters.heroes.Viking;
import com.tihendri.swingy.model.characters.monsters.Basilisk;
import com.tihendri.swingy.model.characters.monsters.Forktail;
import com.tihendri.swingy.model.characters.monsters.SJW;

import java.util.Random;

public class NewCharacter {

    public static Character newCharacter(String characterName, String type, Artifact artifact, Stats stats) {
        if (type.equals("Viking")) {
            return new Viking(characterName, artifact, stats);
        }
        else if (type.equalsIgnoreCase("Crusader")) {
            return new Crusader(characterName, artifact, stats);
        }
        else if (type.equalsIgnoreCase("Druid")) {
            return new Druid(characterName, artifact, stats);
        }
        else {
            return null;
        }
    }

    public static Monster spawnMonsters(Character character) {
        int level;
        int attack;
        int defence;
        int xp;
        int hitPoints;

        Random random = new Random();
        int monster = random.nextInt(2) + 1;
        String artifact = Artifact.randomArtifact();

        switch (monster) {
            case 1:
                switch (artifact) {
                    case "Armor":
                        Armor armor = new Armor("Armor");
                        level = character.getStats().getLevel();
                        attack = 70;
                        defence = 30 + armor.getDefence();
                        hitPoints = 50;
                        xp = 0;
                        return (new Basilisk(armor, attack, defence, hitPoints, level, xp));
                    case "Weapon":
                        Weapon weapon = new Weapon("Weapon");
                        level = character.getStats().getLevel();
                        attack = 70 + weapon.getAttack();
                        defence = 30;
                        hitPoints = 50;
                        xp = 0;
                        return (new Basilisk(weapon, attack, defence, hitPoints, level, xp));
                    case "Helm":
                        Helm helm = new Helm("Helm");
                        level = character.getStats().getLevel();
                        attack = 70;
                        defence = 30;
                        hitPoints = 50 + helm.getHitPoints();
                        xp = 0;
                        return (new Basilisk(helm, attack, defence, hitPoints, level, xp));
                }
                break;
            case 2:
                switch (artifact) {
                    case "Armor":
                        Armor armor = new Armor("Armor");
                        level = character.getStats().getLevel();
                        attack = 50;
                        defence = 50 + armor.getDefence();
                        hitPoints = 50;
                        xp = 0;
                        return (new Forktail(armor, attack, defence, hitPoints, level, xp));
                    case "Weapon":
                        Weapon weapon = new Weapon("Weapon");
                        level = character.getStats().getLevel();
                        attack = 50 + weapon.getAttack();
                        defence = 50;
                        hitPoints = 50;
                        xp = 0;
                        return (new Forktail(weapon, attack, defence, hitPoints, level, xp));
                    case "Helm":
                        Helm helm = new Helm("Helm");
                        level = character.getStats().getLevel();
                        attack = 50;
                        defence = 50;
                        hitPoints = 50 + helm.getHitPoints();
                        xp = 0;
                        return (new Forktail(helm, attack, defence, hitPoints, level, xp));
                }
                break;
            case 3:
                switch (artifact) {
                    case "Armor":
                        Armor armor = new Armor("Armor");
                        level = character.getStats().getLevel();
                        attack = 10;
                        defence = 5 + armor.getDefence();
                        hitPoints = 50;
                        xp = 0;
                        return (new SJW(armor, attack, defence, hitPoints, level, xp));
                    case "Weapon":
                        Weapon weapon = new Weapon("Weapon");
                        level = character.getStats().getLevel();
                        attack = 10 + weapon.getAttack();
                        defence = 5;
                        hitPoints = 50;
                        xp = 0;
                        return (new SJW(weapon, attack, defence, hitPoints, level, xp));
                    case "Helm":
                        Helm helm = new Helm("Helm");
                        level = character.getStats().getLevel();
                        attack = 10;
                        defence = 5;
                        hitPoints = 50 + helm.getHitPoints();
                        xp = 0;
                        return (new SJW(helm, attack, defence, hitPoints, level, xp));
                }
                break;
        }
        return null;
    }
}
