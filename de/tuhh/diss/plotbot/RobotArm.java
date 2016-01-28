package de.tuhh.diss.plotbot;

import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.TouchSensor;

/**
 * class RobotArm: methods and Sensor- & Motor-initialization for the arm
 * @param TouchSensor sensor
 * @param NXTRegulatedMotor motor
 * @param int gearRatio
 * @return void
 */
public class RobotArm {
	private TouchSensor touchSensorArm = null;
	private NXTRegulatedMotor motor = null;
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
	
	/**
	 * init():set position and motor.tacho-count to zero/ reset position counts 
	 * @param void
	 * @return void
	 */
	public void init(){
		motor.resetTachoCount();
	}
	
	/**
	 * move(): rotate the ArmMotor with the desired speed and if desired, with immediate return
	 * @param int degrees
	 * @param boolean immediatReturn
	 * @return void
	 */
	public void move(int degrees, boolean immediateReturn){
		motor.setSpeed(speed);
		motor.rotate(sign*degrees*gearRatio, immediateReturn);
	}
	
	/**
	 * move(): move function with integer input and set immediateReturn
	 * @param int degree
	 * @return void
	 */
	public void move(int degree){
		move(degree, false);
	}
	
	/**
	 * move(): move function with double input and set immediateReturn
	 * @param double degree
	 * @return void
	 */
	public void move(double degrees){
		move(degrees,false);
	}
	
	/**
	 * move(): rotate the ArmMotor with the desired speed and if desired, with immediate return 
	 * @param double degrees
	 * @param boolean immediatReturn
	 * @return void
	 */
	public void move(double degrees, boolean immediatReturn){
		motor.setSpeed(speed);
		motor.rotate((int)(sign*degrees*gearRatio), immediatReturn);
	}
	
	/**
	 * moveTo(): move the motor to a certain position 
	 * @param int degree
	 * @param boolean immediateReturn
	 * @return void
	 */	
	public void moveTo(int degree, boolean immediateReturn){
		motor.setSpeed(speed);
		
		int degrees = this.getPosition() - degree;
		motor.rotate(sign*degrees*gearRatio, immediateReturn);
	}
	
	/**
	 * moveTo(): moveTo function with set immediateReturn
	 * @param degree
	 * @return void
	 */
	public void moveTo(int degree){
		moveTo(degree, false);
	}
	
	
	/**
	 * getPosition(): get the position of the arm in degree
	 * @param void
	 * @return position
	 */
	public int getPosition(){
		return (motor.getTachoCount() /gearRatio);
	}
	
	/**
	 * calibrateArm(): calibration of the arm, 
	 * 1. move to touchSensor
	 * 2. rotate to center position
	 * @param void
	 * @return void
	 */
	public void calibrateArm(){
		int saveSpeed=motor.getSpeed();
		motor.setSpeed(2*saveSpeed);
		motor.backward();
		while (!touchSensorArm.isPressed()){
			
		}
		motor.stop();
		motor.rotate(startAngle);
		init();
		calibrated=true;
		motor.setSpeed(saveSpeed);
	}
	
	/**
	 * getCalibrationStatus(): return calibration status
	 * @param void
	 * @return calibrated
	 */
	public boolean getCalibrationStatus(){
		return calibrated;
	}
}
