package de.tuhh.diss.plotbot;

public class Transform {
	public static final double WHEELCIRCUMFERENCE = 175.93; // unit in mm
	public static final double WHEELRADIUS = 28.0; // unit in mm
	public static final double ARM2PEN = 80.0; // unit in mm
	public static final double ARM2LIGHT = 105.0; // unit in mm
	public static final double GEARRATIOA = 84.0;
	public static final double GEARRATIOC = 5.0;

	// Calculate angle from input size, for square max ~113 mm and for TUHH string max ~79 mm
	public static double sweepAngle(double size) {
		return Math.toDegrees(Math.asin(size / (2 * ARM2PEN))); // in degrees and mm
	}
	
	// Movement in Y-axis to accommodate angled arm position
	public static double offsetYdueToX(double angle) {
		return (80.0 - 80.0 * Math.cos(Math.toRadians(angle)));
	}

	// TUHH string plotting dimensions
	public static double borderHeight(double textSize) {
		return textSize / 0.7;
	}

	public static double borderWidth(double textSize) {
		return ((2.8 * edgeGap(textSize)) + (3 * textGap(textSize)) + (4 * textWidth(textSize)));
	}

	public static double textWidth(double textSize) {
		return 0.5 * textSize;
	}

	public static double edgeGap(double textSize) {
		return (borderHeight(textSize) - textSize) / 2;
	}

	public static double textGap(double textSize) {
		return edgeGap(textSize) / 2; 
	}
	
	// Placing pen to draw strings
	public static double shiftAngle(double textSize) {
		return beta(textSize) - alpha(textSize);
	}
	
	public static double alpha(double textSize) {
		return Math.toDegrees(Math.asin((0.5 * borderHeight(textSize)) / ARM2PEN));
	}
	
	public static double beta(double textSize) {
		return Math.toDegrees(Math.asin((0.5 * textSize) / ARM2PEN));
	}
	
	public static double shiftPosition(double textSize) {
		return (ARM2PEN * Math.cos(Math.toRadians(beta(textSize)))) - (ARM2PEN * Math.cos(Math.toRadians(alpha(textSize))));
	}
	
	// Calculate the distance required to make a straight line due to arc movement
	public static double accmomodateArc(double angle1, double angle2) {
		return (ARM2PEN * Math.cos(Math.toRadians(angle2))) - (ARM2PEN * Math.cos(Math.toRadians(angle1)));
	}
}
