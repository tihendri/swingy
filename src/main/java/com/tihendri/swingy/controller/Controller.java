package com.tihendri.swingy.controller;

import com.tihendri.swingy.Main;
import com.tihendri.swingy.model.artifacts.Armor;
import com.tihendri.swingy.model.artifacts.Helm;
import com.tihendri.swingy.model.artifacts.Weapon;
import com.tihendri.swingy.model.characters.Character;
import com.tihendri.swingy.model.characters.Monster;
import com.tihendri.swingy.view.console.ConsoleViewSupport;

import javax.swing.*;
import java.util.Random;
import java.util.Scanner;

public class Controller {

    public static void start(Character character) {
        ControllerMap controllerMap = new ControllerMap(character);
        controllerMap.mapOutput();
        ConsoleViewSupport.directions();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.matches("1") || line.matches("2") || line.matches("3") || line.matches("4") || line.matches("5")) {
                int selectDirection = Integer.parseInt(line);
                switch (selectDirection) {
                    case 1:
                        controllerMap.updatePosition(0, -1);
                        ConsoleViewSupport.directions();
                        break;
                    case 2:
                        controllerMap.updatePosition(0, 1);
                        ConsoleViewSupport.directions();
                        break;
                    case 3:
                        controllerMap.updatePosition(1, 0);
                        ConsoleViewSupport.directions();
                        break;
                    case 4:
                        controllerMap.updatePosition(-1, 0);
                        ConsoleViewSupport.directions();
                        break;
                    case 5:
                        WriteToFile.close();
                        System.exit(0);
                    default:
                        System.out.println((char)27 + "[031mChoose an option between 1 and 5" + (char)27 + "[0m");
                }
            } else {
                System.out.println((char)27 + "[031mChoose an option between 1 and 5" + (char)27 + "[0m");
            }
        }
    }

    public static boolean chance() {
        Random random = new Random();
        int ch = random.nextInt(2) + 1;
        return ch == 1;
    }

    public static int battle(Monster monster, Character character) {
        Random random = new Random();
        int turn = 1;
        int victory = 0;
        if (character.getStats().getHitPoints() > monster.getHitPoints()) {
            turn = 0;
        }
        victory = getVictoryGuiConsole(monster, character, random, turn, victory);
        return victory;
    }

    private static int getVictoryGuiConsole(Monster monster, Character character, Random random, int turn, int victory) {
        int damage;
        while (character.getStats().getHitPoints() > 0 && monster.getHitPoints() > 0) {
            if (!Main.guiOrConsole) {
                System.out.println((char)27 + "[031mMonster HP: " + monster.getHitPoints() + (char)27 + "[0m");
                System.out.println((char)27 + "[032mYour HP: " + character.getStats().getHitPoints() + (char)27 + "[0m\n");
            } else {
                JOptionPane.showMessageDialog(null, "Monster HP: " + monster.getHitPoints() + "\n" +
                        "Your HP: " + character.getStats().getHitPoints());
            }
            if (turn == 0) {
                damage = random.nextInt(70) - ((character.getStats().getDefence())/5);
                if (monster.getHitPoints() > 0 && damage > 0) {
                    character.getStats().setHitPoints(-damage);
                    WriteToFile.removeLine(character);
                    WriteToFile.writeCharactersStatsChange(character);
                    if (!Main.guiOrConsole) {
                        System.out.println((char)27 + "[031mDue to monster attack you lost " + damage + " HP" + (char)27 + "[0m");
                    } else {
                        JOptionPane.showMessageDialog(null, "Due to monster attack you lost " + damage + " HP");
                    }
                    if (character.getStats().getHitPoints() <= 0) {
                        victory = 2;
                        break;
                    }
                } else {
                    if (!Main.guiOrConsole) {
                        System.out.println((char)27 + "[032mYour defence held up well. You thwarted the " + monster.getMonsterName() + "'s attack to " + damage + " damage." + (char)27 + "[0m");
                    } else {
                        JOptionPane.showMessageDialog(null, "Your defence held up well. You thwarted the " + monster.getMonsterName() + "'s attack to " + damage + " damage.");
                    }
                }
                turn = 1;
            } else {
                damage = random.nextInt(30) + ((character.getStats().getAttack())/6);
                if (character.getStats().getHitPoints() > 0) {
                    monster.setHitPoints(-damage);
                    if (!Main.guiOrConsole) {
                        System.out.println((char)27 + "[032mDue to your attack the monster lost " + damage + " HP" + (char)27 + "[0m\n");
                    } else {
                        JOptionPane.showMessageDialog(null, "Due to your attack the monster lost " + damage + " HP");
                    }
                    if (monster.getHitPoints() <= 0) {
                        victory = 1;
                        if (!Main.guiOrConsole) {
                            System.out.println((char) 27 + "[032mYou won that fight! Thanks to your Taekwondo!" + (char) 27 + "[0m\n");
                        } else {
                            JOptionPane.showMessageDialog(null, "You won that fight! Thanks to your Taekwondo!");
                        }
                        break;
                    }
                    if (character.getStats().getHitPoints() <= 0) {
                        victory = 2;
                        break;
                    }
                }
                turn = 0;
            }
        }
    return victory;
    }

    static void PickUpLoot(Monster encountered, Character character) {
        String type = encountered.getArtifact().getType();
        switch (type) {
            case "Weapon":
                Weapon weapon = new Weapon("WEAPON");
                character.setArtifact(weapon);
                character.getStats().setAttack(70);
                WriteToFile.removeLine(character);
                WriteToFile.writeCharactersStatsChange(character);
                break;
            case "Armor":
                Armor armor = new Armor("ARMOR");
                character.setArtifact(armor);
                character.getStats().setDefence(60);
                WriteToFile.removeLine(character);
                WriteToFile.writeCharactersStatsChange(character);
                break;
            case "Helm":
                Helm helm = new Helm("HELM");
                character.setArtifact(helm);
                character.getStats().setHitPoints(80);
                WriteToFile.removeLine(character);
                WriteToFile.writeCharactersStatsChange(character);
                break;
        }
    }
}
