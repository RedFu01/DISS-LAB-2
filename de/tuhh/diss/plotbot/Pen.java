package de.tuhh.diss.plotbot;

import lejos.nxt.SensorPort;
import lejos.nxt.Motor;
import lejos.nxt.TouchSensor;


public class Pen {
	private TouchSensor Sensor = new TouchSensor(SensorPort.S2);
	private boolean isDown = false;

	public void down(){
		Motor.B.forward();
		while(Sensor.isPressed()){
		
		}
		Motor.B.stop();
		isDown = true;
	}
	public void up(){
		Motor.B.backward();
		while(!Sensor.isPressed()){
			
		}
		Motor.B.stop();
		isDown = false;
	}
	
	public boolean isUp(){
		return isDown;
	}

}
