package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.LCD;

public class Plotbot {
	public static void main(String[] args){
		
		int size = 0;
		String shape = "";
		
		Interface.startUp();
		while (size == 0){
			while (shape == ""){
				shape = Interface.selectShape();
			}
			size = Interface.selectSize();
		}
		
		
		
		
		
		
		// Some example code to check if the build process works
		LCD.drawString("Compiled successfully", 0, 0);
		LCD.drawString("Good Luck!", 0, 1);
		Button.ESCAPE.waitForPressAndRelease();
	}
}
