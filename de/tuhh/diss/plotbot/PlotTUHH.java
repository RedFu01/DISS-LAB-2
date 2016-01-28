package de.tuhh.diss.plotbot;

public class PlotTUHH {
	public static final int edge = 230;
	public static final int speedMotorC = 450;
	public int size;
	
	public PlotTUHH(int size) {
		this.size = size;
	}
	
	Plotline T = new Plotline();
	Plotline U = new Plotline();
	Plotline H = new Plotline();
	Plotline border = new Plotline();
	
	/**
	 * plotBorder(): command the robot to plot a border for TUHH
	 * @param int size
	 * @return void
	 */
	public void plotBorder(int size) {
		
		// size is the input, height of the text
		int borderHeight = (int) Transform.borderHeight(size);
		int borderWidth = (int) Transform.borderWidth(size);
		
		border.lineInX(borderHeight); // draw horizontally to the right
		border.lineInY(-borderWidth);  // draw in vertically backward
		border.lineInX(-borderHeight); // draw in horizontally to the left
		border.lineInY(borderWidth);  // draw in vertically forward
	}
	
	/**
	 * plotStringT(): command the robot to plot a letter T from TUHH
	 * @param int size
	 * @return void
	 */
	public void plotStringT(int size) {
		
		int headT = (int) Transform.textWidth(size);
		int bodyT = size;
		
		// Draw from left to right
		T.lineInX(bodyT);
		
		int mid = Math.round(headT / 2);
		Plotbot.robotWheels.drive(mid);
		T.lineInY(-headT); // backward
		
	}
	
	/**
	 * plotStringU(): command the robot to plot a letter U from TUHH
	 * @param int size
	 * @return void
	 */
	public void plotStringU(int size) {
		
		int bodyU = size;
		int baseU = (int) Transform.textWidth(size);
		
		U.lineInX(-bodyU); // right to left
		U.lineInY(-baseU); // go backward
		U.lineInX(bodyU); // left to right
	}
	
	/**
	 * plotStringH(): command the robot to plot a letter H from TUHH
	 * @param int size
	 * @return void
	 */
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
	 * gap(): command the robot to move as making a gap between letters 
	 * @param int size
	 * @return void
	 */
	public void gap(int size){
		Plotbot.robotWheels.drive((int) -Transform.textGap(this.size));
	}
	
	/**
	 * plot(): command the robot to go to the edge then plot the border followed by letters 
	 * @param void
	 * @return void
	 */
	public void plot() {
		
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
		
		Plotbot.robotWheels.drive(-50); // Get out from the drawing field
	}

}
