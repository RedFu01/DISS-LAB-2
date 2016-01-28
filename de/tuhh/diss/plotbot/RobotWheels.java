package de.tuhh.diss.plotbot;

import lejos.nxt.LightSensor;
import lejos.nxt.NXTRegulatedMotor;

/**
 * class RobotWheels: methods and Sensor- & Motor-initialization for the y-movement
 * @param LightSensor sensor
 * @param NXTRegulatedMotor motor
 * @param int gearRatio
 * @param int wheeldiameter
 * @return void
 *
 */
public class RobotWheels {
	private final static int VALUE_FOR_LIGHT_SENSOR = 50; //range of constant: 0<lightValue<100
	public LightSensor light = null;
	private NXTRegulatedMotor motor = null;
	private int distance =0;
	private double wheelCircumfence = 56*Math.PI;
	private int gearRatio = 5;
	private boolean calibrated = false;
	
	public RobotWheels(LightSensor sensor,NXTRegulatedMotor motor, int gearRatio, int wheeldiameter ){
		this.gearRatio = gearRatio;
		this.wheelCircumfence =  Math.PI * wheeldiameter;
		this.light = sensor;
		this.motor = motor;
	}
	
	/**
	 * drive(): move the robot a certain distance, with possible immediate return 
	 * @param int distance
	 * @param boolean immediateReturn
	 * @return void
	 */
	public void drive(int distance, boolean immediateReturn){
		motor.stop();
		int deg = (int)((gearRatio*distance*360)/this.wheelCircumfence);
		motor.rotate(deg, immediateReturn);
		this.distance += distance;
	}
	
	/**
	 * drive(): drive() with set immediateReturn
	 * @param int distance
	 * @return void
	 */
	public void drive(int distance){
		drive(distance, false);
	}
	
	/**
	 * drive(): move the robot a certain distance, with possible immediate return 
	 * @param double distance
	 * @param boolean immediateReturn
	 * @return void
	 */	
	public void drive(double distance, boolean immediateReturn){
		motor.stop();
		int deg = (int)((gearRatio*distance*360)/this.wheelCircumfence);
		motor.rotate(deg, immediateReturn);
		this.distance += distance;
	}
	
	/**
	 * drive(): drive() with set immediateReturn
	 * @param double distance
	 * @return void
	 */
	public void drive(double distance){
		drive(distance, false);
	}
	
	/**
	 * driveToDistance(): move the robot to a certain position, with possible immediate return 
	 * @param int distance
	 * @param boolean immediateReturn
	 * @return void
	 */
	public void driveToDistance(int distance, boolean immediateReturn){
		int delta = this.distance - distance;
		drive(delta, immediateReturn);
	}
	
	/**
	 * driveToDistance(): driveToDistance() with set immediateReturn
	 * @param int distance
	 * @return void
	 */
	public void driveToDistance(int distance){
		driveToDistance(distance, false);
	}

	/**
	 * calibratePos(): calibrate the y position of the robot. afterwards the pen will be on the top edge of the black bar.
	 * @param void
	 * @return void
	 */
	
	public void calibrateYPos(){
		light.setLow(145);
		light.setHigh(890);
		if(light.getLightValue()<VALUE_FOR_LIGHT_SENSOR){
			motor.forward();
			while(light.getLightValue() < VALUE_FOR_LIGHT_SENSOR){
			}
			motor.stop();
			drive(50);
			
			motor.backward();
			while(light.getLightValue() >= VALUE_FOR_LIGHT_SENSOR){
			}
			motor.stop();
		}
		else{
			motor.backward();
			while(light.getLightValue() >= VALUE_FOR_LIGHT_SENSOR){
			}
			motor.stop();
		}
		drive(25);
		resetDistance();
		calibrated = true;
	}
	
	/**
	 * resetDistance(): set the distance counter to 0
	 * @param void
	 * @return void
	 */
	private void resetDistance(){
		distance = 0;
	}
	
	/**
	 * setSpeed(): set the speed of the wheel motor to a certain value
	 * @param speed
	 * @return void
	 */
	public void setSpeed(int speed){
		this.motor.setSpeed(speed);
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
