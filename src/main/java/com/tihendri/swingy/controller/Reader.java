package com.tihendri.swingy.controller;

import com.tihendri.swingy.model.characters.Character;

import javax.validation.constraints.NotNull;
import java.io.*;

public class Reader {

    public static void updatePlayersList(Character character) {
        try {
            File file = new File("Characters.txt");
            FileWriter fileWriter = new FileWriter(file);
            @NotNull(message = "Values from text file cannot be null/text file cannot be empty")
            String[] elements = readLines();
            String delLine = null;
            String newLine;
            assert elements != null;
            for (String str : elements) {
                if (str.contains(character.getCharacter()) && str.contains(character.getStats().getType())) {
                    delLine =str;
                }
            }
            newLine = (character.getStats().getType() + " " + character.getCharacter() + " " + character.getStats().getLevel() + " " +
                    character.getStats().getAttack() + " " + character.getStats().getDefence() + " " +
                    character.getStats().getHitPoints() + " " + character.getStats().getXp() + " " +
                    character.getArtifact().getType().toUpperCase());
            for (String str : elements) {
                if (str.equals(delLine)) {
                    fileWriter.write(newLine + "\n");
                } else {
                    fileWriter.write(str + "\n");
                }
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Faulty Characters file");
        }
    }

    public static String[] readLines() {
        try {
            File file = new File("Characters.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String str;
            String[] elements = new String[getLinesNumber()];
            int id = 0;
            while ((str = bufferedReader.readLine()) != null) {
                elements[id] = str;
                id++;
            }
            bufferedReader.close();
            return elements;
        } catch (IOException e) {
//            e.getMessage();
        }
        return null;
    }

    public static void getAllPlayers() {
        String[] elements;
        int id = 0;
        int count = 1;
        elements = readLines();
        System.out.println("Choose from listed players");
        while (id < getLinesNumber()) {
            assert elements != null;
            System.out.println(count++ + ". " + elements[id++]);
        }
    }

    public static int getLinesNumber() {
        try {
            File file = new File("Characters.txt");
            FileReader fileReader = new FileReader(file);
            LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
//            lineNumberReader.skip(Long.MAX_VALUE);
            int counter = lineNumberReader.getLineNumber();
            lineNumberReader.close();
            return counter;
        } catch (IOException e) {
//            e.getMessage();
        }
        return -1;
    }

    public static String getPlayer(int character) {
        String[] elements;
        elements = readLines();
        assert elements != null;
        return elements[character - 1];
    }
}
