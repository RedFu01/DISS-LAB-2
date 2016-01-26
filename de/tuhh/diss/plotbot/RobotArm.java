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
	private int speed = 920;
	private boolean calibrated = false;
	private int sign = -1;
	private int gearRatio = 1;
	private int startAngle = 4900;
	
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
		moveTo(degree, false);
	}
	
	public void move(int degree){
		move(degree, false);
	}
	
	public void moveTo(int degree, boolean imideatReturn){
		motor.setSpeed(speed);
		
		int degrees = this.getPosition() - degree;
		motor.rotate(sign*degrees*gearRatio, imideatReturn);
	}
	public void move(int degrees, boolean imideatReturn){
		motor.setSpeed(speed);
		motor.rotate(sign*degrees*gearRatio, imideatReturn);
		
	}
	
	public void move(double degrees){
		move(degrees,false);
	}
	
	public void move(double degrees, boolean imideatReturn){
		motor.setSpeed(speed);
		motor.rotate((int)(sign*degrees*gearRatio), imideatReturn);
		
	}
	
	public int getPosition(){
		return (motor.getTachoCount() /gearRatio);
	}
	
	/**
	 * calibrateArm
	 * @return position
	 * @throws InterruptedException
	 */
	public void calibrateArm(){
		int saveSpeed=motor.getSpeed();
		motor.setSpeed(2*saveSpeed);
		motor.backward();
		while (!touchSensorArm.isPressed()){
			
		}
		motor.stop();
		motor.rotate(startAngle);
		this.position = 0;
		init();
		calibrated=true;
		motor.setSpeed(saveSpeed);
	}
	
	public boolean getCalibrationStatus(){
		return calibrated;
	}
}
