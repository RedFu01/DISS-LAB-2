package de.tuhh.diss.plotbot;

public class Transform {
	public static final double WHEELCIRCUMFERENCE = 175.93; // unit in mm
	public static final double WHEELRADIUS = 28.0; // unit in mm
	public static final double ARM2PEN = 80.0; // unit in mm
	public static final double ARM2LIGHT = 105.0; // unit in mm
	public static final double GEARRATIOA = 84.0;
	public static final double GEARRATIOC = 5.0;

	// Angle from input size, for square max ~113 mm and for TUHH string max ~79 mm
	public static double sweepAngle(double size) {
		return Math.asin(size / (2 * ARM2PEN)); // in degrees and mm
	}
	
	// TachoCount of Motor C movement in y-axis to accommodate straight line in x-axis
	// tachoC = (80 - 80 cos(angle)) / wheelCircumference * gearRatioC * 360
	public static double tachoC(double angle) {
		return (80.0 - 80.0 * Math.cos(angle)) / WHEELCIRCUMFERENCE * GEARRATIOC * 360;
	}
	
	public static double distanceTachoC(double distance) {
		return distance / WHEELCIRCUMFERENCE * GEARRATIOC * 360;
	}

	// Work on linear velocity or angular velocity?
	public static double carVel(double angle, double armVel) {
		return ((1.0 - Math.cos(angle)) / Math.sin(angle)) * armVel;
	}

	public static double omegaMotorA(double armVel) {
		return (GEARRATIOA * armVel) / ARM2PEN;
	}

	public static double omegaMotorC(double angle, double omegaMotorA) {
		return ((1.0 - Math.cos(angle)) / Math.sin(angle)) * (GEARRATIOC / GEARRATIOA) * (ARM2PEN / WHEELRADIUS) * omegaMotorA;
	}

	// TUHH string plotting dimensions
	public static double borderHeight(double textSize) {
		return textSize / 0.7;
	}

	public static double borderWidth(double textSize) {
		return 2 * edgeGap(textSize) + 3 * textGap(textSize) + 4 * textWidth(textSize);
	}

	public static double textWidth(double textSize) {
		return 0.7 * textSize;
	}

	public static double edgeGap(double textSize) {
		return (borderHeight(textSize) - textSize) / 2;
	}

	public static double textGap(double textSize) {
		return edgeGap(textSize) / 2; 
	}
}