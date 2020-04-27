package edu.neu.csye6200.av;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

public class Simulation extends Observable implements Runnable {
	private Thread thread = null; // the thread that runs my simulation
	private boolean paused = false;
	private boolean done = false; // set true to end the simulation loop
	private boolean running = false; // set true if the simulation is running
	
	private Vehicle v1 = new Vehicle(0, 30, 7, 120, 40, 1);
	private Vehicle v2 = new Vehicle(0, 130, 10, 120, 40, 2);
	private Vehicle v3 = new Vehicle(0, 230, 3, 120, 40, 3);
	private Vehicle v4 = new Vehicle(1380, 430, 7, 120, 40, 4);
	private Vehicle v5 = new Vehicle(1380, 530, 3, 120, 40, 5);
	private Vehicle v6 = new Vehicle(1380, 630, 10, 120, 40, 6);
	public ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>(Arrays.asList(v1, v2, v3, v4, v5, v6)); //Initial set of vehicles.
	private int speedType = 1;
	
	/**
	 * When start button is pressed the main simulation starts and thread is executed.
	 */
	public void startSim() {
		if (thread != null) return; // A thread is already running
		
		thread = new Thread(this); // Create a worker thread
		paused = false;
		done = false; // reset the done flag.
		thread.start();
		
	}
	
	/**
	 * This method is to pause the simulation.
	 */
	public void pauseSim() {
		paused = !paused;
	}
	
	/**
	 * This method only returns if the simulation is paused or not.
	 * @return 
	 */
	public boolean isPaused() {
		return paused;
	}
	
	public boolean isPausable() {
		if (!running) return false;
		if (done) return false;
		return true;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	/**
	 * Stop simulation will stop the simulation and everything as initial condition.
	 * So after Stop when start will be pressed the simulation will start simulation from initial situation.
	 */
	public void stopSim() {
		setInitialVehicleArray();
		if (thread == null) return; // defensive coding in case the thread is null
		done = true;
		thread = null;
		
	}

	@Override
	public void run() {
		runSimLoop();
		thread = null; // flag that the simulation thread is finished
	}
	
	/**
	 * In this method, If no rule is selected then it will have 100L sleep,
	 * If it has speed up rule for simulation then it will have 50L sleep,
	 * If it has slow down rule for simulation then it will have 200L sleep,
	 * These all Sleep will cause the simulation to run in selected speed as per rule defined.
	 */
    private void runSimLoop() {
    	running = true;
    	while(!done) {
    		// do some simulation work
    		if (!paused)
    		    updateSim();
    		if(speedType == 1) {
    			sleep(100L); 
    		}
    		else if(speedType == 2) {
    			sleep(200L); 
    		}
    		else if(speedType == 3) {
    			sleep(50L);
    		}
    		else {
    			sleep(100L);
    		}
    		
    	}
    	running = false;
    }
	/**
	 * Make the current thread sleep a little
	 */
    private void sleep(long millis) {
    	try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    
    private void updateSim() {
    	setChanged();
    	notifyObservers(this); // Send a copy of the simulation
    }
    
    /**
     * When launch car will be pressed then this method is called.
     * This method will get the vehicle as object and see which lane and which car is selected, 
     * And accordingly that the car will be added to the list of cars.
     * @param v
     */
	public void addCar(Vehicle v) {
		System.out.println("Add Car called.");
		if(v.getCurrLane() == 1) {
			v.setX(0);
			v.setY(30);
			vehicleList.add(v);
		}
		else if(v.getCurrLane() == 2) {
			v.setX(0);
			v.setY(130);
			vehicleList.add(v);
		}
		else if(v.getCurrLane() == 3) {
			v.setX(0);
			v.setY(230);
			vehicleList.add(v);
		}
		else if(v.getCurrLane() == 4) {
			v.setX(1500 - v.getLength());
			v.setY(430);
			vehicleList.add(v);
		}
		else if(v.getCurrLane() == 5) {
			v.setX(1500 - v.getLength());
			v.setY(530);
			vehicleList.add(v);
		}
		else if(v.getCurrLane() == 6) {
			v.setX(1500 - v.getLength());
			v.setY(630);
			vehicleList.add(v);
		}
	}


	/**
	 * When slow down rule applied, then to control the simulation speed this method is called.
	 */
	public void slowSpeedRule() {
		speedType = 2;
	}

	/**
	 * When Speed up rule applied, then to control the simulation speed this method is called.
	 */
	public void speedUpRule() {
		speedType = 3;
	}

	/**
	 * When NO rule applied, then to control the simulation as the normal speed this method is called.
	 */
	public void noRuleApply() {
		speedType = 1;
	}
	
	/**
	 * This method is to set every thing to the initial point when Stop action is performed at any moment.
	 */
	public void setInitialVehicleArray() {
		v1 = new Vehicle(0, 30, 7, 120, 40, 1);
		v2 = new Vehicle(0, 130, 10, 120, 40, 2);
		v3 = new Vehicle(0, 230, 3, 120, 40, 3);
		v4 = new Vehicle(1380, 430, 7, 120, 40, 4);
		v5 = new Vehicle(1380, 530, 3, 120, 40, 5);
		v6 = new Vehicle(1380, 630, 10, 120, 40, 6);
		vehicleList = new ArrayList<Vehicle>(Arrays.asList(v1, v2, v3, v4, v5, v6)); //Initial set of vehicles.
	}
	
}
