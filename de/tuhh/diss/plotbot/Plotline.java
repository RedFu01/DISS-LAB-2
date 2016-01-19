package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.LCD;

public class Plotline {
	public static final double WHEELCIRCUMFERENCE = 175.93; // unit in mm
	public static final double ARM2PEN = 80.0; // unit in mm
	public static final double ARM2LIGHT = 105.0; // unit in mm
	public static final int GEARRATIOA = 84;
	public static final int GEARRATIOC = 5;

	// Create object pen
	public Pen pen;
	
	public Plotline(Pen pn) {
		pen = pn;
	}
	
	// edgeLocation = 230 / wheelCircumference * gearRatioC * 360
	public static final double edgeLocation = 2353.21; // in degrees relative to
														// Motor.C

	// speedWheel = 0.2 * speedMotor
	public static final int speedMotorC = 450; // speedWheel = 90 deg/s

	// Should this in Plotline Class or in other class?
	public void goToUpEdge() {
		
		Motor.C.setSpeed(450);
		moveForward();

		// Distance still (?) need to consider pen and light sensor location?
		while (Motor.C.isMoving() && Motor.C.getTachoCount() < edgeLocation && !Button.ESCAPE.isDown()) {
			LCD.drawInt(Motor.C.getTachoCount(), 0, 0);
		}
		Motor.C.stop();
	}

	public void lineInY(double lineLengthY) {
		double lengthY = lineLengthY / WHEELCIRCUMFERENCE * GEARRATIOC * 360;

		Motor.C.setSpeed(450);
		
		// This assumes that NXT at y = 230 mm or TachoCount = 2353.21
		if (Motor.C.getTachoCount() == edgeLocation) {
			Motor.C.resetTachoCount();
			// Assume the swivel arm at position 0 degree from calibration?
			// Or should I add logic for swivel position, to determine forward vs. backward?
			
			// swivelStraight();

			// Put pen operation here
			pen.down();

			moveBackward();

			while (Motor.C.isMoving() && Motor.C.getTachoCount() < lengthY && !Button.ESCAPE.isDown()) {
				LCD.drawInt(Motor.C.getTachoCount(), 0, 0);
			}
			Motor.C.stop();
		}
		
		else {
			Motor.C.resetTachoCount();
			
			pen.down();
			
			moveForward();
			
			while (Motor.C.isMoving() && Motor.C.getTachoCount() < lengthY && !Button.ESCAPE.isDown()) {
				LCD.drawInt(Motor.C.getTachoCount(), 0, 0);
			}
			Motor.C.stop();
		}
	}

	public void lineInX(double lineLengthX) {
		TouchSensor touchSwivel = new TouchSensor(SensorPort.S1);

		double angledb, distanceY;
		int omegaA = 450, omegaC;

		angledb = Transform.sweepAngle(lineLengthX); // angle in 'double' type

		// Cannot give exact angle movement due to double vs. int
		int angle = (int) Math.round(angledb); // angle in 'int' type

		omegaC = (int) Transform.omegaMotorC(angle, omegaA);

		distanceY = Transform.tachoC(angle);

		// Motors' speed setup
		Motor.A.setSpeed(omegaA);
		Motor.C.setSpeed(omegaC);

		// Movement of arm from RIGHT to LEFT
		if (touchSwivel.isPressed() == true) {
			pen.down();
			
			// First half of horizontal line
			// Arm swivel rotates with amount of angle CCW while the robot move backwards
			Motor.A.rotate(angle, true);
			moveBackward();
			while (Motor.C.isMoving() && Motor.C.getTachoCount() < distanceY && !Button.ESCAPE.isDown()) {
				LCD.drawInt(Motor.C.getTachoCount(), 0, 0);
			}
			Motor.C.stop();

			// Second half of horizontal line
			Motor.A.rotate(angle, true);
			moveForward();
			while (Motor.C.isMoving() && Motor.C.getTachoCount() < distanceY && !Button.ESCAPE.isDown()) {
				LCD.drawInt(Motor.C.getTachoCount(), 0, 0);
			}
			Motor.C.stop();
		}

		// Movement of arm from LEFT to RIGHT
		else {
			pen.down();
			
			// First half of horizontal line
			// Arm swivel rotates with amount of angle CW while the robot move backwards
			Motor.A.rotate(-angle, true);
			moveBackward();
			while (Motor.C.isMoving() && Motor.C.getTachoCount() < distanceY && !Button.ESCAPE.isDown()) {
				LCD.drawInt(Motor.C.getTachoCount(), 0, 0);
			}
			Motor.C.stop();

			// Second half of horizontal line
			Motor.A.rotate(-angle, true);
			moveForward();
			while (Motor.C.isMoving() && Motor.C.getTachoCount() < distanceY && !Button.ESCAPE.isDown()) {
				LCD.drawInt(Motor.C.getTachoCount(), 0, 0);
			}
			Motor.C.stop();
		}

	}

	// Motor C Control, should this be in this clas?
	public void moveForward() {
		// Motor.C.setSpeed(speedMotorC);
		Motor.C.resetTachoCount();
		Motor.C.forward();
	}

	public void moveBackward() {
		// Motor.C.setSpeed(speedMotorC);
		Motor.C.resetTachoCount();
		Motor.C.backward();
	}

	public void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
