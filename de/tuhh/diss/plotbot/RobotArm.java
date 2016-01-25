package de.tuhh.diss.plotbot;

import lejos.nxt.SensorPort;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.TouchSensor;

public class RobotArm {
	private TouchSensor touchSensorArm = null;
	private NXTRegulatedMotor motor = null;
	private int position =0 ;
	private int speed = 720;
	private boolean calibrated = false;
	private int sign = -1;
	private int gearRatio = 1;
	
	public RobotArm(TouchSensor sensor, NXTRegulatedMotor motor, int gearRatio){
		this.gearRatio = gearRatio;
		this.touchSensorArm = sensor;
		this.motor = motor;
	}
	
	public void init(){
		//TODO: move the arm to the default position;
		motor.resetTachoCount();
	}
	
	public void moveTo(int degree){
		motor.setSpeed(speed);
		
		int degrees = this.position - degree;
		motor.rotate(sign*degrees*gearRatio, false);
		this.position = degree;
	}
	public void move(int degrees){
		motor.setSpeed(speed);
		motor.rotate(sign*degrees*gearRatio, false);
		this.position += degrees; 
		
	}
	public int getPosition(){
		return this.position;
	}
	
	/**
	 * calibrateArm
	 * @return position
	 * @throws InterruptedException
	 */
	public void calibrateArm(){
		
		while (!touchSensorArm.isPressed()) {
			move(1);
		}
		
		this.position = 0;
		init();
		
		//moveTo(90);
		
		calibrated=true;
		//LCD.drawString("Arm calibrated", 0, 0);
		
		//LCD.clear();
		
	}
	public boolean getCalibrationStatus(){
		return calibrated;
	}
}
