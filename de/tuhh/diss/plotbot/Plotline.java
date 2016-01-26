package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.LCD;

public class Plotline {
	
	public static final int speedMotorC = 450; // Default speed

	public void lineInY(int lineLengthY) {

		Plotbot.wheelMotor.setSpeed(speedMotorC);
		Plotbot.pen.down();
		Plotbot.robotWheels.driveToDistance(lineLengthY);
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
	}*/
	
/* ------------------------------------------------ ALTERNATIVE 3 ------------------------------------------------ */	

	public void lineInX(int lineLengthX) {
		double angledb;
		float omegaC;
		int tetha;
		
		angledb = Transform.sweepAngle(lineLengthX); // angle in 'double' type
		int angle = (int) Math.round(angledb); // angle in 'int' type

		
		// Right to left drawing or left to right depends on +/- length of X
		
		Plotbot.robotArm.moveTo(0); // Straight arm position
		Plotbot.robotArm.moveTo(angle);
		Plotbot.pen.down();
		
		Plotbot.robotArm.move(-2 * angle, true);
		
		tetha = angle;
		// right to left plot
		if (angle > 0) {
			while (tetha >= -angle && tetha <= angle) {
				omegaC = (float) Math.round(Transform.altOmegaMotorC(Math.abs(tetha), Plotbot.armMotor.getSpeed()));
				Plotbot.wheelMotor.setSpeed(omegaC);
				if (tetha >= 0) {
					Plotbot.wheelMotor.backward();	
				}
				
				else {
					Plotbot.wheelMotor.forward();
				}
				tetha--;			
			}
		}
		
		// left to right plot
		else {
			while (tetha <= -angle && tetha >= angle) {
				omegaC = (float) Math.round(Transform.altOmegaMotorC(Math.abs(tetha), Plotbot.armMotor.getSpeed()));
				Plotbot.wheelMotor.setSpeed(omegaC);
				if (tetha <= 0) {
					Plotbot.wheelMotor.backward();	
				}
				
				else {
					Plotbot.wheelMotor.forward();
				}
				tetha++;			
			}	
		}
		
		Plotbot.wheelMotor.stop();
		Plotbot.pen.up();
	}
}
