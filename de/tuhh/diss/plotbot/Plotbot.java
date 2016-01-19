package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.LCD;

public class Plotbot {
	public static void main(String[] args){
		Interface menu = new Interface();
		RobotArm arm = new RobotArm();
		Pen pen = new Pen();
		
		int size = 0;
		String shape = "";
		
		menu.startUp();
		while (size == 0){
			while (shape == ""){
				shape = menu.selectShape();
			}
			size = menu.selectSize();
		}
		
		if (arm.getCalibrationStatus() && pen.getCalibrationStatus()){
			
		
		/*
		 * PUT FUNCTION FOR DRAWING HERE!!!!!!
		 */
		
		}
	}
}
