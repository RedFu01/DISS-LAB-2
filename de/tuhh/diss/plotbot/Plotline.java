package de.tuhh.diss.plotbot;

import lejos.nxt.LCD;

public class Plotline {
	
	private static final int speedMotorC = 300; // Default speed

	/**
	 * lineInY(): command the robot to draw in y-axis (vertical line)
	 * @param int lineLengthY
	 * @return void
	 */
	public void lineInY(int lineLengthY) {

		Plotbot.wheelMotor.setSpeed(speedMotorC);
		Plotbot.pen.down();
		Plotbot.robotWheels.drive(lineLengthY);
		Plotbot.pen.up();

	}

	/**
	 * lineInX(): command the robot to draw a line in x-axis (horizontal line) 
	 * @param int lineLengthX
	 * @return void
	 */
	public void lineInX(int lineLengthX) {
		
		double angledb = 0;
		int armSpeed = 100;
		int wheelSpeed = 960;
		
		int position = 0;
		int position1 = 0;
		int position2 = 0;
		
		double distance = 0;
		
		angledb = Transform.sweepAngle(lineLengthX); // angle in 'double' type
		int angle = (int) Math.round(angledb); // angle in 'int' type

		// Right to left drawing or left to right depends on +/- * length of X as the method input
		// Straight arm position: to ensure proper movement in independent of previous arm position
		Plotbot.robotArm.moveTo(0); 
		
		Plotbot.robotArm.moveTo(angle);
		Plotbot.pen.down();
		Plotbot.robotArm.setSpeed(armSpeed);
		
		position = Plotbot.robotArm.getPosition();
		Plotbot.robotArm.moveTo(-position, true);

		position1 = Plotbot.robotArm.getPosition();
		while (Math.abs(Plotbot.robotArm.getPosition() + position)>1) {
			
			if (distance != 0) {
				position1 = position2;
			}
		
			Plotbot.robotWheels.setSpeed(wheelSpeed);
			position2 = Plotbot.robotArm.getPosition();
			distance = Transform.accmomodateArc(position1, position2);
			
			LCD.drawString("-" + distance, 0, 1);
			if (position1 !=0 || position2 != 0){
			Plotbot.robotWheels.drive(-distance);
			}		
			
			Plotbot.robotArm.resetSpeed();
		}
		
		Plotbot.pen.up();
	}
}
