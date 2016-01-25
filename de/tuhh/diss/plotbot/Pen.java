package de.tuhh.diss.plotbot;

import lejos.nxt.SensorPort;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.TouchSensor;


public class Pen {
	private TouchSensor penSensor= null;
	private NXTRegulatedMotor penMotor = null;
	private final static int HEIGHT = 0;
	private int speed = 1320;
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
		while(!penSensor.isPressed()){
			move(sign*1);
		}
		isDown = false;
	}
	
	public boolean isUp(){
		return isDown;
	}
	
	public void calibratePen(){	
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
