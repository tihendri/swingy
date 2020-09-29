package com.tihendri.swingy.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
//                file.createNewFile();
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
            fileWriter.append(line).append("\n");
            fileWriter.close();
            System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
