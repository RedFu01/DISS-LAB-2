package de.tuhh.diss.plotbot;

import lejos.nxt.Motor;

public class Plotsquare {
	public static final int edge = 230;
	public static final int offset = 35; // Distance from pen to light sensor
	public static final int speedMotorC = 450;
	public int size;
	
	public Plotsquare(int size) {
		this.size = size;
	}
	
	// Create object square
	Plotline square = new Plotline();

	public void plotSquare(int size) {
		square.lineInX(this.size);
		square.lineInY(-this.size);
		square.lineInX(-this.size);
		square.lineInY(this.size);
	}

	public void goToUpEdge() {
		double angle = Transform.sweepAngle(this.size);
		
		Motor.C.setSpeed(speedMotorC);
		Plotbot.robotWheels.drive(edge + offset + (int) Transform.tachoC(angle)); 
		// 35 is offset from pen and light sensor + more offset due to angled arm
	}

	public void plot() {
		// TODO Auto-generated method stub

		goToUpEdge();
		plotSquare(size);
	}

}
