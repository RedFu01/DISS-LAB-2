/**
 * 
 */
package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.LCD;

/**
 * @author Bjoern
 *
 */
public class Interface {
	
	public static void startUp(){
		LCD.clear();
		LCD.drawString("**Hello Mate !**",0,4);
		Button.waitForAnyPress();
	}
	
	public static String selectShape(){
		String shape = "";
		LCD.clear();
		LCD.drawString("Select Shape!",1,0);
		LCD.drawString("Button to press:", 1, 1);
		LCD.drawString("Left: rectangle",1,3);
		LCD.drawString("Right: TUHH",1,4);
		Button.waitForAnyPress();
		if(Button.LEFT.isDown()){
			shape = "rectangle";
		}else if(Button.RIGHT.isDown()){
			shape = "TUHH";
		}
		showChoice(shape);
		LCD.clear();
		return shape;
	}
	/**
	 * selectSize()
	 * 
	 */
	public static int selectSize(){
		int size = 0;
		LCD.clear();
		LCD.drawString("Select Size!(mm)", 0, 0);
		LCD.drawString("Right -> +", 0, 1);
		LCD.drawString("Left  -> -", 0, 2);
		LCD.setAutoRefresh(true);
		LCD.drawString("Size = ", 0, 5);
		while (!Button.ENTER.isDown()){
			if(Button.RIGHT.isDown()){
				size+=1;
				showInt(size);
			}
			if(Button.LEFT.isDown()){
				size-=1;
				showInt(size);
			}
			if(Button.ESCAPE.isDown()){
				size = 0;
			}
		}
		LCD.drawString("Your Choice", 0, 6);
		LCD.drawInt(size, 0, 7, 3);
		LCD.drawString("mm", 4, 7);
		
		if(size > 160){
			LCD.clear();
			LCD.drawString(" error in size! ", 0, 3);
			LCD.drawString("...try again!...", 0, 4);
			LCD.drawString(" press a button ", 0, 5);
			Button.waitForAnyPress();
			selectSize();
		}
		return (size);
	}
	
	/**
	 * showChoice()
	 * display choice of shape
	 * @param void
	 * @return void 
	 */
	private static void showChoice(String shape){
		LCD.drawString("Your choice:", 0, 6);
		LCD.drawString(shape, 0, 7);
		Button.waitForAnyPress();
	}
	private static void showInt(int number){
		LCD.drawString("Your choice:", 0, 6);
		LCD.drawInt(number, 0, 7);
		Button.waitForAnyPress();
	}
}
