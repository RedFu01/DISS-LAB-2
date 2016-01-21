package de.tuhh.diss.plotbot;

import lejos.nxt.SensorPort;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.TouchSensor;

public class RobotArm {
	TouchSensor touchSensorArm = new TouchSensor(SensorPort.S4);
	private int position =0 ;
	private int speed = 90;
	private boolean calibrated = false;
	private int sign = 1;
	private int gearRatio = 1;
	
	public RobotArm(int gearRatio){
		this.gearRatio = gearRatio;
	}
	
	public void init(){
		//TODO: move the arm to the default position;
		Motor.A.resetTachoCount();
	}
	
	public void moveTo(int degree){
		Motor.A.setSpeed(speed);
		
		int degrees = this.position - degree;
		Motor.A.rotate(sign*degrees*gearRatio, false);
		this.position = degree;
	}
	public void move(int degrees){
		Motor.A.setSpeed(speed);
		Motor.A.rotate(sign*degrees*gearRatio, false);
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
	public void calibrateArm() throws InterruptedException{
		
		while (!touchSensorArm.isPressed()) {
			move(1);
		}
		this.position = 0;
		init();
		
		//moveTo(90);
		
		calibrated=true;
		LCD.drawString("Arm calibrated", 0, 0);
		wait(1000);
		LCD.clear();
		
	}
	public boolean getCalibrationStatus(){
		return calibrated;
	}
}
