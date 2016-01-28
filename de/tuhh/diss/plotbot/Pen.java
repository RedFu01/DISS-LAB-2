package de.tuhh.diss.plotbot;

import lejos.nxt.LCD;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.TouchSensor;

/**
 * class Pen: methods and Sensor- & Motor-initialization for the pen
 * @param TouchSensor penSensor
 * @param NXTRegulatedMotor penMotor
 * @return void
 */
public class Pen {
	private TouchSensor penSensor= null;
	private NXTRegulatedMotor motor = null;
	private int HEIGHT = 0;
	private int speed = 660;
	private boolean isDown = false;
	private boolean calibrated = false;
	private int sign = 1;
	
	public Pen(TouchSensor penSensor, NXTRegulatedMotor penMotor){
		this.penSensor = penSensor;
		this.motor = penMotor;
	}

	/**
	 * init():set motor.tacho-count to zero
	 * @param void
	 * @return void
	 */
	public void init(){
		motor.resetTachoCount();

	}
	
	/**
	 * move(): rotate the PenMotor with the desired speed  
	 * @param degrees
	 * @return void
	 */
	public void move(int degrees){
		motor.setSpeed(speed);
		motor.rotate(sign*degrees, false);
		stop();
	}
	
	/**
	 * down(): set the pen down on the drawing ground
	 * @param void
	 * @return void
	 */
	public void down(){
		if(!isDown){
			move(HEIGHT);
			isDown = true;
		}
	}
	
	/**
	 * up(): lift the pen
	 * @param void
	 * @return void
	 */
	public void up(){
		motor.forward();
		while(!penSensor.isPressed()){
			
		}
		motor.stop();
		isDown = false;
	}
	
	/**
	 * isUp(): check if the pen is up
	 * @param void 
	 * @return isDown
	 */
	public boolean isUp(){
		return isDown;
	}
	
	/**
	 * calibratePen(): first setup of the pen
	 * calculation the amount of degrees the motor has to move, to put the pen down save.
	 * @param void
	 * @return void
	 */
	public void calibratePen(){	
		up();
		motor.resetTachoCount();
		motor.backward();
		while(penSensor.isPressed()){
		}
		while(!penSensor.isPressed()){
		}
		motor.stop();
		HEIGHT = motor.getTachoCount()/2;

		motor.forward();
		while(penSensor.isPressed()){
		}
		up();
		calibrated = true;
		LCD.clear();
	}
	
	/**
	 * stop(): stop the motor movement
	 * @param coid
	 * @return void
	 */
	private void stop(){
		motor.stop();
	}
	
	/**
	 * getCalibrationStatus(): return calibration status
	 * @param void
	 * @return calibrated
	 */
	public boolean getCalibrationStatus(){
		return calibrated;
	}

}
