package de.tuhh.diss.plotbot;

import lejos.nxt.SensorPort;
import lejos.nxt.Motor;
import lejos.nxt.TouchSensor;

public class RobotArm {
	private int position =0 ;
	private int speed = 90;
	
	public void init(){
		//TODO: move the arm to the default position;
		Motor.B.resetTachoCount();
	}
	
	public void moveTo(int degree){
		Motor.B.setSpeed(speed);
		
		int degrees = this.position - degree;
		Motor.B.rotate(degrees, false);
		this.position = degree;
	}
	public void move(int degrees){
		Motor.B.setSpeed(speed);
		Motor.B.rotate(degrees, false);
		this.position += degrees; 
		
	}
	public int getPosition(){
		return this.position;
	}

}
