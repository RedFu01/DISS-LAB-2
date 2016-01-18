package de.tuhh.diss.plotbot;

public class MotorControl {
	private int posX = 0;
	private int posY = 0;
	
	public void moveTo(int x, int y){
		
		int deltaY = this.posY - y;
		int
		
		
		this.posX = x;
		this.posY = y;
	}
	
	public void move(int x, int y){
		
		this.posX += x;
		this.posY += y;
	}

}
