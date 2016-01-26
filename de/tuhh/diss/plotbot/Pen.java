package de.tuhh.diss.plotbot;

import lejos.nxt.SensorPort;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.TouchSensor;


public class Pen {
	private TouchSensor penSensor= null;
	private NXTRegulatedMotor motor = null;
	private int HEIGHT = 0;
	private int speed = 660;
	private boolean isDown = false;
	private boolean calibrated = false;
	private int position =0 ;
	private int sign = 1;
	
	public Pen(TouchSensor penSensor, NXTRegulatedMotor penMotor){
		this.penSensor = penSensor;
		this.motor = penMotor;
	}

	public void init(){
		motor.resetTachoCount();
		this.position = 0;
	}
	
	public void move(int degrees){
		motor.setSpeed(speed);
		motor.rotate(sign*degrees, false);
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
		motor.forward();
		while(!penSensor.isPressed()){
			
		}
		motor.stop();
		isDown = false;
	}
	
	public boolean isUp(){
		return isDown;
	}
	
	public void calibratePen(){	
		up();
		motor.resetTachoCount();
		motor.backward();
		while(penSensor.isPressed()){
			
		}
		while(!penSensor.isPressed()){
			
		}
		motor.stop();
		HEIGHT = motor.getTachoCount()/2;

		motor.forward();
		while(penSensor.isPressed()){
			
		}
		up();
		calibrated = true;
		LCD.clear();
	}
	
	private void stop(){
		motor.stop();
	}
	
	public boolean getCalibrationStatus(){
		return calibrated;
	}

}
