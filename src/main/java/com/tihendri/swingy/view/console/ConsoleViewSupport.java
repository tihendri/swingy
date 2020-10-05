package com.tihendri.swingy.view.console;

import com.tihendri.swingy.model.characters.Character;

import java.util.Scanner;

public class ConsoleViewSupport {

    public static void directions() {
        System.out.println("\n" + "Select your way: ");
        System.out.println("1. North");
        System.out.println("2. South");
        System.out.println("3. East");
        System.out.println("4. West");
        System.out.println("5. Exit");
    }

    public static int createOrSelect() {
        System.out.println("\n" + "SWINGY" + "\n");
        System.out.println("1. Create player");
        System.out.println("2. Select player");
        int select = 0;
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            if (str.matches("1") || str.matches("2")) {
                select = Integer.parseInt(str);
                break;
            } else {
                System.out.println("Wrong input value!");
            }
        }
        return select;
    }

    public static boolean IsDigit(String str)
    {
        char[] charArray = str.toCharArray();
        for (char c : charArray) {
            if (!java.lang.Character.isDigit(c))
                return false;
        }
        return true;
    }

    public static String enterHeroName() {
        System.out.println("To continue please enter your character's name:");
        String characterName = null;
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            characterName = scanner.nextLine();
            characterName = characterName.trim();
            if (characterName.length() > 0) {
                String[] ch = characterName.split("\\s");
                characterName = String.join("_", ch);
                break;
            } else {
                System.out.println("Wrong input value!");
            }
        }
        return characterName;
    }

    public static int choosePlayerType() {
        System.out.println("Select your player type:");
        System.out.println("1. Viking");
        System.out.println("2. Crusader");
        System.out.println("3. Druid");
        int select = 0;
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            if (str.equals("1") || str.equals("2") || str.equals("3")) {
                select = Integer.parseInt(str);
                break;
            } else {
                System.out.println("Wrong input value!");
            }
        }
        return select;
    }

    public static void startOrQuit() {
        System.out.println("What would you do next?");
        System.out.println("1. Start game");
        System.out.println("2. Exit game");
    }

    public static int printStatistics(String hero, Character player, int type) {
        System.out.println("Welcome to SWINGY");
        System.out.println("This is your character: ");
        if (type == 1) {
            System.out.println("Viking " + hero);
            printThings(player);
        } else if (type == 2) {
            System.out.println("Crusader " + hero);
            printThings(player);
        } else if (type == 3) {
            System.out.println("Druid " + hero);
            printThings(player);
        }
        int opt = 0;
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            if (str.equals("1") || str.equals("2")) {
                opt = Integer.parseInt(str);
                break;
            } else {
                System.out.println("Wrong input value!");
            }
        }
        return opt;
    }

    private static void printThings(Character player) {
        System.out.println("Level: " + player.getStats().getLevel());
        System.out.println("Attack: " + player.getStats().getAttack());
        System.out.println("Defence: " + player.getStats().getDefence());
        System.out.println("Experience: " + player.getStats().getXp());
        System.out.println("HP: " + player.getStats().getHitPoints() + "\n");
        startOrQuit();
    }

    public static int printLevelUp() {
        System.out.println("You've leveled up!");
        System.out.println("1. Continue playing");
        System.out.println("2. Exit game");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            if (str.matches("\\s*[1-2]\\s*")) {
                int opt = Integer.parseInt(str);
                if (opt == 1) {
                    return 1;
                } else if (opt == 2) {
                    System.out.println("goodbye");
                    System.exit(0);
                }
            } else {
                System.out.println((char)27 + "[031mYour choice is out of bounds, try again." + (char)27 + "[0m");
            }
        }
        return 0;
    }
}
