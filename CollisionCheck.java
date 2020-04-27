package edu.neu.csye6200.av;

import java.util.ArrayList;

/**
 * This Class contains all the necessary methods to perform all the collision related tasks.
 * @author cvam6
 *
 */

public class CollisionCheck {

	private int[] sumArray = new int[20];

	/**
	 * This function will Check for collision whenever any vehicle take step ahead.
	 * @param vehicleList
	 * @param vehicle
	 * @return This will return boolean value, if will collide with another vehicle then return true, else false.
	 */
	public boolean checkCollision(ArrayList<Vehicle> vehicleList, Vehicle vehicle) {
		boolean collisionAccure = false;
		for (Vehicle vehicleItter : vehicleList) {
			/**
			 * This If will check for collision in upper lane.
			 */
			if (vehicle.getCurrLane() <= 3) {
				if (vehicle.getCurrLane() == vehicleItter.getCurrLane()) { // Checking if in same lane
					if (vehicleItter.equals(vehicle) == false) { // Checking if it is no the same Vehicle.
						/**
						 * This will check for the next step of current vehicle, So the check will be for the next step position,
						 * for next step position we need to check with addition of current vehicle speed.
						 */
						if (vehicle.getX() + vehicle.getSpeed() + vehicle.getLength() + 10 > vehicleItter.getX()
								&& vehicle.getX() + vehicle.getSpeed() + 10 < vehicleItter.getX()
										+ vehicleItter.getLength()) {
							collisionAccure = true;
						}
					}
				}
			} else if (vehicle.getCurrLane() > 3) { // This will check for collision in lower lanes.
				if (vehicle.getCurrLane() == vehicleItter.getCurrLane()) { // Checking if in same lane
					if (vehicleItter.equals(vehicle) == false) { // Checking if it is no the same Vehicle.
						/**
						 * Reverse Logic will be applied to this lane as compare to upper lane.
						 */
						if (vehicleItter.getX() + vehicleItter.getSpeed() + vehicleItter.getLength() + 10 > vehicle.getX()
								&& vehicleItter.getX() + vehicleItter.getSpeed() + 10 < vehicle.getX() + vehicle.getLength()) {
							collisionAccure = true;
						}
					}
				}
			}
		}
		return collisionAccure; // This will return true or false.
	}
	
	/**
	 * This will return the changing lane of collision vehicle for upper lane. If it returns 0 then the change of lane will not be there.
	 * @param vehicleList
	 * @param vehicle
	 * @return lane number in which vehicle will be shifted
	 */
	public int checkUpperLaneChange(ArrayList<Vehicle> vehicleList, Vehicle vehicle) {
		int changeLaneTo = 0;
		ArrayList<Vehicle> upperVehicleList = new ArrayList<Vehicle>();
		
		for(Vehicle vehicleToAdd : vehicleList) {
			if(vehicleToAdd.getCurrLane() <= 3) {
				upperVehicleList.add(vehicleToAdd);
			}
		}

		if (vehicle.getCurrLane() != 1) { // Check for Upper Lane Traversal because lane 1 vehicle can't do upper traversal.
			boolean collideWithOtherLane = false;
			boolean laneTwoCollide = false;

			for (Vehicle vehicleItter : upperVehicleList) {
				if (vehicle.getCurrLane() - 1 == vehicleItter.getCurrLane()) {
					if (vehicleItter.equals(vehicle) == false) {
						int[] vehicleItterAfter = {vehicleItter.getX(), vehicleItter.getLength()};
						int vehicleItterAfterSum = sumAll(vehicleItterAfter);
						int[] vehicleAfter = {vehicle.getX(), vehicle.getLength()};
						int vehicleAfterSum = sumAll(vehicleAfter);
						if (vehicle.getX() < vehicleItterAfterSum
								&& vehicleAfterSum > vehicleItter.getX()) {
							collideWithOtherLane = true;
						}
					}
				}
			}
			if (collideWithOtherLane == false) {
				changeLaneTo = vehicle.getCurrLane() - 1;
			}
			else if(vehicle.getCurrLane() < 3 ){ // this is for lanes between first and last lane vehicles traversal if upper lane is having collision in traversal.
				for (Vehicle vehicleItter : upperVehicleList) {
					if (vehicle.getCurrLane() + 1 == vehicleItter.getCurrLane()) {
						if (vehicleItter.equals(vehicle) == false) {
							int[] vehicleItterAfter = {vehicleItter.getX(), vehicleItter.getLength()};
							int vehicleItterAfterSum = sumAll(vehicleItterAfter);
							int[] vehicleAfter = {vehicle.getX(), vehicle.getLength()};
							int vehicleAfterSum = sumAll(vehicleAfter);
							if (vehicle.getX() < vehicleItterAfterSum
									&& vehicleAfterSum > vehicleItter.getX()) {
								laneTwoCollide = true;
							}
						}
					}
				}
				if(laneTwoCollide == false) {
					changeLaneTo = vehicle.getCurrLane() + 1;
				}
			}
		} 
		else if(vehicle.getCurrLane() < 3){ // This is for lower lane traversal and here lane 3 vehicles are not allowed as they can't traverse down words.
			boolean collideWithOtherLane = false;

			for (Vehicle vehicleItter : upperVehicleList) {
				if (vehicle.getCurrLane() + 1 == vehicleItter.getCurrLane()) {
					if (vehicleItter.equals(vehicle) == false) {
						int[] vehicleItterAfter = {vehicleItter.getX(), vehicleItter.getLength()};
						int vehicleItterAfterSum = sumAll(vehicleItterAfter);
						int[] vehicleAfter = {vehicle.getX(), vehicle.getLength()};
						int vehicleAfterSum = sumAll(vehicleAfter);
						
						if (vehicle.getX() < vehicleItterAfterSum
								&& vehicleAfterSum > vehicleItter.getX()) {
							collideWithOtherLane = true;
						}
					}
				}
			}
			if (collideWithOtherLane == false) {
				changeLaneTo = vehicle.getCurrLane() + 1;
			}

		}

		return changeLaneTo;
	}
	
	/**
	 * This will return the changing lane of collision vehicle for Lower lane. If it returns 0 then the change of lane will not be there.
	 * @param vehicleList
	 * @param vehicle
	 * @return This will return the lane number in which the vehicle will be traverse.
	 */
	
	public int checkLowerLaneChange(ArrayList<Vehicle> vehicleList, Vehicle vehicle) {
		int changeLaneTo = 0;
		ArrayList<Vehicle> lowerVehicleList = new ArrayList<Vehicle>();
		
		for(Vehicle vehicleToAdd : vehicleList) {
			if(vehicleToAdd.getCurrLane() > 3) {
				lowerVehicleList.add(vehicleToAdd);
			}
		}

		if (vehicle.getCurrLane() != 4) { // Check for Upper Lane Traversal without lane 4 because lane 4 vehicles can't traverse upper side.
			boolean collideWithOtherLane = false;
			boolean laneFiveCollide = false;

			for (Vehicle vehicleItter : lowerVehicleList) {
				if (vehicle.getCurrLane() - 1 == vehicleItter.getCurrLane()) {
					if (vehicleItter.equals(vehicle) == false) {
						int[] vehicleAfter = {vehicle.getX(), vehicle.getLength()};
						int vehicleAfterSum = sumAll(vehicleAfter);
						int[] vehicleItterAfter = {vehicleItter.getX(), vehicleItter.getLength()};
						int vehicleItterAfterSum = sumAll(vehicleItterAfter);
						if (vehicleItter.getX() < vehicleAfterSum
								&& vehicleItterAfterSum > vehicle.getX()) {
							collideWithOtherLane = true;
						}
					}
				}
			}
			if (collideWithOtherLane == false) {
				changeLaneTo = vehicle.getCurrLane() - 1;
			}
			else if(vehicle.getCurrLane() < 6) { // If vehicle of lanes between first and last lanes having collision in upper traversal then have check for lower traversal.
				for (Vehicle vehicleItter : lowerVehicleList) {
					if (vehicle.getCurrLane() + 1 == vehicleItter.getCurrLane()) {
						if (vehicleItter.equals(vehicle) == false) {
							int[] vehicleAfter = {vehicle.getX(), vehicle.getLength()};
							int vehicleAfterSum = sumAll(vehicleAfter);
							int[] vehicleItterAfter = {vehicleItter.getX(), vehicleItter.getLength()};
							int vehicleItterAfterSum = sumAll(vehicleItterAfter);
							
							if (vehicleItter.getX() < vehicleAfterSum
									&& vehicleItterAfterSum > vehicle.getX()) {
								laneFiveCollide = true;
							}
						}
					}
				}
				if(laneFiveCollide == false) {
					changeLaneTo = vehicle.getCurrLane() + 1;
				}
			}
		} else if(vehicle.getCurrLane() < 6){ // This is for Lower lane traversal without lane 6 vehicles because lane 6 vehicles can't traverse in below.
			boolean collideWithOtherLane = false;

			for (Vehicle vehicleItter : lowerVehicleList) {
				if (vehicle.getCurrLane() + 1 == vehicleItter.getCurrLane()) {
					if (vehicleItter.equals(vehicle) == false) {
						int[] vehicleAfter = {vehicle.getX(), vehicle.getLength()};
						int vehicleAfterSum = sumAll(vehicleAfter);
						int[] vehicleItterAfter = {vehicleItter.getX(), vehicleItter.getLength()};
						int vehicleItterAfterSum = sumAll(vehicleItterAfter);
						
						if (vehicleItter.getX() < vehicleAfterSum
								&& vehicleItterAfterSum > vehicle.getX()) {
							collideWithOtherLane = true;
						}
					}
				}
			}
			if (collideWithOtherLane == false) {
				changeLaneTo = vehicle.getCurrLane() + 1;
			}

		}

		return changeLaneTo;
	}
	/**
	 * This method is created to have sum of values whenever it is used in this class.
	 * @param sum
	 * @return This will return the sum of all the integers given in an array
	 */
	public int sumAll(int[] sum) {
		int result = 0;

		for (int i = 0; i < sum.length; i++) {
			result = result + sum[i];
		}

		return result;
	}
}
