package de.tuhh.diss.plotbot;

import lejos.nxt.SensorPort;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.TouchSensor;


public class Pen {
	private TouchSensor penSensor= null;
	private NXTRegulatedMotor penMotor = null;
	private int HEIGHT = -500;
	private int speed = 960;
	private boolean isDown = false;
	private boolean calibrated = false;
	private int position =0 ;
	private int sign = 1;
	
	public Pen(TouchSensor penSensor, NXTRegulatedMotor penMotor){
		this.penSensor = penSensor;
		this.penMotor = penMotor;
	}

	public void init(){
		Motor.B.resetTachoCount();
		this.position = 0;
	}
	
	public void move(int degrees){
		Motor.B.setSpeed(speed);
		Motor.B.rotate(sign*degrees, false);
		stop();
		this.position += degrees; 
		
	}
	public void down(){
		if(!isDown){
			move(HEIGHT);
			isDown = true;
		}
	}
	public void up(){
		penMotor.forward();
		while(!penSensor.isPressed()){
			
		}
		penMotor.stop();
		isDown = false;
	}
	
	public boolean isUp(){
		return isDown;
	}
	
	public void calibratePen(){	
		up();
		penMotor.resetTachoCount();
		penMotor.rotate(-20);
		penMotor.backward();
		while(!penSensor.isPressed()){
			
		}
		stop();
		HEIGHT = penMotor.getTachoCount()/2;
		up();
		calibrated = true;
		LCD.clear();
	}
	
	private void stop(){
		Motor.B.stop();
	}
	
	public boolean getCalibrationStatus(){
		return calibrated;
	}

}
