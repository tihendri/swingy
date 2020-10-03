package com.tihendri.swingy.controller;

import java.io.*;

public class Reader {

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
        System.out.println("Choose your previously created character:");
        System.out.println("_________________________________________\n");
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
