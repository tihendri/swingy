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
    private boolean leveledUpAfterBattle = false;


    public ControllerMapGui(JFrame jFrame, Character character) {
        ControllerMapGui.character = character;
        this.jFrame = jFrame;
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

    public JTextArea mapOutputGui() {
        textArea.selectAll();
        textArea.replaceSelection("");
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
            boolean meetMonster = encounteredMonsterGui(this.yCoordinates, this.xCoordinates, monster.getMonsterCoordinatesY(), monster.getMonsterCoordinatesX());
            if (meetMonster) {
                if (leveledUpAfterBattle) {
                    set = false;
                    return mapOutputGui();
                }
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
                        textArea.append("w  w");
                        break;
                    case 1:
                    case 2:
                    case 3:
                        textArea.append("w  " + "m" + "  w");
                        break;
                    default:
                        textArea.append("|  " + "U" + "  |");
                        break;
                }
            }
            textArea.append("\n");
        }
        return textArea;
    }

    private boolean encounteredMonsterGui(int yCharacter, int xCharacter, int yMonster, int xMonster) {
        if ((xCharacter == xMonster) && (yCharacter == yMonster)) {
            Monster encountered = getBattle();
            int showButton = JOptionPane.YES_NO_OPTION;
            assert encountered != null;
            int result = JOptionPane.showConfirmDialog(this, "You've crossed paths with a " + encountered.getMonsterName() + ". Do you want to fight it?", "Fight?", showButton);
            if (result == 0) {
                if (character.getStats().getHitPoints() < 30) {
                    JOptionPane.showMessageDialog(null, "You do not have enough HP to fight (" + character.getStats().getHitPoints() + ")");
                    int whatToDo = whatToDoWithLowHP(encountered);
                    switch (whatToDo) {
                        case 1:
                            win(encountered);
                            return true;
                        case 2:
                            WriteToFile.removeLine(character);
                            JOptionPane.showMessageDialog(null, "The " + encountered.getMonsterName() + " has eaten you alive.\n" +
                                    "GAME OVER!");
                            jFrame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING));
                            break;
                        case 3:
                            return true;
                    }
                } else {
                    int win = Controller.battle(encountered, character);
                    if (win == 1) {
                        win(encountered);
                        return true;
                    } else if (win == 2) {
                        WriteToFile.removeLine(character);
                        JOptionPane.showMessageDialog(null, "you died. GAME OVER!");
                        jFrame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING));
                    }
                }
            } else {
                character.getStats().setXp(-500);
                JOptionPane.showMessageDialog(null, "You are a coward, but okay... you have lost 500 experience points because you ran away. Your current XP: " + (character.getStats().getXp()));
                if (character.getStats().getXp() <= 0) {
                    JOptionPane.showMessageDialog(null, "You ran out of experience points, therefore it's GAME OVER!");
                    jFrame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING));
                }
            }
        }
        return false;
    }

    private int Randomizer() {
        Random random = new Random();
        int rand = random.nextInt(21);
        if (rand >= 13) {
            return 1;
        }
        return 0;
    }

    private int whatToDoWithLowHP(Monster encountered) {
        int showButton = JOptionPane.YES_NO_OPTION;
        int choice = JOptionPane.showConfirmDialog(this, "Your health has reached a minimum (" + character.getStats().getHitPoints() + "). Do you want to attempt an escape?", "Flee or Fight?", showButton);
        int rand = Randomizer();
        if (choice == 0 && rand == 1) {
            character.getStats().setHitPoints(10);
            JOptionPane.showMessageDialog(null, "You managed to escape! Because of your escaping skills, Houdini has granted you 10 HP.");
            return 3;
        } else if (choice == 1) {
            character.getStats().setHitPoints(30);
            JOptionPane.showMessageDialog(null, "You are a true warrior! Odin has granted you 30 HP.");
            return Controller.battle(encountered, character);
        } else {
            JOptionPane.showMessageDialog(null, "You failed to escape the clutches of this beast. You face a terrible fate.");
            return 2;
        }
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

    public void updatePositionGui(int positionX, int positionY) {
        this.xCoordinates += positionX;
        this.yCoordinates += positionY;
        if (this.xCoordinates < 0 || this.xCoordinates >= size) {
            this.xCoordinates = size / 2;
            updateXP(1);
            levelUp();
            set = false;
            mapOutputGui();
        } else if (this.yCoordinates < 0 || this.yCoordinates >= size) {
            this.yCoordinates = size / 2;
            updateXP(1);
            levelUp();
            set = false;
            mapOutputGui();
        } else {
//            textArea.selectAll();
//            textArea.replaceSelection("");
            mapOutputGui();
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
            } else if (character.getStats().getXp() <= 12200) {
                exp = 12200;
                character.getStats().setXp(exp);
            }
            if (character.getStats().getXp() >= 12201) {
                JOptionPane.showMessageDialog(null, "You've reached the maximum amount of XP! You WIN!");
                GuiDisplay.endGame();
            }
            levelUp();
        } else if (type == 2) {
            character.getStats().setXp(550);
            WriteToFile.removeLine(character);
            WriteToFile.writeCharactersStatsChange(character);
            levelUp();
//                mapOutputGui();
        }
    }

    private void win(Monster encountered) {
//        monsterArrayList.remove(encountered);
        deadMonster(encountered);
        if ((character.getStats().getXp() >= 2000 && character.getStats().getXp() < 2450) ||
                (character.getStats().getXp() >= 4350 && character.getStats().getXp() < 4800) ||
                (character.getStats().getXp() >= 7700 && character.getStats().getXp() < 8050) ||
                (character.getStats().getXp() >= 12000 && character.getStats().getXp() < 12200)) {
            updateXP(1);
        } else {
            updateXP(2);
        }
        if (Controller.chance()) {
            int showButton = JOptionPane.YES_NO_OPTION;
            int yesOrNo = JOptionPane.showConfirmDialog(this, "You destroyed the monster and it has dropped " + encountered.getArtifact().getType() + ". Do you want to pick it up?", "Loot?", showButton);
            if (yesOrNo == 0) {
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
        } else {
//            updateXP(2);
            JOptionPane.showMessageDialog(null, "You killed that beast!... and destroyed the artifact as well... (sigh)");
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
        else if (character.getStats().getXp() > 12200) {
            this.level = 5;
        }

        if (this.level > character.getStats().getLevel()) {
            character.getStats().setLevel(this.level);
            WriteToFile.removeLine(character);
            WriteToFile.writeCharactersStatsChange(character);
            JOptionPane.showMessageDialog(null, "You've leveled up!");
//            monsterArrayList.removeAll(monsterArrayList);
            textArea.selectAll();
            textArea.replaceSelection("");
            leveledUpAfterBattle = true;
//            GuiDisplay.game();
//            mapOutputGui();
//            textArea.append(this.level + "\n");
        } else if (this.level == character.getStats().getLevel()) {
            textArea.selectAll();
            textArea.replaceSelection("");
            tempMonsterArray.addAll(monsterArrayList);
            leveledUpAfterBattle = false;
//            monsterArrayList.removeAll(monsterArrayList);
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
