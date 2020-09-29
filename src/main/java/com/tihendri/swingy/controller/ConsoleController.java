package com.tihendri.swingy.controller;

import com.tihendri.swingy.Main;
import com.tihendri.swingy.model.characters.Character;
import com.tihendri.swingy.model.characters.Monster;
import com.tihendri.swingy.view.console.ConsoleViewSupport;

import java.util.Random;
import java.util.Scanner;

public class ConsoleController {

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
                        System.exit(0);
                    default:
                        System.out.println("Wrong input value!");
                }
            } else {
                System.out.println("Wrong input value!");
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
        if (character.getStats().getHitPoints() > 20) {
            while (character.getStats().getHitPoints() > 0 && monster.getHitPoints() > 0) {
                if (!Main.guiOrConsole) {
                    System.out.println("Enemy HP: " + monster.getHitPoints() + "\n" +
                            "Player HP: " + character.getStats().getHitPoints());
                }
                if (battle == 0) {
                    damage = random.nextInt(30) + 1;
                    if (monster.getHitPoints() > 0) {
                        character.getStats().setHitPoints(-damage);
                        Reader.updatePlayersList(character);
                        if (!Main.guiOrConsole) {
                            System.out.println("Due to monster attack you lost " + damage + " HP");
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
                            System.out.println("Due to your attack the monster lost " + damage + " HP");
                        }
                        if (monster.getHitPoints() <= 0) {
                            victory = 1;
                            break;
                        }
                        battle = 0;
                    }
                }
            }
        } else {
            if (!Main.guiOrConsole) {
                System.out.println("You do not have enough HP to fight (" + character.getStats().getHitPoints() + ")");
            }
        }
        return victory;
    }
}
