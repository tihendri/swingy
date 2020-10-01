package com.tihendri.swingy.view.console;

import com.tihendri.swingy.controller.Controller;
import com.tihendri.swingy.controller.Reader;
import com.tihendri.swingy.model.characters.Character;
import com.tihendri.swingy.controller.DatabaseOps;

import java.util.Scanner;

public class ConsoleView {

    public static void start() {
        String player;
        int type;
        int newOrOldPlayer;
        int opt = 0;
        int start;
        Character character;

        try {
            newOrOldPlayer = ConsoleViewSupport.createOrSelect();
            if (newOrOldPlayer == 1) {
                player = ConsoleViewSupport.enterHeroName();
                type = ConsoleViewSupport.choosePlayerType();
                character = DatabaseOps.newCharacterDB(type, player);
                start = ConsoleViewSupport.printStatistics(player, character, type);
                if (start == 1) {
                    Controller.start(character);
                } else {
                    System.out.println("goodbye");
                    System.exit(0);
                }
            } else if (newOrOldPlayer == 2) {
                Reader.getAllCharacters();
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNextLine()) {
                    String str = scanner.nextLine();
                    int counter = Reader.getLinesNumber();
                    if (ConsoleViewSupport.IsDigit(str)) {
                        try {
                            int id = Integer.parseInt(str);
                            if (id > 0 && id <= counter) {
                                opt = id;
                                break;
                            }
                        } catch (Exception e) {
                            System.out.println((char)27 + "[031mWrong input value!" + (char)27 + "[0m");
                        }
                    } else {
                        System.out.println((char)27 + "[031mWrong input value!" + (char)27 + "[0m");
                    }
                }
                character = DatabaseOps.setCharacter(Reader.getYourCharacter(opt));
                Controller.start(character);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
