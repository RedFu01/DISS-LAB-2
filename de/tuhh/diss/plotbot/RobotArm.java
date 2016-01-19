package de.tuhh.diss.plotbot;

import lejos.nxt.SensorPort;
import lejos.nxt.Motor;
import lejos.nxt.TouchSensor;

public class RobotArm {
	private int position =0 ;
	private int speed = 90;
	
	public void init(){
		//TODO: move the arm to the default position;
		Motor.A.resetTachoCount();
	}
	
	public void moveTo(int degree){
		Motor.A.setSpeed(speed);
		
		int degrees = this.position - degree;
		Motor.A.rotate(degrees, false);
		this.position = degree;
	}
	public void move(int degrees){
		Motor.A.setSpeed(speed);
		Motor.A.rotate(degrees, false);
		this.position += degrees; 
		
	}
	public int getPosition(){
		return this.position;
	}

}
