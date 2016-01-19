package de.tuhh.diss.plotbot;

import lejos.nxt.Motor;

public class RobotWheels {
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

}
