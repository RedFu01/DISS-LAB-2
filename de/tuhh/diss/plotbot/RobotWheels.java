package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;

public class RobotWheels {
	private final static int VALUE_FOR_LIGHT_SENSOR = 50; //range of constant: 0<lightValue<100
	public LightSensor light = null;
	private NXTRegulatedMotor motor = null;
	
	private int distance =0;
	private double wheelCircumfence = 56*Math.PI;
	private int gearRatio = 5;
	private boolean calibrated = false;
	
	public void setSpeed(int speed){
		this.motor.setSpeed(speed);
	}
	
	public RobotWheels(LightSensor sensor,NXTRegulatedMotor motor, int gearRatio, int wheeldiameter ){
		this.gearRatio = gearRatio;
		this.wheelCircumfence =  Math.PI * wheeldiameter;
		this.light = sensor;
		this.motor = motor;
	}
	
	public void drive(int distance){
		int deg = (int)((gearRatio*distance*360)/this.wheelCircumfence);
		motor.rotate(deg);
		this.distance += distance;
	}
	public void driveToDistance(int distance){
		int delta = this.distance - distance;
		drive(delta);
		
	}

	public void calibrateYPos(){
		light.setLow(145);
		light.setHigh(890);
		if(light.getLightValue()<VALUE_FOR_LIGHT_SENSOR){
			motor.forward();
			while(light.getLightValue() < VALUE_FOR_LIGHT_SENSOR){
			}
			motor.stop();
			drive(10);
			calibrateYPos();
		}else{
			motor.backward();
			while(light.getLightValue() >= VALUE_FOR_LIGHT_SENSOR){
			}
			motor.stop();
		}
		//drive(25);
		resetDistance();
		calibrated = true;
	}
	private void resetDistance(){
		distance = 0;
	}
	public boolean getCalibrationStatus(){
		return calibrated;
	}
	
	
	/**/
	
	
}
