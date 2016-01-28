package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;

public class Plotbot {
	private final static int ARM_GEAR_RATIO = 84;
	private final static int WHEEL_GEAR_RATIO = 5;
	private final static int WHEEL_DIAMETER = 56;
	
	/* initialize Sensors */
	public final static TouchSensor penSensor = new TouchSensor(SensorPort.S2);
	public final static TouchSensor armSensor = new TouchSensor(SensorPort.S1);
	public final static LightSensor wheelsSensor = new LightSensor(SensorPort.S3);

	/* initialize Motors */
	public final static NXTRegulatedMotor armMotor = Motor.A;
	public final static NXTRegulatedMotor penMotor = Motor.B;
	public final static NXTRegulatedMotor wheelMotor = Motor.C;
	
	
	public final static RobotWheels robotWheels = new RobotWheels(wheelsSensor, wheelMotor, WHEEL_GEAR_RATIO, WHEEL_DIAMETER);
	public final static RobotArm robotArm = new RobotArm(armSensor, armMotor, ARM_GEAR_RATIO);
	public final static Pen pen = new Pen(penSensor, penMotor);
	public final static Interface menu = new Interface();
	
	public static void main(String[] args){
		boolean calibration = false;
	/*	get the measurements of the robot? set (boolean) calibration = true */
		if(calibration){
				GetMeasurements.start(ARM_GEAR_RATIO);
		}else{
	/* 	main task below!*/
			int size = 50;
			boolean sizeSelected = false;
			String shape = "";
			
			menu.startUp();
	//	user interaction: selection off shape and size of the picture to be drawn
			while (!sizeSelected){
				while (shape.equals("")){
					shape = menu.selectShape();
				}
				size = menu.selectSize();
				sizeSelected = true;
			}
	//	calibrate the motors/ positions of arm, pen and robot
			pen.calibratePen();
			robotArm.calibrateArm();
			robotWheels.calibrateYPos();
			
	// 	when calibration is complete, start the drawing process; else show error on LCD
			if (robotArm.getCalibrationStatus() && pen.getCalibrationStatus() && robotWheels.getCalibrationStatus()){
				LCD.drawString("calibrated!", 0, 0);
				LCD.drawString("press any button", 0, 1);
				Button.waitForAnyPress();
				LCD.clear();
				if (shape.equals("TUHH")){
					PlotTUHH plotTUHH = new PlotTUHH(size);
					plotTUHH.plot();
				}
				if (shape.equals("square")){
					Plotsquare plotSqr = new Plotsquare(size);
					plotSqr.plot();
				}
				done();
			}else{
				LCD.drawString("calibration:", 0, 0);
				LCD.drawString("failed!", 0, 1);
				LCD.drawString("start again", 0, 2);
			}
		}
	}
	
	/**
	 * done(): after the picture is finished, move the arm to startingposition and set the pen down
	 * @param void
	 * @return void
	 */
	public static void done(){
		robotArm.moveTo(0);
		pen.down();
	}
}

