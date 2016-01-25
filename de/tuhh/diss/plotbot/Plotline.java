package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.LCD;

public class Plotline {

	// Create objects
	Pen pen = new Pen();
	RobotWheels robot = new RobotWheels(5, 28);
	RobotArm arm = new RobotArm(1);

	public static final int speedMotorC = 450; // Default speed

	public void lineInY(int lineLengthY) {
		int lengthY = lineLengthY;

		Motor.C.setSpeed(speedMotorC);
		Motor.C.resetTachoCount();
		
		pen.down();
		robot.driveToDistance(lengthY);

		while (Motor.C.isMoving() && !Button.ESCAPE.isDown()) {
			LCD.drawInt((int) Transform.distanceC(Motor.C.getTachoCount()), 0, 0);
		}
		Motor.C.stop();
		Motor.C.resetTachoCount();
		
		pen.up();

	}

	public void lineInX(int lineLengthX) {
		double angledb;
		int omegaC;
		int distanceY = 1;

		angledb = Transform.sweepAngle(lineLengthX); // angle in 'double' type
		
		omegaC = (int) Transform.syncA2C(Motor.A.getSpeed());
		
		// Cannot give exact angle movement due to double vs. int
		int angle = (int) Math.round(angledb); // angle in 'int' type
		
		int countAngle;

		// Motors' speed setup, synchronized C depends on A
		Motor.C.setSpeed(omegaC);
		
		// Right to left drawing or left to right depends on +/- length of X
		
		arm.init();
		arm.moveTo(angle);
		pen.down();
		
		// Right to Left drawing
		if (angle < 0) {
			// Start drawing from right to middle
			for (countAngle = 0; countAngle > angle; countAngle--) {
				arm.move(1);
				robot.drive(-distanceY);
			}

			// Start drawing from middle to left
			for (countAngle = 0; countAngle > angle; countAngle--) {
				arm.move(1);
				robot.drive(distanceY);
			}
			
			pen.up();
		}
		
		// Left to Right drawing
		else {
			// Start drawing from left to middle
			for (countAngle = 0; countAngle < angle; countAngle++) {
				arm.move(-1);
				robot.drive(-distanceY);
			}

			// Start drawing from middle to right
			for (countAngle = 0; countAngle < angle; countAngle++) {
				arm.move(-1);
				robot.drive(distanceY);
			}
			
			pen.up();
		}
	}
}
