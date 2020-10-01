package com.tihendri.swingy.controller;

import com.tihendri.swingy.model.artifacts.Armor;
import com.tihendri.swingy.model.artifacts.Helm;
import com.tihendri.swingy.model.artifacts.Weapon;
import com.tihendri.swingy.model.characters.Character;
import com.tihendri.swingy.model.characters.NewCharacter;
import com.tihendri.swingy.model.characters.Stats;

import java.util.Random;

public class DatabaseOps {

    public static String artifact;
    public static int attack;
    public static int defence;
    public static int hitPoints;
    public static int level;
    public static String printStats;
    public static int xp;
    public static Character characterSet = new Character();
    public static Character newCharacter = new Character();

    public static Character newCharacterDB(int select, String characterName) {
        if (select == 1) {
            return addPlayer("Viking", characterName);
        } else if (select == 2) {
            return addPlayer("Crusader", characterName);
        } else if (select == 3) {
            return addPlayer("Druid", characterName);
        }
        return null;
    }

    public static void setPrintStats(String type, String character, String artifact) {
        printStats = String.format("%s %s %d %d %d %d %d %s", type, character, level, attack, defence, hitPoints, xp, artifact);
    }

    private static String randomArtifact(String[] artifacts) {
        Random random = new Random();
        return(artifacts[random.nextInt(3)]);
    }

    private static Character addPlayer(String select, String characterName){
        String[] artifactsArray = {"WEAPON", "HELM", "ARMOR"};
        artifact = randomArtifact(artifactsArray);
        switch (artifact) {
            case "WEAPON":
                Weapon weapon = new Weapon("WEAPON");
                level = 1;
                attack = 100 + weapon.getAttack();
                defence = 100;
                hitPoints = 100;
                xp = 1000;
                Stats statistic = new Stats(select, attack, defence, hitPoints, level, xp);
                newCharacter = NewCharacter.newCharacter(characterName, select, weapon, statistic);
                setPrintStats(select, characterName, artifact);
                break;
            case "ARMOR": {
                Armor armor = new Armor("ARMOR");
                level = 1;
                attack = 100;
                defence = 100 + armor.getDefence();
                hitPoints = 100;
                xp = 1000;
                statistic = new Stats(select, attack, defence, hitPoints, level, xp);
                newCharacter = NewCharacter.newCharacter(characterName, select, armor, statistic);
                setPrintStats(select, characterName, artifact);
                break;
            }
            case "HELM": {
                Helm helm = new Helm("HELM");
                level = 1;
                attack = 100 + helm.getHitPoints();
                defence = 100;
                hitPoints = 100;
                xp = 1000;
                statistic = new Stats(select, attack, defence, hitPoints, level, xp);
                newCharacter = NewCharacter.newCharacter(characterName, select, helm, statistic);
                setPrintStats(select, characterName, artifact);
                break;
            }
        }
        WriteToFile.writeCharacters(printStats);
        return newCharacter;
    }

    public static Character setCharacter(String character) {
        String[] array = character.split(" ");
        int attack = Integer.parseInt(array[3]);
        int defence = Integer.parseInt(array[4]);
        int level = Integer.parseInt(array[2]);
        int hitPoints = Integer.parseInt(array[5]);
        int xp = Integer.parseInt(array[6]);
        String name = array[1];
        String artifact = array[7];
        String type = array[0];
        Stats stats = new Stats(type, attack, defence, hitPoints, level, xp);
        if (artifact.equalsIgnoreCase("Armor")) {
            Armor armor = new Armor("Armor");
            characterSet = NewCharacter.newCharacter(name, type, armor, stats);
        } else if (artifact.equalsIgnoreCase("Helm")) {
            Helm helm = new Helm("Helm");
            characterSet = NewCharacter.newCharacter(name, type, helm, stats);
        } else if (artifact.equalsIgnoreCase("Weapon")) {
            Weapon weapon = new Weapon("Weapon");
            characterSet = NewCharacter.newCharacter(name, type, weapon, stats);
        }
        System.out.println(characterSet);
        return characterSet;
    }
}
