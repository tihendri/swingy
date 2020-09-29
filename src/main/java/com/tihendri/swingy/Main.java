package com.tihendri.swingy;

import com.tihendri.swingy.controller.WriteToFile;
import com.tihendri.swingy.view.console.ConsoleView;
import com.tihendri.swingy.view.gui.GuiDisplay;

public class Main {

	public static boolean guiOrConsole;

    public static void main(String[] args) {

		WriteToFile.createFile();
        if (args.length != 1 || (!args[0].equals("console") && !args[0].equals("gui"))) {
			System.out.println("Usage: java -jar [path] console | gui");
			System.exit(0);
        }

		if (args[0].equalsIgnoreCase("console")) {
			guiOrConsole = false;
			ConsoleView.start();
//			System.out.println("Terminal loading... (not really)");
		} else {
			guiOrConsole = true;
//			System.setProperty("java.awt.headless", "false");
//			SwingUtilities.invokeLater(() -> {
//				JFrame f = new JFrame("swingy");
//				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				f.setVisible(true);
//			});

			GuiDisplay guiDisplay = new GuiDisplay();
			guiDisplay.guiView();
//			System.out.println("Graphical User Interface loading... (not really)");
		}
		WriteToFile.close();
//		System.exit(0);
	}

//    public static Scanner scannerFromMain() {
//        return new Scanner(System.in);
//    }
}
