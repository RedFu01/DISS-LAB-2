package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;

public class GetMeasurements {
/**
 * start(): 'main'-program for the measurements. starts all subroutines
 * the values acquired are used to calculate the boundaries of the robot  
 * @param ARM_GEAR_RATIO
 */
	public static void start(int ARM_GEAR_RATIO){
		
		int angleArm = measureArm(ARM_GEAR_RATIO);
		int anglePen = measurePen();
		int[] lightValues = new int[2];
		
		lightValues=getLightAndDark();
		showValues(angleArm, anglePen, lightValues[0], lightValues[1]);
	}
	
	/**
	 * measureArm(): used for measuring the angle the arm can move, to calculate the max. size of the drawing.
	 * @param ARM_GEAR_RATIO
	 * @return angleArm: max. angle the arm can rotate
	 */
	private static int measureArm(int ARM_GEAR_RATIO){
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
		LCD.clear();
		return angleArm;
	}
	
	/**
	 * measurePen(): used to measure the angle the pen-motor has to rotate such as the pen is lifted/put down, to minimize the time it needs.
	 * @return anglePen: angle the pen-motor needs to rotate
	 */
	private static int measurePen(){
		Pen pen = new Pen();
		int anglePen=0;
		
		LCD.drawString("measure Pen?", 0, 0);
		if(Button.ENTER.isDown()){
			LCD.clear();
			LCD.drawString("measure Pen:", 0, 0);
			
			pen.calibratePen();
			pen.init();
			LCD.drawString("LEFT  -> up", 1, 0);
			LCD.drawString("RIGHT -> down", 2, 0);
			while(!Button.ENTER.isDown() && !Button.ESCAPE.isDown()){
				
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
			}
		}
		LCD.clear();
		return anglePen;
	}

/**
 * getLightAndDark(): puts the value for the dark and the light field in a vector  
 * @return values[2]: includes the value of the dark space and the light space 
 */
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
	
	/**
	 * getLightValue(): reads the value provided by the light sensor
	 * @return value of the light sensor
	 */
	private static int getLightValues(){
		RobotWheels rWs = new RobotWheels(1,10);
		return rWs.light.getLightValue();
	}
	
	/**
	 * showValues(): displays the values the GetMeasurements-class provides
	 * @param angleArm
	 * @param anglePen
	 * @param lightValue
	 * @param darkValue
	 * 
	 */
	private static void showValues(int angleArm, int anglePen, int lightValue, int darkValue){
		LCD.clear();
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
