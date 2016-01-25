package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;

public class Plotbot {
	private final static int ARM_GEAR_RATIO = 84;
	private final static int WHEEL_GEAR_RATIO = 5;
	private final static int WHEEL_DIAMETER = 56;
	private final static int ARM_RADIUS = 80;
	private final static int LIGHT_SENSOR_RADIUS = 105;
	
	public static void main(String[] args){
		boolean calibration = true;
		
		if(calibration){
				GetMeasurements.start(ARM_GEAR_RATIO);
		}else{
		Interface menu = new Interface();
		RobotArm arm = new RobotArm(ARM_GEAR_RATIO);
		Pen pen = new Pen();
		RobotWheels rw = new RobotWheels(ARM_GEAR_RATIO, WHEEL_DIAMETER);
		
		int size = 0;
		String shape = "";
		
		menu.startUp();
		while (size == 0){
			while (shape == ""){
				shape = menu.selectShape();
			}
			size = menu.selectSize();
		}
			pen.calibratePen();
			arm.calibrateArm();
			rw.calibrateYPos();
			
		if (arm.getCalibrationStatus() && pen.getCalibrationStatus() && rw.getCalibrationStatus()){
			if (shape == "TUHH"){
				PlotTUHH plotTUHH = new PlotTUHH(size);
				plotTUHH.plot();
			}
			if (shape == "square"){
				Plotsquare plotSqr = new Plotsquare(size);
				plotSqr.plot();
			}
		}else{
			LCD.drawString("calibration:", 0, 0);
			LCD.drawString("failed!", 1, 0);
			LCD.drawString("start again", 5, 0);
		}

		
		}
	}
}

