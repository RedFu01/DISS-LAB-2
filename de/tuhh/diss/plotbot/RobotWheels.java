package de.tuhh.diss.plotbot;

import lejos.nxt.Motor;

public class RobotWheels {
	private int distance =0;
	private final int wheelRadius = 30;
	
	void drive(int distance){
		int deg = distance/this.wheelRadius;
		Motor.C.rotate(deg);
		this.distance += distance;
	}
	void driveToDistance(int distance){
		int delta = this.distance - distance;
		int deg = distance/this.wheelRadius;
		Motor.C.rotate(deg);
		this.distance = distance;
	}

}
