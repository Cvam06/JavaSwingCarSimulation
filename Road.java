package edu.neu.csye6200.av;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

/**
 * This Panel is for the main road panel where the center panel actions are implemented.
 * @author cvam6
 */

public class Road extends JPanel implements Observer {

	int laneHeight = 100;
	int laneWidth = 1500;
	public Color roadColor = Color.DARK_GRAY;

	private Simulation sim = new Simulation();
	private CollisionCheck collisionCheck = new CollisionCheck();

	/**
	 * This is to paint road.
	 */
	public void paint(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(roadColor);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		// Drawing lanes separation
		for (int i = 0; i <= (laneHeight * 7); i = i + laneHeight) {
			if (i == 0 || i == (laneHeight * 3) || i == (laneHeight * 4) || i == (laneHeight * 7)) {
				for (int j = 0; j < laneWidth; j++) {
					g2d.setColor(Color.BLACK);
					g2d.fillRect(j, i, 1, 5);
				}
			} else {
				for (int j = 0; j < laneWidth; j = j + 60) {
					g2d.setColor(Color.WHITE);
					g2d.fillRect(j, i, 30, 5);
				}
			}
		}

		drawCar(g2d);
	}

	/**
	 * This method is to draw car.
	 * @param g
	 */
	public void drawCar(Graphics g) {
		for (Vehicle v : sim.vehicleList) {
			if (v.getSpeed() == 7) {
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(v.getX(), v.getY(), v.getLength(), v.getWidth());
			} else if (v.getSpeed() == 10) {
				g.setColor(Color.ORANGE);
				g.fillRect(v.getX(), v.getY(), v.getLength(), v.getWidth());
			} else {
				g.setColor(Color.PINK);
				g.fillRect(v.getX(), v.getY(), v.getLength(), v.getWidth());
			}
		}
	}

	/**
	 * When simulation is running, so regarding the update the action perfomance on Road is implemented here.
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Simulation) {
			sim = (Simulation) arg;
			for (Vehicle v : sim.vehicleList) {
				if (v.getCurrLane() == 1 || v.getCurrLane() == 2 || v.getCurrLane() == 3) {
					if (collisionCheck.checkCollision(sim.vehicleList, v) == false) {
						v.setX(v.getX() + v.getSpeed());
					} else {
						int changeLaneTo = collisionCheck.checkUpperLaneChange(sim.vehicleList, v);
						if (changeLaneTo != 0) {
							if (changeLaneTo == 1) {
								v.setY(30);
								v.setCurrLane(changeLaneTo);
							} else if (changeLaneTo == 2) {
								v.setY(130);
								v.setCurrLane(changeLaneTo);
							} else if (changeLaneTo == 3) {
								v.setY(230);
								v.setCurrLane(changeLaneTo);
							}
						}
					}
				} else if (v.getCurrLane() == 4 || v.getCurrLane() == 5 || v.getCurrLane() == 6) {
					if (collisionCheck.checkCollision(sim.vehicleList, v) == false) {
						v.setX(v.getX() - v.getSpeed());
					} else {
						int changeLaneTo = collisionCheck.checkLowerLaneChange(sim.vehicleList, v);
						if (changeLaneTo != 0) {
							if (changeLaneTo == 4) {
								v.setY(430);
								v.setCurrLane(changeLaneTo);
							} else if (changeLaneTo == 5) {
								v.setY(530);
								v.setCurrLane(changeLaneTo);
							} else if (changeLaneTo == 6) {
								v.setY(630);
								v.setCurrLane(changeLaneTo);
							}
						}
					}
				}
			}
		}
	}
}
