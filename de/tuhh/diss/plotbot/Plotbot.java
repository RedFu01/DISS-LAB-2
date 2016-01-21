package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.LCD;

public class Plotbot {
	private final static int ARM_GEAR_RATIO = 84;
	private final static int WHEEL_GEAR_RATIO = 5;
	private final static int WHEEL_DIAMETER = 56;
	private final static int ARM_RADIUS = 80;
	private final static int LIGHT_SENSOR_RADIUS = 105;
	
	public static void main(String[] args){
		boolean calibration = true;
		
		if(calibration){
			try {
				GetMeasurements.start(ARM_GEAR_RATIO);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
		Interface menu = new Interface();
		RobotArm arm = new RobotArm(ARM_GEAR_RATIO);
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
}
