package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.LCD;

public class Plotline {
	
	public static final int speedMotorC = 450; // Default speed

	public void lineInY(int lineLengthY) {
		int distanceY = (int) Transform.distanceTachoC(lineLengthY);

		Motor.C.setSpeed(speedMotorC);
		Motor.C.resetTachoCount();
		
		Plotbot.pen.down();
		Plotbot.robotWheels.tachoDriveTo(distanceY);
		Plotbot.pen.up();

	}

	public void lineInX(int lineLengthX) {
		double angledb;
		int omegaC;
		double distanceY;

		angledb = Transform.sweepAngle(lineLengthX); // angle in 'double' type
		
		distanceY = Transform.tachoC(Math.abs(angledb));
		
		omegaC = (int) Transform.omegaMotorC(angledb, Motor.A.getSpeed());
		
		// Cannot give exact angle movement due to double vs. int
		int angle = (int) Math.round(angledb); // angle in 'int' type
		
		int countAngle;

		// Motors' speed setup, synchronized C depends on A
		Motor.C.setSpeed(omegaC);
		
		// Right to left drawing or left to right depends on +/- length of X
		
		Plotbot.robotArm.init();
		Plotbot.robotArm.moveTo(angle);
		Plotbot.pen.down();
		
		// 1st half of the line in x-dir
		Plotbot.robotArm.move(-angle);
		Plotbot.robotWheels.tachoDrive(-distanceY);
		
		// 2nd half of the line in x-dir
		Plotbot.robotArm.move(-angle);
		Plotbot.robotWheels.tachoDrive(distanceY);
					
		Plotbot.pen.up();
	}

	// Create objects

	/*public static final int speedMotorC = 450; // Default speed

	public void lineInY(int lineLengthY) {
		int lengthY = lineLengthY;

		Plotbot.robotWheels.setSpeed(speedMotorC);
		Plotbot.wheelMotor.resetTachoCount();
		
		Plotbot.pen.down();
		Plotbot.robotWheels.driveToDistance(lengthY);

		while (Plotbot.wheelMotor.isMoving() && !Button.ESCAPE.isDown()) {
			LCD.drawInt((int) Transform.distanceC(Plotbot.wheelMotor.getTachoCount()), 0, 0);
		}
		Plotbot.wheelMotor.resetTachoCount();
		
		Plotbot.pen.up();

	}

	public void lineInX(int lineLengthX) {
		double angledb;
		int omegaC;
		int distanceY = 1;

		angledb = Transform.sweepAngle(lineLengthX); // angle in 'double' type
		
		omegaC = (int) Transform.syncA2C(Plotbot.armMotor.getSpeed());
		
		// Cannot give exact angle movement due to double vs. int
		int angle = (int) Math.round(angledb); // angle in 'int' type
		
		int countAngle;

		// Motors' speed setup, synchronized C depends on A
		Plotbot.robotWheels.setSpeed(omegaC);
		
		// Right to left drawing or left to right depends on +/- length of X
		
		Plotbot.robotArm.init();
		Plotbot.robotArm.moveTo(angle);
		Plotbot.pen.down();
		
		// Right to Left drawing
		if (angle < 0) {
			// Start drawing from right to middle
			for (countAngle = 0; countAngle > angle; countAngle--) {
				Plotbot.robotArm.move(1);
				Plotbot.robotWheels.drive(-distanceY);
			}

			// Start drawing from middle to left
			for (countAngle = 0; countAngle > angle; countAngle--) {
				Plotbot.robotArm.move(1);
				Plotbot.robotWheels.drive(distanceY);
			}
			
			Plotbot.pen.up();
		}
		
		// Left to Right drawing
		else {
			// Start drawing from left to middle
			for (countAngle = 0; countAngle < angle; countAngle++) {
				Plotbot.robotArm.move(-1);
				Plotbot.robotWheels.drive(-distanceY);
			}

			// Start drawing from middle to right
			for (countAngle = 0; countAngle < angle; countAngle++) {
				Plotbot.robotArm.move(-1);
				Plotbot.robotWheels.drive(distanceY);
			}
			
			Plotbot.pen.up();
		}
	}*/
}
