package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;

public class GetMeasurements {

	public static void start(int ARM_GEAR_RATIO) throws InterruptedException {
		
		int angleArm = measureArm(ARM_GEAR_RATIO);
		int anglePen = measurePen();
		int[] lightValues= new int[2];
		
		lightValues=getLightAndDark();
		showValues(angleArm, anglePen, lightValues[0], lightValues[1]);
	}
	
	public static int measureArm(int ARM_GEAR_RATIO) throws InterruptedException{
		

		RobotArm arm = new RobotArm(ARM_GEAR_RATIO);
		int angleArm=0;
		
		//Measuring the arm!
		LCD.drawString("measure Arm?", 0, 0);
		if(Button.ENTER.isDown()){
			LCD.clear();
			LCD.drawString("measure Arm:", 0, 0);
			
			arm.calibrateArm();
			arm.init();
			while(!Button.ENTER.isDown() && !Button.ESCAPE.isDown()){
				while(Button.LEFT.isDown()){
					arm.move(1);
				}
				while(Button.RIGHT.isDown()){
					arm.move(-1);
				}
			}
			if(Button.ENTER.isDown()){
				angleArm = Motor.A.getTachoCount();
				LCD.drawInt(Motor.A.getTachoCount(), 1, 0);
			}
		}
		return angleArm;
	}
	private static int measurePen() throws InterruptedException{
		Pen pen = new Pen();
		int anglePen=0;
		
		LCD.drawString("measure Pen?", 0, 0);
		if(Button.ENTER.isDown()){
			LCD.clear();
			LCD.drawString("measure Pen:", 0, 0);
			
			pen.calibratePen();
			pen.init();
			while(!Button.ENTER.isDown() && !Button.ESCAPE.isDown()){
				LCD.drawString("LEFT  -> up", 1, 0);
				LCD.drawString("RIGHT -> down", 2, 0);
				while(Button.LEFT.isDown()){
					pen.move( 1);
				}
				while(Button.RIGHT.isDown()){
					pen.move(-1);
				}
			}
			if(Button.ENTER.isDown()){
				anglePen = Motor.B.getTachoCount();
				LCD.drawInt(Motor.B.getTachoCount(), 1, 0);
				Thread.sleep(200);
			}
		}
		return anglePen;
	}


	private static int[] getLightAndDark(){
		int[] values = new int[2];
		LCD.clear();
		LCD.drawString("place on white", 0, 0);
		Button.ENTER.waitForPressAndRelease();
		values[0]=getLightValues();
		
		LCD.clear();
		LCD.drawString("place on black", 0, 0);
		Button.ENTER.waitForPressAndRelease();
		values[1]=getLightValues();
		LCD.clear();
		return values;
	}
	
	private static int getLightValues(){
		RobotWheels rWs = new RobotWheels(1,10);
		return rWs.light.getLightValue();
	}
	
	private static void showValues(int angleArm, int anglePen, int lightValue, int darkValue){
		LCD.drawString("show values?", 0, 0);
		if(Button.ENTER.isDown()){
			LCD.clear();
			LCD.drawString("Motor-angle Arm:", 0, 0);
			LCD.drawInt(angleArm, 1, 0, 3);
			LCD.drawString("degrees", 1, 5);
			
			LCD.drawString("Motor-angle Pen:", 2, 0);
			LCD.drawInt(anglePen, 3, 0, 3);
			LCD.drawString("degrees", 3, 5);
			
			LCD.drawString("Value Light:", 4, 0);
			LCD.drawInt(lightValue, 5, 0, 3);

			LCD.drawString("Value Dark:", 6, 0);
			LCD.drawInt(darkValue, 7, 0, 3);
		}
		Button.waitForAnyPress();
		LCD.clear();
	}
}
