package edu.neu.csye6200.av;

public class Vehicle {

	private static int nextId = 0;
	int x = 0;
	int y = 0;
	int speed = 0;
	int length = 0;
	int width = 0;
	int currLane = 0;
	int id;
	
	public Vehicle(int x, int y, int speed, int length, int width, int currLane) {
		nextId = nextId + 1;
		this.id = nextId;
		this.x = x; 
		this.y = y;
		this.speed = speed;
		this.length = length;
		this.width = width;
		this.currLane = currLane;
	}

	public static int getNextId() {
		return nextId;
	}
	
	public static void setNextId(int nextId) {
		Vehicle.nextId = nextId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCurrLane() {
		return currLane;
	}

	public void setCurrLane(int currLane) {
		this.currLane = currLane;
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	
	
}
