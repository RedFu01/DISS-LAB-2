package de.tuhh.diss.plotbot;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;

public class Plotsquare {

	// Create object square
	public Plotline square;

	public Plotsquare(Plotline sq) {
			square = sq;
		}

	public void plotSquare(double size) {
		square.lineInX(size);
		square.lineInY(size);
		square.lineInX(size);
		square.lineInY(size);
	}

	public void main(String[] args) {
		// TODO Auto-generated method stub
		TouchSensor touchSwivel = new TouchSensor(SensorPort.S1);

		int size = 113;

		square.goToUpEdge();

		while (touchSwivel.isPressed() == false) {
			Motor.A.rotate(-1); // Assume the swivel arm is initially straight
		}
		Motor.A.stop();
		Motor.A.resetTachoCount();

		plotSquare(size);
	}

}
