package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.LCD;

public class Plotbot {
	public static void main(String[] args){
		Interface menu = new Interface();
		RobotArm arm = new RobotArm(1);
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
		try {
			pen.calibratePen();
			arm.calibrateArm();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (arm.getCalibrationStatus() && pen.getCalibrationStatus()){
			
		Plotsquare plotter = new Plotsquare(size);
		plotter.plot();

		
		}
	}
}
