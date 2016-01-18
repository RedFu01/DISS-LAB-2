package de.tuhh.diss.plotbot;

import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;


public class Calibration {
	TouchSensor touchArm = new TouchSensor(SensorPort.S4);
	TouchSensor touchPen = new TouchSensor(SensorPort.S2);
	LightSensor light = new LightSensor(SensorPort.S3);
	private final static int lightValue = 35; //range of constant: 0<lightValue<100
		
	public void calibrate() throws InterruptedException{
		LCD.clear();
		LCD.drawString("Calibration:", 0, 0);
		calibrateArm();
		calibrateYPos();
		calibratePen();
		LCD.drawString("Done!", 4, 0);
		wait(500);
		LCD.clear();
	}
	
	
	private void calibrateArm() throws InterruptedException{
		
		while (!touchArm.isPressed()) {
			Motor.A.forward();
			}
		Motor.A.stop();
		Motor.A.resetTachoCount();
		Motor.A.rotate(-90,false); //arm pointing forward
		Motor.A.resetTachoCount();
		LCD.drawString("Arm calibrated", 1, 0);
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
	
	
	private void calibratePen() throws InterruptedException{
		
		while(!touchPen.isPressed()){
			Motor.B.forward();
		}
		Motor.B.stop();
		LCD.drawString("Pen calibrated", 3, 0);
	}
}
