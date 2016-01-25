package de.tuhh.diss.plotbot;

import lejos.nxt.SensorPort;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.TouchSensor;


public class Pen {
	TouchSensor touchPen = new TouchSensor(SensorPort.S2);
	private final static int HEIGHT = 0;
	private int speed = 960;
	private boolean isDown = false;
	private boolean calibrated = false;
	private int position =0 ;
	private int sign = 1;

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
		while(!touchPen.isPressed()){
			move(sign*-1);
		}
		isDown = false;
	}
	
	public boolean isUp(){
		return isDown;
	}
	
	public void calibratePen(){	
		up();
		//LCD.drawString("Pen calibrated", 3, 0);
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
