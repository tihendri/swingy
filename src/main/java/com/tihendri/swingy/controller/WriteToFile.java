package com.tihendri.swingy.controller;

import com.tihendri.swingy.model.characters.Character;

import java.io.*;

public class WriteToFile {

    public static File file = null;
    public static FileWriter fileWriter;

    public WriteToFile() {}

    public static void close() {
        try {
            if (fileWriter != null) {
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createFile() {
        try {
            if (file == null) {
                file = new File("Characters.txt");
                file.createNewFile();
            }
            fileWriter = new FileWriter(file, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeCharacters(String line) {
        try {
            file = new File("Characters.txt");
            fileWriter = new FileWriter(file, true);
            fileWriter.append(line + "\n");
            fileWriter.close();
            System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeCharactersStatsChange(Character character) {
        try {
            String line = character.getStats().getType() + " " + character.getCharacterName() + " " + character.getStats().getLevel() + " " +
                    character.getStats().getAttack() + " " + character.getStats().getDefence() + " " +
                    character.getStats().getHitPoints() + " " + character.getStats().getXp() + " " +
                    character.getArtifact().getType().toUpperCase();
            file = new File("Characters.txt");
            fileWriter = new FileWriter(file, true);
            fileWriter.write(line + "\n");
            fileWriter.close();
//            System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeLine(Character character) {
        try{
            File inputFile = new File("Characters.txt");
            File tempFile = new File("Characters_temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String lineToRemove = character.getCharacterName();
            String currentLine;

            while((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if(trimmedLine.contains(lineToRemove)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            boolean successful = tempFile.renameTo(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
