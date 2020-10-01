package com.tihendri.swingy.controller;

import com.tihendri.swingy.Main;
import com.tihendri.swingy.model.characters.Character;
import com.tihendri.swingy.model.characters.Monster;
import com.tihendri.swingy.view.console.ConsoleViewSupport;

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
//                        controllerMap.mapOutput();
                        ConsoleViewSupport.directions();
                        break;
                    case 2:
                        controllerMap.updatePosition(0, 1);
//                        controllerMap.mapOutput();
                        ConsoleViewSupport.directions();
                        break;
                    case 3:
                        controllerMap.updatePosition(1, 0);
//                        controllerMap.mapOutput();
                        ConsoleViewSupport.directions();
                        break;
                    case 4:
                        controllerMap.updatePosition(-1, 0);
//                        controllerMap.mapOutput();
                        ConsoleViewSupport.directions();
                        break;
                    case 5:
                        WriteToFile.close();
                        System.exit(0);
                    default:
                        System.out.println((char)27 + "[031mWrong input value!" + (char)27 + "[0m");
                }
            } else {
                System.out.println((char)27 + "[031mWrong input value!" + (char)27 + "[0m");
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
        int battle = 0;
        int victory = 0;
        if (chance() || character.getStats().getAttack() > monster.getHitPoints()) {
            battle = 1;
        }
        victory = getVictoryGuiConsole(monster, character, random, battle, victory);
        return victory;
    }

    private static int getVictoryGuiConsole(Monster monster, Character character, Random random, int battle, int victory) {
        int damage;
        if (character.getStats().getHitPoints() > 30) {
            while (character.getStats().getHitPoints() > 0 && monster.getHitPoints() > 0) {
                if (!Main.guiOrConsole) {
                    System.out.println((char)27 + "[031mEnemy HP: " + monster.getHitPoints() + (char)27 + "[0m");
                    System.out.println((char)27 + "[032mPlayer HP: " + character.getStats().getHitPoints() + (char)27 + "[0m\n");
                }
                if (battle == 0) {
                    damage = random.nextInt(30) + 1;
                    if (monster.getHitPoints() > 0) {
                        character.getStats().setHitPoints(-damage);
//                        Reader.updatePlayersList(character);
                        WriteToFile.removeLine(character);
                        WriteToFile.writeCharactersStatsChange(character);
                        if (!Main.guiOrConsole) {
                            System.out.println((char)27 + "[031mDue to monster attack you lost " + damage + " HP" + (char)27 + "[0m");
                        }
                        if (character.getStats().getHitPoints() <= 0) {
                            break;
                        }
                        battle = 1;
                    }
                } else {
                    damage = random.nextInt(50) + 1;
                    if (character.getStats().getHitPoints() > 0) {
                        monster.setHitPoints(-damage);
                        if (!Main.guiOrConsole) {
                            System.out.println((char)27 + "[032mDue to your attack the monster lost " + damage + " HP" + (char)27 + "[0m\n");
                        }
                        if (monster.getHitPoints() <= 0) {
                            victory = 1;
                            if (!Main.guiOrConsole) {
                                System.out.println((char) 27 + "[032mYou won that fight! Thanks to your Taekwondo!" + (char) 27 + "[0m\n");
                            }
                            break;
                        }
                        if (character.getStats().getHitPoints() <= 0) {
                            victory = 2;
                            break;
                        }
                        battle = 0;
                    }
                }
            }
        } else {
            if (!Main.guiOrConsole) {
                System.out.println((char)27 + "[031mYou do not have enough HP to fight (" + character.getStats().getHitPoints() + ")" + (char)27 + "[0m");
                victory = 3;
            }
        }
        return victory;
    }
}
