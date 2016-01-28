package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;

public class GetMeasurements {
/**
 * start(): program for the measurements. starts all subroutines
 * the values acquired are used to calculate the boundaries of the robot 
 * to include in Plotbot-Class, set (boolean) Plotbot.calibration=true!!  
 * @param ARM_GEAR_RATIO
 * @return void
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
		RobotArm arm = new RobotArm(Plotbot.armSensor, Plotbot.armMotor,ARM_GEAR_RATIO);
		int angleArm=0;
		
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
	 * measurePen(): used to measure the angle the pen-motor has to rotate such as the pen is lifted/put down, 
	 * to minimize the time it needs.
	 * @param void
	 * @return anglePen: angle the pen-motor needs to rotate
	 */
	private static int measurePen(){
		Pen pen = new Pen(Plotbot.penSensor, Plotbot.penMotor);
		int anglePen=0;
		
		LCD.drawString("measure Pen?", 0, 0);
		if(Button.ENTER.isDown()){
			LCD.clear();
			LCD.drawString("measure Pen:", 0, 0);
			
			pen.calibratePen();
			pen.init();
			LCD.drawString("LEFT  -> up", 0, 1);
			LCD.drawString("RIGHT -> down", 0, 2);
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
	 * @param void
	 * @return values[2]: includes the value of the dark space and the light space 
	 */
	private static int[] getLightAndDark(){
		int[] values = new int[2];
		LCD.clear();
		LCD.drawString("place on white", 0, 0);
		Button.ENTER.waitForPressAndRelease();
		values[0]=getLightSensorValue();
		
		LCD.clear();
		LCD.drawString("place on black", 0, 0);
		Button.ENTER.waitForPressAndRelease();
		values[1]=getLightSensorValue();
		LCD.clear();
		return values;
	}
	
	/**
	 * getLightSensorValue(): reads the value provided by the light sensor
	 * @param void
	 * @return value of the light sensor
	 */
	private static int getLightSensorValue(){
		RobotWheels rWs = new RobotWheels(Plotbot.wheelsSensor, Plotbot.wheelMotor,1,10);
		return rWs.light.getLightValue();
	}
	
	/**
	 * showValues(): displays the values the GetMeasurements-class provides
	 * @param angleArm
	 * @param anglePen
	 * @param lightValue
	 * @param darkValue
	 * @return void
	 * 
	 */
	private static void showValues(int angleArm, int anglePen, int lightValue, int darkValue){
		LCD.clear();
		LCD.drawString("Motor-angle Arm:", 0, 0);
		LCD.drawInt(angleArm, 3, 0, 1);
		LCD.drawString("degrees", 5, 1);
		
		LCD.drawString("Motor-angle Pen:", 0, 2);
		LCD.drawInt(anglePen, 3, 0, 3);
		LCD.drawString("degrees", 5, 3);
		
		LCD.drawString("Value Light:", 0, 4);
		LCD.drawInt(lightValue, 3, 0, 5);
		
		LCD.drawString("Value Dark:", 0, 6);
		LCD.drawInt(darkValue, 3, 0, 7);
		
		Button.waitForAnyPress();
		LCD.clear();
	}
}
