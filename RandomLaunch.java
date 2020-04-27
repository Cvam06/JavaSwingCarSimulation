package edu.neu.csye6200.av;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class RandomLaunch {

	private Timer timer;
	private Simulation sim;
	private MyAppUI mpu;
	private int[] speeds = { 3, 7, 10 };
	private int[] launchSelectedLanes = { 1, 2, 3, 4, 5, 6 };

	public RandomLaunch(Simulation simulation, MyAppUI mpu1) {
		timer = new Timer();
		// task, delay, period
		sim = simulation;
		mpu = mpu1;
		timer.schedule(new ReminderTask(), 1000L, 3000L);
	}

	// A repeatable Task
	class ReminderTask extends TimerTask {
		@Override
		public void run() {
			int rndSpeed = new Random().nextInt(speeds.length);
			int speed = speeds[rndSpeed];
			boolean launchCheck = false;
			int rndLane = new Random().nextInt(launchSelectedLanes.length);
			int launchSelectedLane = launchSelectedLanes[rndLane];
			System.out.println("Speed = " + speed + " lane = " + launchSelectedLane);

			if (launchSelectedLane <= 3) {
				for (Vehicle vehicle : sim.vehicleList) {
					if (vehicle.getCurrLane() == launchSelectedLane) {
						if (vehicle.getX() > 130) {
							launchCheck = true;
						} else {
							launchCheck = false;
							mpu.resetSouthPanel("No Launch, Already Vehicle in Lane " + launchSelectedLane);
						}
					}
				}

				if (launchCheck == true) {
					mpu.resetSouthPanel("Car Launch in " + launchSelectedLane);
					Vehicle v = new Vehicle(0, 130, speed, 120, 40, launchSelectedLane);
					sim.addCar(v);
				}
			} else {
				for (Vehicle vehicle : sim.vehicleList) {
					if (vehicle.getCurrLane() == launchSelectedLane) {
						if (vehicle.getX() < 1380) {
							launchCheck = true;
						} else {
							launchCheck = false;
							mpu.resetSouthPanel("No Launch, Already Vehicle in Lane " + launchSelectedLane);
						}
					}
				}

				if (launchCheck == true) {
					mpu.resetSouthPanel("Car Launch in " + launchSelectedLane);
					Vehicle v = new Vehicle(0, 1380, speed, 120, 40, launchSelectedLane);
					sim.addCar(v);
				}
			}
		}
	}

}
