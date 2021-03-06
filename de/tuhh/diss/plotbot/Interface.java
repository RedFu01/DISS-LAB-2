package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.LCD;

public class Interface {
	/**
	 * startUp(): greetings mask. shows the program started
	 * @param void
	 * @return void
	 */
	public void startUp(){
		LCD.clear();
		LCD.drawString("**Hello Mate !**",0,4);
		Button.waitForAnyPress();
	}
	
	/**
	 * selectShape(): user interaction to select the picture/shape that is to be drawn
	 * @param void
	 * @return shape
	 */
	public String selectShape(){
		String shape = "";
		LCD.clear();
		LCD.drawString("Select Shape!",0,0);
		LCD.drawString("Button to press:", 0, 1);
		LCD.drawString("Left: square",0,3);
		LCD.drawString("Right: TUHH",0,4);
		Button.waitForAnyPress();
		if(Button.LEFT.isDown()){
			shape = "square";
		}
		if(Button.RIGHT.isDown()){
			shape = "TUHH";
		}
		showChoice(shape);
		LCD.clear();
		return shape;
	}
	
	/**
	 * selectSize(): user interface to select the size of the picture/shape to be drawn	 
	 * @param void
	 * @return size
	 */
	public int selectSize(){
		int size = 65;
		LCD.clear();
		LCD.drawString("Select Size!(mm)", 0, 0);
		LCD.drawString("Right -> +", 0, 1);
		LCD.drawString("Left  -> -", 0, 2);
		LCD.setAutoRefresh(true);
		LCD.drawString("Size = ", 0, 5);
		while (!Button.ENTER.isDown()){
			while(Button.RIGHT.isDown()){
				size+=5;
				showChoice(size);
			}
			while(Button.LEFT.isDown()){
				size-=5;
				showChoice(size);
			}
			if(Button.ESCAPE.isDown()){
				size = 0;
			}
		}
		LCD.drawString("Your Choice", 0, 6);
		LCD.drawInt(size, 0, 7, 3);
		LCD.drawString("mm", 4, 7);
		
		if(size > 100 || size < 0){
			LCD.clear();
			LCD.drawString(" error in size! ", 0, 3);
			LCD.drawString("...try again!...", 0, 4);
			LCD.drawString(" press a button ", 0, 5);
			Button.waitForAnyPress();
			selectSize();
		}
		LCD.clear();
		return (size);
	}
	
	/**
	 * showChoice()
	 * display choice of shape
	 * @param void
	 * @return void 
	 */
	private void showChoice(String shape){
		LCD.clear();
		LCD.drawString("Your choice:", 0, 6);
		LCD.drawString(shape, 0, 7);
		LCD.drawString("press button", 0, 8);
		Button.waitForAnyPress();
		LCD.clear();
		
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * showInt(): display a parameter number with some extra text.
	 * @param int number
	 * @return void 
	 */
	private void showChoice(int number){
		LCD.clear();
		LCD.drawString("Your choice:", 0, 6);
		LCD.drawInt(number, 0, 7, 3);
		LCD.drawString("press button", 0, 8);
		Button.waitForAnyPress();
		LCD.clear();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
