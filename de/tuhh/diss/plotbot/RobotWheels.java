package de.tuhh.diss.plotbot;

import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;

public class RobotWheels {
	private final static int lightValue = 35; //range of constant: 0<lightValue<100
	LightSensor light = new LightSensor(SensorPort.S3);
	
	private int distance =0;
	private double wheelCircumfence = 28*2*Math.PI;
	private int gearRatio = 5;
	
	public RobotWheels(int gearRatio, int wheelRadius ){
		this.gearRatio = gearRatio;
		this.wheelCircumfence =  2* Math.PI * wheelRadius;
	}
	
	void drive(int distance){
		int deg = (int)(gearRatio*distance/this.wheelCircumfence);
		Motor.C.rotate(deg);
		this.distance += distance;
	}
	void driveToDistance(int distance){
		int delta = this.distance - distance;
		int deg = (int)(gearRatio*delta/this.wheelCircumfence);
		Motor.C.rotate(deg);
		this.distance = distance;
	}

	private void calibrateYPos() throws InterruptedException{
		
		light.setLow(145);
		light.setHigh(890);
		if(light.getLightValue()<lightValue){
			while(light.getLightValue() < lightValue){
				Motor.C.forward();
			}
			Motor.C.stop();
			calibrateYPos();
		}else{
			while(light.getLightValue() >= lightValue){
				Motor.C.backward();
			}
			Motor.C.stop();
		}
		LCD.drawString("Pos calibrated", 2, 0);
	}
	
}
