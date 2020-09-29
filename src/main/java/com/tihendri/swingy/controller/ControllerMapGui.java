package com.tihendri.swingy.controller;

import com.tihendri.swingy.model.artifacts.Armor;
import com.tihendri.swingy.model.artifacts.Helm;
import com.tihendri.swingy.model.artifacts.Weapon;
import com.tihendri.swingy.model.characters.Character;
import com.tihendri.swingy.model.characters.Monster;
import com.tihendri.swingy.model.characters.NewCharacter;
import com.tihendri.swingy.view.gui.GuiDisplay;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

public class ControllerMapGui extends JFrame {

    public JTextArea textArea = new JTextArea();
    public JFrame jFrame;
    private static int[][] map;
    private int monsters;
    private static int size;
    private int yCoordinates;
    private int xCoordinates;
    public int xOld;
    public int yOld;
    public static Character character;
    private Monster monster;
    private static final ArrayList<Monster> monsterArrayList = new ArrayList<>();
    private static final ArrayList<Monster> tempMonsterArray = new ArrayList<>();
    private boolean set;
    private int level;
    private static int mapYCoordinate;
    private static int mapXCoordinate;


    public ControllerMapGui(JFrame jFrame, Character character) {
        ControllerMapGui.character = character;
        this.jFrame = jFrame;
    }

    public void updatePosition(int positionX, int positionY) {
        this.xCoordinates += positionX;
        this.yCoordinates += positionY;
//        positionAssist(this.xCoordinates, this.yCoordinates);
        if (this.xCoordinates < 0 || this.xCoordinates >= size) {
            this.xCoordinates = size / 2;
            updateXP(1);
            levelUp();
            set = false;
            mapOutput();
        } else if (this.yCoordinates < 0 || this.yCoordinates >= size) {
            this.yCoordinates = size / 2;
            updateXP(1);
            levelUp();
            set = false;
            mapOutput();
        } else {
            textArea.selectAll();
            textArea.replaceSelection("");
            mapOutput();
        }
    }

    public JTextArea mapOutput() {
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
            boolean meetMonster = encounteredMonster(this.yCoordinates, this.xCoordinates, monster.getMonsterCoordinatesY(), monster.getMonsterCoordinatesX());
            if (meetMonster) {
                break;
            }
        }
        textArea.append("Level: " + character.getStats().getLevel() + " | " +
                "Attack: " + character.getStats().getAttack() + " | " +
                "Defence: " + character.getStats().getDefence() + " | " +
                "Hit points: " + character.getStats().getHitPoints() + " | " +
                "Experience: " + character.getStats().getXp() + "\n\n");

        for (int y = 0; y < mapYCoordinate; y++) {
            for (int x = 0; x < mapXCoordinate; x++) {
                switch (map[y][x]) {
                    case 0:
                        textArea.append("w   w");
                        break;
                    case 1:
                    case 2:
                    case 3:
                        textArea.append("w " + "m" + " w");
                        break;
                    default:
                        textArea.append("| " + "U" + " |");
                        break;
                }
            }
            textArea.append("\n");
        }
        return textArea;
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

    private boolean encounteredMonster(int yp, int xp, int yv, int xv) {
        if ((xp == xv) && (yp == yv)) {
            int showButton = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(this, "You've crossed paths with a " + monster.getMonsterName() + ". Do you want to fight it?", "Fight?", showButton);
            if (result == 0) {
                Monster encountered = getBattle();
                int win = ConsoleController.battle(encountered, character);
                if (win == 1) {
                    win(encountered);
                    deadMonster(encountered);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "you died. GAME OVER!");
                    jFrame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING));

                }
            } else {
                character.getStats().setXp(-1000);
                JOptionPane.showMessageDialog(null, "You are a coward, but okay... you have lost 500 experience points because you ran away. Your current XP: " + (character.getStats().getXp()));
//                textArea.selectAll();
//                textArea.replaceSelection("");
//                textArea.append("You are a coward, but okay...\n\n");
//                textArea.append("You have lost 500 experience points because you ran away.\n\n");
//                textArea.append("Your current XP: " + (character.getStats().getXp()));
//                character.getStats().setXp(-500);
                if (character.getStats().getXp() <= 0) {
                    JOptionPane.showMessageDialog(null, "You ran out of experience points, therefore it's GAME OVER!");
                    jFrame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING));
                }
                this.yCoordinates = this.yOld;
                this.xCoordinates = this.xOld;
            }
        }
        return false;
    }

    private void deadMonster(Monster dead) {
        if (!monsterArrayList.contains(dead)) {
            return;
        }
        monsterArrayList.remove(dead);
    }

    private void spawnMonstersOnMap() {
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
            monster = NewCharacter.spawnMonsters(character);
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

    private void positionAssist(int positionX, int positionY) {
        if (positionX < 0 || positionY < 0) {
            updateXP(1);
            levelUp();
            set = false;
            mapOutput();
        } else if (positionX >= size || positionY >= size) {
            updateXP(1);
            levelUp();
            set = false;
            mapOutput();
        } else {
            textArea.selectAll();
            textArea.replaceSelection("");
            mapOutput();
        }
    }

    private void updateXP(int type) {
        if (type == 1) {
            int exp;
            if (character.getStats().getXp() < 2450) {
                exp = 2450;
                character.getStats().setXp(exp);
            } else if (character.getStats().getXp() < 4800) {
                exp = 4800;
                character.getStats().setXp(exp);
            } else if (character.getStats().getXp() < 8050) {
                exp = 8050;
                character.getStats().setXp(exp);
            } else if (character.getStats().getXp() < 12200) {
                exp = 12200;
                character.getStats().setXp(exp);
            } else if (character.getStats().getXp() < 12201) {
                System.out.println("you've reached the maximum amount of XP! You WIN!");
                GuiDisplay.endGame();
            }
            levelUp();
        } else if (type == 2) {
            character.getStats().setXp(character.getStats().getXp());
            Reader.updatePlayersList(character);
            levelUp();
        }
    }

    private void win(Monster encountered) {
//        monsterArrayList.remove(encountered);
//        deadMonster(encountered);
        updateXP(2);
        if (ConsoleController.chance()) {
            int showButton = JOptionPane.YES_NO_OPTION;
            int yesOrNo = JOptionPane.showConfirmDialog(this, "You destroyed the monster and it has dropped " + encountered.getArtifact().getType() + ". Do you want to pick it up?", "Loot?", showButton);
            if (yesOrNo == 0) {
                String type = monster.getArtifact().getType();
                switch (type) {
                    case "WEAPON":
                        Weapon weapon = new Weapon("WEAPON");
                        character.setArtifact(weapon);
                        character.getStats().setAttack(70);
                        Reader.updatePlayersList(character);
//                        ConsoleController.start(character);
//                        GuiDisplay.game();
                        break;
                    case "ARMOR":
                        Armor armor = new Armor("ARMOR");
                        character.setArtifact(armor);
                        character.getStats().setDefence(60);
                        Reader.updatePlayersList(character);
//                        ConsoleController.start(character);
//                        GuiDisplay.game();
                        break;
                    case "HELM":
                        Helm helm = new Helm("HELM");
                        character.setArtifact(helm);
                        character.getStats().setHitPoints(80);
                        Reader.updatePlayersList(character);
//                        ConsoleController.start(character);
//                        GuiDisplay.game();
                        break;
                }
            } else if (yesOrNo == 1) {
                updateXP(2);
            }
        } else {
            updateXP(2);
            JOptionPane.showMessageDialog(null, "You killed that beast!... and destroyed the artifact as well... (sigh)");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.exit(0);
            }
//            ConsoleController.start(character);
//            GuiDisplay.game();
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
        else if (character.getStats().getXp() <= 12200) {
            this.level = 5;
        }

        if (this.level > character.getStats().getLevel()) {
            character.getStats().setLevel(this.level);
            Reader.updatePlayersList(character);
            JOptionPane.showMessageDialog(null, "You've leveled up!");
            monsterArrayList.removeAll(monsterArrayList);
            textArea.append(this.level + "\n");
        } else if (this.level == character.getStats().getLevel()) {
            textArea.selectAll();
            textArea.replaceSelection("");
            tempMonsterArray.addAll(monsterArrayList);
            monsterArrayList.removeAll(monsterArrayList);
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
