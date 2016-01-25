package de.tuhh.diss.plotbot;

import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;

public class RobotWheels {
	private final static int VALUE_FOR_LIGHT_SENSOR = 35; //range of constant: 0<lightValue<100
	public LightSensor light = null;
	private NXTRegulatedMotor motor = null;
	
	private int distance =0;
	private double wheelCircumfence = 28*2*Math.PI;
	private int gearRatio = 5;
	private boolean calibrated = false;
	
	public void setSpeed(int speed){
		this.motor.setSpeed(speed);
	}
	
	public RobotWheels(LightSensor sensor,NXTRegulatedMotor motor, int gearRatio, int wheelRadius ){
		this.gearRatio = gearRatio;
		this.wheelCircumfence =  2* Math.PI * wheelRadius;
		this.light = sensor;
		this.motor = motor;
	}
	
	public void drive(int distance){
		int deg = (int)(gearRatio*distance*360/this.wheelCircumfence);
		Motor.C.rotate(deg);
		this.distance += distance;
	}
	public void driveToDistance(int distance){
		int delta = this.distance - distance;
		int deg = (int)(gearRatio*delta*360/this.wheelCircumfence);
		Motor.C.rotate(deg);
		this.distance = distance;
	}

	public void calibrateYPos(){
		light.setLow(145);
		light.setHigh(890);
		if(light.getLightValue()<VALUE_FOR_LIGHT_SENSOR){
			Motor.C.forward();
			while(light.getLightValue() < VALUE_FOR_LIGHT_SENSOR){
			}
			Motor.C.stop();
			drive(10);
			calibrateYPos();
		}else{
			Motor.C.backward();
			while(light.getLightValue() >= VALUE_FOR_LIGHT_SENSOR){
			}
			Motor.C.stop();
		}
		drive(25);
		resetDistance();
		calibrated = true;
	}
	private void resetDistance(){
		distance = 0;
	}
	public boolean getCalibrationStatus(){
		return calibrated;
	}
	
}
