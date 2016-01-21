package de.tuhh.diss.plotbot;

import lejos.nxt.Motor;

public class PlotTUHH {
	public static final int edge = 230;
	public static final int offset = 35; // Distance from pen to light sensor
	public static final int speedMotorC = 450;
	public int size;
	
	public PlotTUHH(int size) {
		this.size = size;
	}
	
	RobotWheels robot = new RobotWheels(5, 28);
	RobotArm arm = new RobotArm();
	
	// Create the letters and border objects
	Plotline T = new Plotline();
	Plotline U = new Plotline();
	Plotline H = new Plotline();
	Plotline border = new Plotline();
	

	
	// size is the input, height of the text
	public void plotBorder(int size) {
		int borderHeight = (int) Transform.borderHeight(size);
		int borderWidth = (int) Transform.borderWidth(size);
		
		border.lineInX(-borderHeight); // draw in x-dir to the right
		border.lineInY(-borderWidth);  // draw in y-dir backward
		border.lineInX(borderHeight); // draw in x-dir to the left
		border.lineInY(borderWidth);  // draw in y-dir forward
	}
	
	public void plotStringT(int size) {
		int headT = (int) Transform.textWidth(size);
		int bodyT = size;
		
		// Draw from left to right
		T.lineInX(-bodyT);
		
		int mid = (int) Transform.distanceTachoC(headT / 2);
		robot.drive(mid);
		T.lineInY(-headT); // backward
		
	}
	
	public void plotStringU(int size) {
		int bodyU = (int) Transform.textWidth(size);
		int baseU = size;
		
		// Put arm to the starting drawing of U position (consider text gap)
		// Or the placement of pen for next letter in the main()
		
		U.lineInX(bodyU); // right to left
		U.lineInY(-baseU); // go down
		U.lineInX(-bodyU); // left to right
	}
	
	public void plotStringH(int size) {
		int bodyH = (int) Transform.textWidth(size);
		int middleH = size;
		int reposition = (int) Transform.tachoC(size);
		
		H.lineInX(bodyH); // right to left
		arm.init();
		robot.drive(-reposition);
		H.lineInY(-middleH); // backwards
		robot.drive(reposition);
		H.lineInX(-bodyH); // left to right
		
	}

	public void goToUpEdge() {

		Motor.C.setSpeed(speedMotorC);
		robot.drive(edge + offset); // 35 is offset from pen and light sensor
		
	}
	
	public void main(String[] args) {
		// TODO Auto-generated method stub
		goToUpEdge();
		plotBorder(this.size);
		
		// Put positioning code according to edgeGap
		Motor.A.rotate((int) Transform.shiftAngle(this.size));
		Motor.C.rotate((int) -(Transform.edgeGap(this.size) + Transform.distanceTachoC(Transform.textWidth(size) / 2)));
		
		plotStringT(this.size);
		
		Motor.C.rotate((int) -Transform.textGap(this.size));
		
		plotStringU(this.size);
		
		// Put positioning code according to textGap
		Motor.C.rotate((int) -Transform.textGap(this.size));
		
		plotStringH(this.size);
		
		// Put positioning code according to textGap
		Motor.C.rotate((int) -Transform.textGap(this.size));
		
		plotStringH(this.size);
		
		Motor.C.rotate(-1000); // Get out from the drawing field
	}

}
