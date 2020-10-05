package com.tihendri.swingy.controller;

import com.tihendri.swingy.model.characters.Character;
import com.tihendri.swingy.model.characters.Monster;
import com.tihendri.swingy.model.characters.NewCharacter;
import com.tihendri.swingy.view.console.ConsoleViewSupport;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ControllerMap {

    public static Character character;
    private static int[][] map;
    private int monsters;
    private static int size;
    private int yCoordinates;
    private int xCoordinates;
    private static final ArrayList<Monster> monsterArrayList = new ArrayList<>();
    private static final ArrayList<Monster> tempMonsterArray = new ArrayList<>();
    private boolean set;
    private int level;
    private static int mapYCoordinate;
    private static int mapXCoordinate;

    public ControllerMap(Character character) {
        ControllerMap.character = character;
    }

    private static void registerMonster(Monster monster) {
        if (monsterArrayList.contains(monster)) {
            return;
        }
        monsterArrayList.add(monster);
    }

    private static int GetMapSize(int level){
        return (level - 1) * 5 + 10 - (level % 2);
    }

    private static void setMapSize() {
        size = GetMapSize(character.getStats().getLevel());
        mapXCoordinate = size;
        mapYCoordinate = size;
        map = new int[size][size];
    }

    public void mapOutput() {
        if (!set) {
            setMapSize();
            setPosition();
            setMonsters();
            if (tempMonsterArray.isEmpty()) {
                spawnMonstersOnMap();
            } else {
                monsterArrayList.addAll(tempMonsterArray);
            }
            set = true;
        }
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                map[y][x] = 0;
            }
        }
        for (Monster monster : monsterArrayList) {
            map[monster.getMonsterCoordinatesY()][monster.getMonsterCoordinatesX()] = monster.getMonsterIdType();
        }
        map[this.yCoordinates][this.xCoordinates] = 4;
        for (Monster monster : monsterArrayList) {
            boolean meetMonster = encounteredMonsterConsole(this.yCoordinates, this.xCoordinates, monster.getMonsterCoordinatesY(), monster.getMonsterCoordinatesX());
            if (meetMonster) {
                break;
            }
        }
        System.out.println(character.getStats().getType() + " " + character.getCharacterName() + " | " +
                "Level: " + character.getStats().getLevel() + " | " +
                "Attack: " + character.getStats().getAttack()+ " | " +
                "Defence: " + character.getStats().getDefence() + " | " +
                "HP: " + character.getStats().getHitPoints() + " | " +
                "Experience: " + character.getStats().getXp() + "\n");

        for (int y = 0; y < mapYCoordinate; y++) {
            for (int x = 0; x < mapXCoordinate; x++) {
                switch (map[y][x]) {
                    case 0:
                        System.out.print("w   w");
                        break;
                    case 1:
                    case 2:
                    case 3:
                        System.out.print("w " + "m" + " w");
                        break;
                    default:
                        System.out.print("| " + "U" + " |");
                        break;
                }
            }
            System.out.println();
        }
    }

    private boolean encounteredMonsterConsole(int yp, int xp, int yv, int xv) {
        if ((xp == xv) && (yp == yv)) {
            Monster encountered = getBattle();
            assert encountered != null;
            System.out.println("You've encountered a " + encountered.getMonsterName());
            System.out.println("1. Flee");
            System.out.println("2. Fight");

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String str = scanner.nextLine();
                if (str.matches("\\s*[1-2]\\s*")) {
                    int choice = Integer.parseInt(str);
                    if (choice == 1) {
                        System.out.println("You are a coward, but okay...");
                        System.out.println("You have lost 500 experience points because you ran away.");
                        character.getStats().setXp(-500);
                        System.out.println();
                        System.out.println("Your current XP: " + (character.getStats().getXp()));
                        if (character.getStats().getXp() <= 0) {
                            WriteToFile.removeLine(character);
                            System.out.println("You ran out of experience points, therefore it's GAME OVER!");
                            System.exit(0);
                        }
                        return true;
                    } else if (choice == 2) {
                        if (character.getStats().getHitPoints() < 30) {
                            System.out.println((char)27 + "[031mYou do not have enough HP to fight (" + character.getStats().getHitPoints() + ")" + (char)27 + "[0m");
                            int whatToDo = whatToDoWithLowHP(encountered);
                            if (whatToDo == 1) {
                                deadMonster(encountered);
                                win(encountered);
                                return true;
                            } else if (whatToDo == 2) {
                                WriteToFile.removeLine(character);
                                System.out.println((char)27 + "[031mThe " + encountered.getMonsterName() + " has eaten you alive.\n" +
                                        "GAME OVER!" + (char)27 + "[0m");
                                System.exit(0);
                            } else {
                                return true;
                            }
                        } else {
                            int win = Controller.battle(encountered, character);
                            if (win == 1) {
                                deadMonster(encountered);
                                win(encountered);
                                return true;
                            } else if (win == 2) {
                                WriteToFile.removeLine(character);
                                System.out.println((char)27 + "[031mYou died. GAME OVER!" + (char)27 + "[0m");
                                System.exit(0);
                            }
                        }
                    } else {
                        System.out.println((char)27 + "[031mYour choice is out of bounds, try again." + (char)27 + "[0m");
                    }
                } else {
                    System.out.println((char)27 + "[031mYour choice is out of bounds, try again." + (char)27 + "[0m");
                }
            }
        }
        return false;
    }

    private int Randomizer() {
        Random random = new Random();
        int rand = random.nextInt(21);
        if (rand > 10) {
            return 1;
        }
        return 0;
    }

    private int whatToDoWithLowHP(Monster encountered) {
        System.out.println("What do you want to do?");
        System.out.println("1. Try to escape");
        System.out.println("2. Stay and fight");

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            if (str.matches("\\s*[1-2]\\s*")) {
                int choice = Integer.parseInt(str);
                int rand = Randomizer();
                if (choice == 1 && rand == 1) {
                    character.getStats().setHitPoints(10);
                    System.out.println((char)27 + "[032mYou managed to escape! Because of your escaping skills, Houdini has granted you 10 HP." + (char)27 + "[0m");
                    return 3;
                } else if (choice == 2) {
                    character.getStats().setHitPoints(30);
                    System.out.println((char)27 + "[032mYou are a true warrior! Odin has granted you 30 HP." + (char)27 + "[0m\n");
                    return Controller.battle(encountered, character);
                } else {
                    System.out.println((char)27 + "[031mYou failed to escape the clutches of this beast. You must fight for your life!" + (char)27 + "[0m\n");
                    return Controller.battle(encountered, character);
                }
            } else {
                System.out.println((char)27 + "[031mYour choice is out of bounds, try again." + (char)27 + "[0m");
            }
        }
        return 0;
    }

    private void deadMonster(Monster dead) {
        if (!monsterArrayList.contains(dead)) {
            return;
        }
        monsterArrayList.remove(dead);
    }

    public void spawnMonstersOnMap() {
        int i = 0;
        while (i < this.monsters) {
            Random random = new Random();
            int xPosition = random.nextInt(size);
            int yPosition = random.nextInt(size);
            if (yPosition == this.yCoordinates || xPosition == this.xCoordinates) {
                do {
                    xPosition = random.nextInt(size);
                    yPosition = random.nextInt(size);
                } while (yPosition == this.yCoordinates || xPosition == this.xCoordinates);
            }
            Monster monster = NewCharacter.spawnMonsters(character);
            assert monster != null;
            monster.setEnemyPosition(xPosition, yPosition);
            registerMonster(monster);
            i++;
        }
    }

    private void setMonsters() {
        this.monsters = character.getStats().getLevel() * 8;

    }

    private void setPosition() {
        int x = 0;
        int y = 0;
        if ((size % 2) == 1) {
            x = size / 2;
            y = size / 2;
        } else if ((size % 2) == 0) {
            x = (size / 2);
            y = (size / 2);
        }
        this.xCoordinates = x;
        this.yCoordinates = y;
    }

    public void updatePosition(int positionX, int positionY) {
        this.xCoordinates += positionX;
        this.yCoordinates += positionY;
        if (this.xCoordinates < 0 || this.xCoordinates >= size) {
            this.xCoordinates = size / 2;
            updateXP(1);
            levelUp();
            set = false;
        } else if (this.yCoordinates < 0 || this.yCoordinates >= size) {
            this.yCoordinates = size / 2;
            updateXP(1);
            levelUp();
            set = false;
        } else {
            mapOutput();
        }
    }

    private void updateXP(int type) {
        switch (type) {
            case 1:
                int exp;
                if (character.getStats().getXp() <= 2450) {
                    exp = 2450;
                    character.getStats().setXp(exp);
                } else if (character.getStats().getXp() <= 4800) {
                    exp = 4800;
                    character.getStats().setXp(exp);
                } else if (character.getStats().getXp() <= 8050) {
                    exp = 8050;
                    character.getStats().setXp(exp);
                } else if (character.getStats().getXp() <= 12200) {
                    exp = 12200;
                    character.getStats().setXp(exp);
                }
                if (character.getStats().getXp() >= 12201) {
                    System.out.println("you've reached the maximum amount of XP! You WIN!");
                    System.exit(0);
                }
                levelUp();
                break;
            case 2:
                character.getStats().setXp(500);
                WriteToFile.removeLine(character);
                WriteToFile.writeCharactersStatsChange(character);
                levelUp();
                break;
        }
    }

    private void win(Monster encountered) {
        monsterArrayList.remove(encountered);
        deadMonster(encountered);
        updateXP(2);
        if (Controller.chance()) {
            System.out.println("You killed encountered enemy, he dropped an artifact." + "\n" +
                    "You can pickup his artifact (" + encountered.getArtifact().getType() + ")");
            System.out.println("1. Pick up");
            System.out.println("2. Continue without loot");

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String str = scanner.nextLine();
                if (str.matches("1") || str.matches("2")) {
                    int choice = Integer.parseInt(str);
                    if (choice == 1) {
                        Controller.PickUpLoot(encountered, character);
                        break;
                    } else if (choice == 2) {
                        break;
                    }
                } else {
                    System.out.println((char)27 + "[031mYour choice is out of bounds, try again." + (char)27 + "[0m");
                }
            }
        } else {
            System.out.println("You killed that beast!... and destroyed the artifact as well... (sigh)");
        }
    }

    private void levelUp() {
        if (character.getStats().getXp() > 1000 && character.getStats().getXp() < 2450) {
            this.level = 1;
        }
        else if (character.getStats().getXp() >= 2450 && character.getStats().getXp() < 4800) {
            this.level = 2;
        }
        else if (character.getStats().getXp() >= 4800 && character.getStats().getXp() < 8050) {
            this.level = 3;
        }
        else if (character.getStats().getXp() >= 8050 && character.getStats().getXp() < 12200) {
            this.level = 4;
        }
        else if (character.getStats().getXp() >= 12200) {
            this.level = 5;
        }

        if (this.level > character.getStats().getLevel()) {
            character.getStats().setLevel(this.level);
            WriteToFile.removeLine(character);
            WriteToFile.writeCharactersStatsChange(character);
            int opt = ConsoleViewSupport.printLevelUp();
            if (opt == 1) {
                Controller.start(character);
            }
        }
    }

    private Monster getBattle() {
        for (Monster value : monsterArrayList) {
            if (value.getMonsterCoordinatesY() == this.yCoordinates && value.getMonsterCoordinatesX() == this.xCoordinates) {
                return value;
            }
        }
        return null;
    }
}
