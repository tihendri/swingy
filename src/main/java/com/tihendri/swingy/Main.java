package com.tihendri.swingy;

import com.tihendri.swingy.controller.WriteToFile;
import com.tihendri.swingy.view.console.ConsoleView;
import com.tihendri.swingy.view.gui.GuiDisplay;

public class Main {

	public static boolean guiOrConsole;

    public static void main(String[] args) {

		try {
			WriteToFile.createFile();
			if (args.length != 1 || (!args[0].equals("console") && !args[0].equals("gui"))) {
				System.out.println("Usage: java -jar [path] console | gui");
				System.exit(0);
			} if (args[0].equalsIgnoreCase("console")) {
				guiOrConsole = false;
				ConsoleView.start();
			} else {
				guiOrConsole = true;
				GuiDisplay guiDisplay = new GuiDisplay();
				guiDisplay.start();
			}
		} catch (Exception e) {
			System.exit(1);
		} finally {
			WriteToFile.close();
		}
	}
}
