package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.LCD;

public class Plotline {
	
	public static final int speedMotorC = 300; // Default speed

	public void lineInY(int lineLengthY) {

		Plotbot.wheelMotor.setSpeed(speedMotorC);
		Plotbot.pen.down();
		Plotbot.robotWheels.drive(lineLengthY);
		Plotbot.pen.up();

	}

/*	public void lineInX(int lineLengthX) {
		double angledb;
		int omegaC;
		int distanceY;

		angledb = Transform.sweepAngle(lineLengthX); // angle in 'double' type
		int angle = (int) Math.round(angledb); // angle in 'int' type
		
		distanceY = (int) Math.round(Transform.offsetYdueToX(Math.abs(angledb)));
		
		omegaC = (int) Math.round(Transform.omegaMotorC(angledb, Plotbot.armMotor.getSpeed()));

		// Motors' speed setup, synchronized C depends on A
		Plotbot.wheelMotor.setSpeed(omegaC);
		
		// Right to left drawing or left to right depends on +/- length of X
		// +X -> +angle -> right to left, vice versa
		Plotbot.robotArm.moveTo(0);
		Plotbot.robotArm.moveTo(angle);
		Plotbot.pen.down();
		
		// 1st half of the line in x-dir
		Plotbot.robotArm.move(-angle,true);
		Plotbot.robotWheels.drive(-distanceY);
		
		// 2nd half of the line in x-dir
		Plotbot.robotArm.move(-angle, true);
		Plotbot.robotWheels.drive(distanceY);
					
		Plotbot.pen.up();
	}*/

/* ------------------------------------------------ ALTERNATIVE 2 ------------------------------------------------ */	

/*	public void lineInX(int lineLengthX) {
		double angledb;
		int omegaC;
		int distanceY = 1;
		int shift = 2;

		angledb = Transform.sweepAngle(lineLengthX); // angle in 'double' type
		int angle = (int) Math.round(angledb); // angle in 'int' type
		
		omegaC = (int) Math.round(Transform.omegaMotorC(angledb, Plotbot.armMotor.getSpeed()));
		
		int countAngle;

		// Motors' speed setup, synchronized C depends on A
		Plotbot.robotWheels.setSpeed(omegaC);
		
		// Right to left drawing or left to right depends on +/- length of X
		
		Plotbot.robotArm.moveTo(0); // Straight arm position
		Plotbot.robotArm.moveTo(angle);
		Plotbot.pen.down();
		
		while(!Plotbot.armSensor.isPressed()) {
			// Right to Left drawing
			if (angle > 0) {
				// Start drawing from right to middle
				for (countAngle = 0; countAngle > -angle; countAngle = countAngle - shift) {
					Plotbot.robotArm.move(-shift);
					Plotbot.robotWheels.drive(-distanceY);
				}
	
				// Start drawing from middle to left
				for (countAngle = 0; countAngle > -angle; countAngle = countAngle - shift) {
					Plotbot.robotArm.move(-shift);
					Plotbot.robotWheels.drive(distanceY);
				}
				
				Plotbot.pen.up();
			}
			
			// Left to Right drawing
			else {
				// Start drawing from left to middle
				for (countAngle = 0; countAngle > -angle; countAngle = countAngle + shift) {
					Plotbot.robotArm.move(shift);
					Plotbot.robotWheels.drive(-distanceY);
				}
	
				// Start drawing from middle to right
				for (countAngle = 0; countAngle < angle; countAngle = countAngle + shift) {
					Plotbot.robotArm.move(shift);
					Plotbot.robotWheels.drive(distanceY);
				}
				
				Plotbot.pen.up();
			}
		}
		Plotbot.armMotor.stop();
	}*/
	
/* ------------------------------------------------ ALTERNATIVE 3 ------------------------------------------------ */	

	public void lineInX(int lineLengthX) {
		double angledb = 0;
		float omegaC = 0;
		int tetha = 0;
		
		int position1 = 0;
		int position2 = 0;
		
		angledb = Transform.sweepAngle(lineLengthX); // angle in 'double' type
		int angle = (int) Math.round(angledb); // angle in 'int' type

		
		// Right to left drawing or left to right depends on +/- length of X
		
		Plotbot.robotArm.moveTo(0); // Straight arm position
		Plotbot.robotArm.moveTo(angle);
		Plotbot.pen.down();
		Plotbot.robotArm.setSpeed(100);
		double distance = 0;
		
		int position = 0;
		
		position = Plotbot.robotArm.getPosition();
		Plotbot.robotArm.moveTo(-position, true);
		//while(!Plotbot.armSensor.isPressed()) {
		position1 = Plotbot.robotArm.getPosition();
		while (Math.abs(Plotbot.robotArm.getPosition() + position)>1) {
			if (distance != 0){
				position1 = position2;
			}
			

			
			Plotbot.robotWheels.setSpeed(960);
			position2 = Plotbot.robotArm.getPosition();
			distance = Transform.accmomodateArc(position1, position2);
			
			LCD.drawString("-" + distance, 0, 1);
			if (position1 !=0 || position2 != 0){
			Plotbot.robotWheels.drive(-distance);
			}			
			Plotbot.robotArm.resetSpeed();
		}
			//Plotbot.robotArm.moveTo(0, true);
			
			
			//Plotbot.robotWheels.drive(-distance);
			
			
			
			
			//Plotbot.robotWheels.drive(distance);
			
			/*tetha = angle;
			// right to left plot
			if (angle > 0) {
				while (tetha >= -angle && tetha <= angle) {
					// omegaC = (float) Math.round(Transform.altOmegaMotorC(Math.abs(tetha), Plotbot.armMotor.getSpeed()));
					omegaC = 250;
					Plotbot.wheelMotor.setSpeed(omegaC);
					if (tetha >= 0) {
						Plotbot.wheelMotor.backward();	
					}
					
					else {
						Plotbot.wheelMotor.forward();
					}
					
					tetha = tetha - Plotbot.robotArm.getPosition();
					
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Plotbot.wheelMotor.stop();
				}
				Plotbot.wheelMotor.stop();
				Plotbot.pen.up();
			}
			
			// left to right plot
			else {
				while (tetha <= -angle && tetha >= angle) {
					// omegaC = (float) Math.round(Transform.altOmegaMotorC(Math.abs(tetha), Plotbot.armMotor.getSpeed()));
					omegaC = 250;
					Plotbot.wheelMotor.setSpeed(omegaC);
					if (tetha <= 0) {
						Plotbot.wheelMotor.backward();	
					}
					
					else {
						Plotbot.wheelMotor.forward();
					}
					tetha = tetha + Plotbot.robotArm.getPosition();
					
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Plotbot.wheelMotor.stop();
				}
				Plotbot.wheelMotor.stop();
				Plotbot.pen.up();
			}*/
		//}
		//Plotbot.wheelMotor.stop();
		Plotbot.pen.up();
		
		
	}
}
