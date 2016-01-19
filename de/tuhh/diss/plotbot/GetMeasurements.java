package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;

public class GetMeasurements {

	public static void main(String[] args) throws InterruptedException {
		
		int angleArm=0;
		int anglePen=0;
		RobotArm arm = new RobotArm();
		Pen pen = new Pen();
		
		//Measuring the arm!
		LCD.drawString("measure Arm?", 0, 0);
		if(Button.ENTER.isDown()){
			LCD.clear();
			LCD.drawString("measure Arm:", 0, 0);
			
			arm.calibrateArm();
			arm.init();
			while(!Button.ENTER.isDown() || !Button.ESCAPE.isDown()){
				while(Button.LEFT.isDown()){
					arm.move(1);
				}
				while(Button.LEFT.isDown()){
					arm.move(-1);
				}
			}
			if(Button.ENTER.isDown()){
				angleArm = Motor.A.getTachoCount();
				LCD.drawInt(Motor.A.getTachoCount(), 1, 0);
			}
		}
		
		LCD.drawString("measure Pen?", 0, 0);
		if(Button.ENTER.isDown()){
			LCD.clear();
			LCD.drawString("measure Pen:", 0, 0);
			
			pen.calibratePen();
			pen.init();
			while(!Button.ENTER.isDown() || !Button.ESCAPE.isDown()){
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
			}
		}
		LCD.drawString("show values?", 0, 0);
		if(Button.ENTER.isDown()){
			LCD.clear();
			LCD.drawString("Motor-angle Arm:", 0, 0);
			LCD.drawInt(angleArm, 1, 0, 3);
			LCD.drawString("degrees", 1, 5);
			
			LCD.drawString("Motor-angle Pen:", 3, 0);
			LCD.drawInt(anglePen, 4, 0, 3);
			LCD.drawString("degrees", 4, 5);
		}
		Button.waitForAnyPress();
		LCD.clear();
	}

}
