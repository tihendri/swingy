package com.tihendri.swingy.view.gui;

import com.tihendri.swingy.controller.ControllerMapGui;
import com.tihendri.swingy.controller.Reader;
import com.tihendri.swingy.controller.WriteToFile;
import com.tihendri.swingy.controller.DatabaseOps;
import com.tihendri.swingy.model.characters.Character;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GuiDisplay extends JFrame {

    public int type;
    public String character;
    public String playerData;
    public String[] ch = null;
    public static Character player = new Character();

    public GuiDisplay() {}

    public void createView() {
        JLabel label = new JLabel("Create Character");
        JLabel label1 = new JLabel("Enter character name");
        JButton helloButton = new JButton("ENTER");
        JFrame helloF = new JFrame("Character Creation");
        JButton backButton = new JButton("Back");
        JTextField playerName = new JTextField();

        label.setBounds(165, 95, 200, 35);
        label.setBackground(Color.GRAY);
        label.setForeground(Color.ORANGE);
        label.setOpaque(true);
        label.setFont(new Font("Courier", Font.PLAIN, 16));

        label1.setBounds(190, 170, 150, 30);
        label1.setBackground(Color.GRAY);
        label1.setForeground(Color.ORANGE);
        label1.setOpaque(true);
        label1.setFont(new Font("Courier", Font.PLAIN, 14));

        playerName.setBounds(130, 210, 250, 30);
        playerName.setCaretColor(Color.ORANGE);
        playerName.setBackground(Color.GRAY);
        playerName.setForeground(Color.ORANGE);
        playerName.setText("Enter name here");
        Border border = BorderFactory.createLineBorder(Color.ORANGE, 2);
        playerName.setBorder(border);

        helloButton.setBounds(155, 260, 200, 30);
        helloButton.setBackground(Color.ORANGE);
        helloButton.setOpaque(true);
        helloButton.setBorderPainted(false);

        backButton.setBounds(200, 340, 100, 40);
        backButton.setBackground(Color.ORANGE);
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);

        helloF.add(label);
        helloF.add(label1);
        helloF.add(playerName);
        helloF.add(helloButton);
        helloF.add(backButton);
        helloF.setBackground(Color.ORANGE);
        helloF.setSize(500, 500);
        helloF.setBackground(Color.GRAY);
        helloF.getContentPane().setBackground(Color.lightGray);
        helloF.setLocationRelativeTo(null);
        helloF.setLayout(null);
        helloF.setVisible(true);
        helloF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        helloF.setResizable(false);

        backButton.addActionListener(e -> {
            start();
            helloF.dispose();
        });

        helloButton.addActionListener(e -> {
            character = playerName.getText();
            character = character.trim();
            if (character.length() > 0){
                ch = character.split("\\s");
                character = String.join("_", ch);
                if (character.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Invalid input.");
                }
                else {
                    createPlayerView();
                    helloF.setVisible(false);
                    helloF.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid input.");
            }
        });
        System.out.println("createView");
    }

    public void start() {
        JButton createPlayerButton = new JButton("Create New Character");
        JButton selectPlayerButton = new JButton("Select Old Character");
        JFrame playerFrame = new JFrame("SWINGY");

        createPlayerButton.setBounds(180, 170, 220, 40);
        createPlayerButton.setBackground(Color.ORANGE);
        createPlayerButton.setOpaque(true);
        createPlayerButton.setBorderPainted(false);
        createPlayerButton.setFont(new Font("Courier", Font.PLAIN, 16));
        createPlayerButton.setForeground(Color.GRAY);

        selectPlayerButton.setBounds(180, 260, 220, 40);
        selectPlayerButton.setBackground(Color.ORANGE);
        selectPlayerButton.setOpaque(true);
        selectPlayerButton.setBorderPainted(false);
        selectPlayerButton.setFont(new Font("Courier", Font.PLAIN, 16));

        playerFrame.add(createPlayerButton);
        playerFrame.add(selectPlayerButton);
        playerFrame.setSize(500, 500);
        playerFrame.setBackground(Color.LIGHT_GRAY);
        playerFrame.getContentPane().setBackground(Color.GRAY);
        playerFrame.setLocationRelativeTo(null);
        playerFrame.setLayout(null);
        playerFrame.setVisible(true);
        playerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playerFrame.setResizable(false);

        createPlayerButton.addActionListener(e -> {
            createView();
            playerFrame.dispose();
        });

        selectPlayerButton.addActionListener(e -> {
            selectPlayerView();
            playerFrame.setVisible(false);
            playerFrame.dispose();
        });
        System.out.println("start");
    }

    public void selectPlayerView() {
        String[] elements = Reader.readLines();
        JList<? extends String> playerList = new JList<>(elements);
        JLabel label = new JLabel("Select character");
        JFrame selectFr = new JFrame("Select Character");
        JButton enterButton = new JButton("Continue");
        JButton quit = new JButton("Quit");
        JButton backButton = new JButton("Back");

        label.setBounds(20, 20, 200, 30);
        label.setBounds(160, 20, 200, 30);
        label.setBackground(Color.ORANGE);
        label.setOpaque(true);
        label.setFont(new Font("Courier", Font.PLAIN, 15));

        playerList.setBounds(20, 50, 350, 520);
        playerList.setBounds(20, 70, 320, 360);
        playerList.setBackground(Color.ORANGE);
        playerList.setOpaque(true);
        playerList.setFont(new Font("Courier", Font.PLAIN, 12));
        playerList.setSelectedIndex(0);

        enterButton.setBounds(365, 70, 100, 40);
        enterButton.setBackground(Color.ORANGE);
        enterButton.setOpaque(true);
        enterButton.setBorderPainted(false);
        enterButton.setFont(new Font("Courier", Font.PLAIN, 12));

        quit.setBounds(365, 390, 100, 40);
        quit.setBackground(Color.ORANGE);
        quit.setOpaque(true);
        quit.setBorderPainted(false);
        quit.setFont(new Font("Courier", Font.PLAIN, 13));

        backButton.setBounds(365, 170, 100, 40);
        backButton.setBackground(Color.ORANGE);
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);
        backButton.setFont(new Font("Courier", Font.PLAIN, 13));

        selectFr.add(label);
        selectFr.add(enterButton);
        selectFr.add(quit);
        selectFr.add(playerList);
        selectFr.add(backButton);
        selectFr.setSize(500, 500);
        selectFr.setBackground(Color.gray);
        selectFr.getContentPane().setBackground(Color.GRAY);
        selectFr.setLocationRelativeTo(null);
        selectFr.setLayout(null);
        selectFr.setVisible(true);
        selectFr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        selectFr.setResizable(false);

        playerList.addListSelectionListener(arg0 -> {
            playerData = playerList.getSelectedValue();
            player = DatabaseOps.setCharacter(playerData);
        });

        enterButton.addActionListener(e -> {
            if (playerData == null) {
                JOptionPane.showMessageDialog(null, "You must select your hero before continuing!");
            } else {
                game();
                selectFr.setVisible(false);
                selectFr.dispose();
            }
        });

        backButton.addActionListener(e -> {
            start();
            selectFr.dispose();
        });

        quit.addActionListener(e -> selectFr.dispose());
        System.out.println("selectPlayersView");

    }

    public void createPlayerView() {
        ButtonGroup buttonGroup = new ButtonGroup();
        JLabel faction = new JLabel("CHOOSE YOUR FACTION");
        JRadioButton viking = new JRadioButton("Viking");
        JRadioButton crusader = new JRadioButton("Crusader");
        JRadioButton druid = new JRadioButton("Druid");
        JFrame createF = new JFrame("Create Character");
        JButton enterButton = new JButton("CONTINUE");
        JButton backButton = new JButton("Back");

        faction.setBounds(165, 55, 200, 30);
        faction.setForeground(Color.ORANGE);
        faction.setFont(new Font("Courier", Font.PLAIN, 16));

        viking.setBounds(200, 130, 100, 40);
        viking.setForeground(Color.ORANGE);
        viking.setBackground(Color.GRAY);
        viking.setFont(new Font("Courier", Font.PLAIN, 14));
        viking.setSelected(true);

        crusader.setBounds(200, 160, 100, 40);
        crusader.setForeground(Color.ORANGE);
        crusader.setBackground(Color.GRAY);
        crusader.setFont(new Font("Courier", Font.PLAIN, 14));

        druid.setBounds(200, 190, 100, 40);
        druid.setForeground(Color.ORANGE);
        druid.setBackground(Color.GRAY);
        druid.setFont(new Font("Courier", Font.PLAIN, 14));

        enterButton.setBounds(200, 230, 150, 40);
        enterButton.setBackground(Color.ORANGE);
        enterButton.setOpaque(true);
        enterButton.setBorderPainted(false);
        enterButton.setFont(new Font("Courier", Font.PLAIN, 16));

        backButton.setBounds(200, 360, 100, 40);
        backButton.setBackground(Color.ORANGE);
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);
        backButton.setFont(new Font("Courier", Font.PLAIN, 16));

        buttonGroup.add(viking);
        buttonGroup.add(crusader);
        buttonGroup.add(druid);

        createF.add(viking);
        createF.add(crusader);
        createF.add(druid);
        createF.add(enterButton);
        createF.add(backButton);
        createF.add(faction);
        createF.setSize(500, 500);
        createF.setBackground(Color.GRAY);
        createF.getContentPane().setBackground(Color.LIGHT_GRAY);
        createF.setLocationRelativeTo(null);
        createF.setLayout(null);
        createF.setVisible(true);
        createF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createF.setResizable(false);

        enterButton.addActionListener(e -> {
            if (viking.isSelected()) {
                type = 1;
            } else if (crusader.isSelected()) {
                type = 2;
            } else if (druid.isSelected()) {
                type = 3;
            }
            displayCharacterStats();
            createF.setVisible(false);
            createF.dispose();
        });

        backButton.addActionListener(e -> {
            createView();
            createF.dispose();
        });
        System.out.println("createPlayerView");
    }

    public void displayCharacterStats(){
        player = DatabaseOps.newCharacterDB(type, character);
        String artifact;
        JLabel label1 = new JLabel("Character: " + character);
        JLabel label2 = new JLabel("Faction: " + player.getStats().getType());
        JLabel label3 = new JLabel("Level: " +  (player.getStats().getLevel()));
        JLabel label4 = new JLabel("Attack: " + (player.getStats().getAttack()));
        JLabel label5 = new JLabel("Defense: " + (player.getStats().getDefence()));
        JLabel label6 = new JLabel("HitPoints: " + (player.getStats().getHitPoints()));
        JLabel label7 = new JLabel("Artifact: " + (artifact = player.getArtifact().getType()));
        JLabel label8 = new JLabel("Player Statistics");
        JFrame statisticsF = new JFrame("Player Statistics");
        JButton enterButton = new JButton("Enter");

        label1.setBounds(110, 130, 400, 30);
        label1.setForeground(Color.ORANGE);
        label1.setFont(new Font("Courier", Font.PLAIN, 13));
        label2.setBounds(110, 150, 400, 30);
        label2.setForeground(Color.ORANGE);
        label2.setFont(new Font("Courier", Font.PLAIN, 13));
        label3.setBounds(110, 170, 400, 30);
        label3.setForeground(Color.ORANGE);
        label3.setFont(new Font("Courier", Font.PLAIN, 13));
        label4.setBounds(110, 190, 400, 30);
        label4.setForeground(Color.ORANGE);
        label4.setFont(new Font("Courier", Font.PLAIN, 13));
        label5.setBounds(110, 210, 400, 30);
        label5.setForeground(Color.ORANGE);
        label5.setFont(new Font("Courier", Font.PLAIN, 13));
        label6.setBounds(110, 230, 400, 30);
        label6.setForeground(Color.ORANGE);
        label6.setFont(new Font("Courier", Font.PLAIN, 13));
        label7.setBounds(110, 250, 400, 30);
        label7.setForeground(Color.ORANGE);
        label7.setFont(new Font("Courier", Font.PLAIN, 13));
        label8.setBounds(170, 95, 400, 30);
        label8.setForeground(Color.ORANGE);
        label8.setFont(new Font("Courier", Font.PLAIN, 13));

        enterButton.setBounds(180, 300, 100, 40);
        enterButton.setBackground(Color.ORANGE);
        enterButton.setOpaque(true);
        enterButton.setBorderPainted(false);
        enterButton.setFont(new Font("Courier", Font.PLAIN, 12));

        statisticsF.add(label1);
        statisticsF.add(label2);
        statisticsF.add(label3);
        statisticsF.add(label4);
        statisticsF.add(label5);
        statisticsF.add(label6);
        statisticsF.add(label7);
        statisticsF.add(label8);
        statisticsF.add(enterButton);
        statisticsF.setSize(500, 500);
        statisticsF.setBackground(Color.GRAY);
        statisticsF.getContentPane().setBackground(Color.DARK_GRAY);
        statisticsF.setLocationRelativeTo(null);
        statisticsF.setLayout(null);
        statisticsF.setVisible(true);
        statisticsF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        statisticsF.setResizable(false);

        enterButton.addActionListener(arg0 -> {
            playerData = player.getStats().getType() + " " + character + " " + player.getStats().getLevel() + " " +
                    player.getStats().getAttack() + " " + player.getStats().getAttack() + " " +
                    player.getStats().getHitPoints() + " " + player.getStats().getXp() + " " + artifact.toUpperCase();
            WriteToFile.writeCharacters(playerData);
            WriteToFile.close();
            game();
            statisticsF.setVisible(false);
            statisticsF.dispose();
        });
        System.out.println("PlayerStats");
    }

    public static void game() {
        JFrame swingyF = new JFrame("Ongoing Game");
        JLabel jungle = new JLabel(" Swingy Jungle");
        ControllerMapGui map = new ControllerMapGui(swingyF, player);
        JTextArea textArea = map.mapOutput();
        JButton northButton = new JButton("NORTH");
        JButton southButton = new JButton("SOUTH");
        JButton eastButton = new JButton("EAST");
        JButton westButton = new JButton("WEST");

        jungle.setBounds(350, 40, 175, 30);
        jungle.setForeground(Color.ORANGE);
        jungle.setBackground(Color.GRAY);
        jungle.setOpaque(true);
        jungle.setFont(new Font("Courier", Font.PLAIN, 20));
        Border labelBorder = BorderFactory.createLineBorder(Color.ORANGE, 5);
        jungle.setBorder(labelBorder);

        textArea.setBounds(50, 100, 757, 615);
        textArea.setForeground(Color.ORANGE);
        textArea.setBackground(Color.DARK_GRAY);
        Border border1 = BorderFactory.createLineBorder(Color.ORANGE, 5);
        textArea.setBorder(border1);

        northButton.setBounds(140, 760, 100, 40);
        northButton.setBackground(Color.ORANGE);
        northButton.setOpaque(true);
        northButton.setBorderPainted(false);
        northButton.setFont(new Font("Courier", Font.PLAIN, 13));

        southButton.setBounds(280, 760, 100, 40);
        southButton.setBackground(Color.ORANGE);
        southButton.setOpaque(true);
        southButton.setBorderPainted(false);
        southButton.setFont(new Font("Courier", Font.PLAIN, 13));

        westButton.setBounds(440, 760, 100, 40);
        westButton.setBackground(Color.ORANGE);
        westButton.setOpaque(true);
        westButton.setBorderPainted(false);
        westButton.setFont(new Font("Courier", Font.PLAIN, 13));

        eastButton.setBounds(590, 760, 100, 40);
        eastButton.setBackground(Color.ORANGE);
        eastButton.setOpaque(true);
        eastButton.setBorderPainted(false);
        eastButton.setFont(new Font("Courier", Font.PLAIN, 13));

        northButton.addActionListener(e -> map.updatePosition(0, -1));

        southButton.addActionListener(e -> map.updatePosition(0, 1));

        eastButton.addActionListener(e -> map.updatePosition(1, 0));

        westButton.addActionListener(e -> map.updatePosition(-1, 0));

        swingyF.add(northButton);
        swingyF.add(southButton);
        swingyF.add(eastButton);
        swingyF.add(westButton);
        swingyF.add(textArea);
        swingyF.add(jungle);
        swingyF.setSize(850, 850);
        swingyF.setLocationRelativeTo(null);
        swingyF.setLayout(null);
        swingyF.setVisible(true);
        swingyF.setResizable(false);
        swingyF.setBackground(Color.GRAY);
        swingyF.getContentPane().setBackground(Color.DARK_GRAY);
        swingyF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println("game");
    }

    public static void endGame() {
        JLabel label = new JLabel("Going on with out adventure");
        JButton close = new JButton("GAME COMPLETED");
        JFrame endGameF = new JFrame("Game Ended");

        label.setBounds(145, 90, 150, 40);
        label.setForeground(Color.ORANGE);
        label.setFont(new Font("Courier", Font.PLAIN, 16));

        close.setBounds(150, 150, 100, 40);
        close.setBackground(Color.ORANGE);
        close.setOpaque(true);
        close.setBorderPainted(false);
        close.setFont(new Font("Courier", Font.PLAIN, 12));

        close.addActionListener(arg0 -> {
            endGameF.dispose();
            System.exit(0);
        });

        endGameF.add(label);
        endGameF.add(close);
        endGameF.setSize(400, 400);
        endGameF.setBackground(Color.GRAY);
        endGameF.getContentPane().setBackground(Color.GRAY);
        endGameF.setLocationRelativeTo(null);
        endGameF.setLayout(null);
        endGameF.setVisible(true);
        endGameF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        endGameF.setResizable(false);
        System.out.println("endgame");
    }

}
