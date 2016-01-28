package de.tuhh.diss.plotbot;

public class Plotsquare {
	public static final int edge = 230;
	public static final int offset = 25; // Distance from pen to light sensor
	public static final int speedMotorC = 450;
	public int size;
	
	public Plotsquare(int size) {
		this.size = size;
	}
	
	Plotline square = new Plotline();

	/**
	 * plotSquare(): command the robot to plot a square with the size as user request 
	 * @param int size
	 * @return void
	 */
	public void plotSquare(int size) {
		square.lineInX(-this.size); // draw horizontally to the left
		square.lineInY(-this.size); // draw in vertically backward
		square.lineInX(this.size); // draw horizontally to the right
		square.lineInY(this.size); // draw in vertically forward
	}

	/**
	 * goToUpEdge(): command the robot to go to the starting plot position (edge) 
	 * @param void
	 * @return void
	 */
	public void goToUpEdge() {
		double angle = Transform.sweepAngle(this.size);
		
		Plotbot.wheelMotor.setSpeed(speedMotorC);
		Plotbot.robotWheels.drive(edge + (int) Transform.offsetYdueToX(angle)); 
	}

	/**
	 * plot(): command the robot to go to the edge then plot the square
	 * @param void
	 * @return void
	 */
	public void plot() {
		goToUpEdge();
		plotSquare(size);
		
		Plotbot.robotWheels.drive(-(size+75)); // Get out from the drawing field
	}

}
