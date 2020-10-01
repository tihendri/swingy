package com.tihendri.swingy.controller;

import java.io.*;

public class Reader {

//    public static String updatedStats;
//
//    public static void updatePlayersList(Character character) {
//        try {
//            File file = new File("Characters.txt");
//            FileWriter fileWriter = new FileWriter(file);
//            Scanner scanner = new Scanner(file);
////            String[] elements = readLines();
//            String delLine = null;
//            String newLine;
//            WriteToFile.removeLine(character);
////            assert elements != null;
////            for (String str : elements) {
////                if (str.contains(character.getCharacter())) {
////                    delLine = str;
////                }
////            }
//            updatedStats = character.getStats().getType() + " " + character.getCharacterName() + " " + character.getStats().getLevel() + " " +
//                    character.getStats().getAttack() + " " + character.getStats().getDefence() + " " +
//                    character.getStats().getHitPoints() + " " + character.getStats().getXp() + " " +
//                    character.getArtifact().getType().toUpperCase();
////            while (scanner.hasNextLine()) {
////            for (String str : elements) {
////                String str = scanner.nextLine();
////                if (str.contains(character.getCharacter())) {
////                    WriteToFile.writeCharacters(updatedStats);
////                    fileWriter.write(newLine + "\n");
////                    break;
////                }
////                else {
////                    fileWriter.write(str + "\n");
////                }
////            }
////            }
////            fileWriter.close();
//        } catch (IOException e) {
//            System.out.println("Faulty Characters file");
//        }
//    }

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
            System.exit(1);
        }
        return null;
    }

    public static void getAllCharacters() {
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
            lineNumberReader.skip(Long.MAX_VALUE);
            int counter = lineNumberReader.getLineNumber();
            lineNumberReader.close();
            return counter;
        } catch (IOException e) {
            System.exit(1);
        }
        return -1;
    }

    public static String getYourCharacter(int character) {
        String[] elements;
        elements = readLines();
        assert elements != null;
        return elements[character - 1];
    }
}
