package de.tuhh.diss.plotbot;

import lejos.nxt.Motor;

public class PlotTUHH {
	public static final int edge = 230;
	public static final int offset = 25; // Distance from pen to light sensor
	public static final int speedMotorC = 450;
	public int size;
	
	public PlotTUHH(int size) {
		this.size = size;
	}
	
	// Create the letters and border objects
	Plotline T = new Plotline();
	Plotline U = new Plotline();
	Plotline H = new Plotline();
	Plotline border = new Plotline();
	

	
	// size is the input, height of the text
	public void plotBorder(int size) {
		int borderHeight = (int) Transform.borderHeight(size);
		int borderWidth = (int) Transform.borderWidth(size);
		
		border.lineInX(borderHeight); // draw in x-dir to the right
		border.lineInY(-borderWidth);  // draw in y-dir backward
		border.lineInX(-borderHeight); // draw in x-dir to the left
		border.lineInY(borderWidth);  // draw in y-dir forward
	}
	
	public void plotStringT(int size) {
		int headT = (int) Transform.textWidth(size);
		int bodyT = size;
		
		// Draw from left to right
		T.lineInX(bodyT);
		
		int mid = Math.round(headT / 2);
		Plotbot.robotWheels.drive(mid);
		T.lineInY(-headT); // backward
		
	}
	
	public void plotStringU(int size) {
		int bodyU = size;
		int baseU = (int) Transform.textWidth(size);
		
		U.lineInX(-bodyU); // right to left
		U.lineInY(-baseU); // go backward
		U.lineInX(bodyU); // left to right
	}
	
	public void plotStringH(int size) {
		int bodyH = size;
		int middleH = (int) Transform.textWidth(size);
		int reposition = (int) Transform.offsetYdueToX(Transform.sweepAngle(size));
		
		H.lineInX(-bodyH); // right to left
		Plotbot.robotArm.moveTo(0);
		Plotbot.robotWheels.drive(-reposition);
		H.lineInY(-middleH); // backwards
		Plotbot.robotWheels.drive(reposition);
		H.lineInX(bodyH); // left to right
		
	}

	public void goToUpEdge() {
		double angle = Transform.sweepAngle(this.size);
		
		Plotbot.wheelMotor.setSpeed(speedMotorC);
		Plotbot.robotWheels.drive(edge + (int) Transform.offsetYdueToX(angle)); 
	}

	public void gap(int size){
		Plotbot.robotWheels.drive((int) -Transform.textGap(this.size));
	}
	
	public void plot() {
		// TODO Auto-generated method stub
		goToUpEdge();
		plotBorder(this.size);
		
		// Pen placement after draw the border
		Plotbot.armMotor.rotate((int) Transform.shiftAngle(this.size));
		Plotbot.robotWheels.drive((int) -(Transform.shiftPosition(this.size) + Transform.edgeGap(this.size) + (Transform.textWidth(size) / 2)));
		
		plotStringT(this.size);
		
		gap(this.size);
		
		plotStringU(this.size);
		
		gap(this.size);
		
		plotStringH(this.size);
		
		gap(this.size);
		
		plotStringH(this.size);
		
		Plotbot.robotWheels.drive((int) Transform.wheelDistance(-100)); // Get out from the drawing field
	}

}
