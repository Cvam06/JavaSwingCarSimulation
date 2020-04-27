package edu.neu.csye6200.av;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * This Class is for the initial starting of the Program.
 * @author cvam6
 *
 */
public class MyAppUI extends AVApp implements ActionListener, Observer {

	private Road road;
	protected JPanel mainPanel;
	protected JPanel northPanel;
	protected JPanel southPanel;

	private JButton startBtn;
	private JButton pauseBtn;
	private JButton stopBtn;
	private JButton launchButton;

	private JTextField southTextField;

	private JComboBox<String> ruleComboBox;

	private Simulation sim = null;

	private int launchSelectedLane = 1;
	private String launchSelectedCar = "Slow";

	private String southTextFieldText = "No rule Applied";
	
	private JRadioButton carSelection1;
	private JRadioButton laneSelection1;

	private boolean pausePressed = false;
	@SuppressWarnings("deprecation")
	public MyAppUI() {
		
		frame.setSize(1500, 850);
		frame.setTitle("Vehicle Simulation Project");

		menuMgr.createDefaultActions(); // Set up default menu items

		initSim(); // Initialize the sim
		
		showUI(); // Cause the Swing Dispatch thread to display the JFrame
		
		// make the subscription
		sim.addObserver(road); // Allow the road panel to hear about simulation events
		sim.addObserver(this); // Allow this panel to hear about simulation events

	}

	/**
	 * This method will initialize simulation and start simulation.
	 */
	private void initSim() {
		// TODO Auto-generated method stub
		sim = new Simulation();
		new RandomLaunch(sim, this);
	}

	@Override
	public JPanel getMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(BorderLayout.NORTH, getNorthPanel());
//		mainPanel.add(BorderLayout.SOUTH, getSouthPanel());
		road = new Road();
		mainPanel.add(BorderLayout.CENTER, road);
		return mainPanel;
	}

	/**
	 * Components of North panel are included in northPanel and included in northPanel.
	 * In which Launch button will launch the Car with speed and on lane number which is selected from selection portion.
	 * Start Button will start the simulation. and Stop button will stop the simulation.
	 * Pause button will pause simulation and converted into Play, while in running mode it is alreade in Pause Button.
	 * Selection of Rule is included in combo box and in which 3 rules are there.
	 * 1. No rule applied => which will elevate the normal simulation.
	 * 2. Slow down rule => Which will elevate in slow speed simulation.
	 * 3. Speed Up rule => which will elevate in fast speed simulation.
	 * @return the north Panel.
	 */
	public JPanel getNorthPanel() {
		northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout());

		/**
		 * New Car Speed Selection.
		 */
		carSelection1 = new JRadioButton("Slow", true);
		JRadioButton carSelection2 = new JRadioButton("Medium");
		JRadioButton carSelection3 = new JRadioButton("Fast");

		ButtonGroup carRadioGroup = new ButtonGroup();
		carRadioGroup.add(carSelection1);
		carRadioGroup.add(carSelection2);
		carRadioGroup.add(carSelection3);

		Box carSelectionBox = Box.createHorizontalBox();
		carSelectionBox.add(carSelection1);
		carSelectionBox.add(carSelection2);
		carSelectionBox.add(carSelection3);

		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new GridLayout(1, 1));
		radioPanel.add(carSelectionBox);

		radioPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Car"));

		/**
		 * New Car launch lane selection.
		 */
		laneSelection1 = new JRadioButton("1", true);
		JRadioButton laneSelection2 = new JRadioButton("2");
		JRadioButton laneSelection3 = new JRadioButton("3");
		JRadioButton laneSelection4 = new JRadioButton("4");
		JRadioButton laneSelection5 = new JRadioButton("5");
		JRadioButton laneSelection6 = new JRadioButton("6");

		ButtonGroup laneRadioGroup = new ButtonGroup();
		laneRadioGroup.add(laneSelection1);
		laneRadioGroup.add(laneSelection2);
		laneRadioGroup.add(laneSelection3);
		laneRadioGroup.add(laneSelection4);
		laneRadioGroup.add(laneSelection5);
		laneRadioGroup.add(laneSelection6);

		Box upperLaneSelectionBox = Box.createHorizontalBox();
		upperLaneSelectionBox.add(laneSelection1);
		upperLaneSelectionBox.add(laneSelection2);
		upperLaneSelectionBox.add(laneSelection3);

		Box lowerLaneSelectionBox = Box.createHorizontalBox();
		lowerLaneSelectionBox.add(laneSelection4);
		lowerLaneSelectionBox.add(laneSelection5);
		lowerLaneSelectionBox.add(laneSelection6);

		

		startBtn = new JButton("Start");
		startBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resetSouthPanel("Start is preseed");
				
				sim.startSim();
				sim.setRunning(true); // force this on early, because we're about to reset the buttons
				resetButtons();
			}

		});

		pauseBtn = new JButton("Pause");
		pauseBtn.setEnabled(false); // Disable until 'start' has been pressed
		pauseBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				sim.pauseSim();
				resetButtons();
				if(pausePressed == false) {
					pausePressed = true;
				}
				else {
					pausePressed = false;
				}
				if(pauseBtn.getText() == "Pause") {
					resetSouthPanel("Pause is preseed");
					pauseBtn.setText("Play");
				}
				else {resetSouthPanel("Play is preseed");
					pauseBtn.setText("Pause");
				}
			}

		});

		stopBtn = new JButton("Stop");
		stopBtn.setEnabled(false); // Disable until 'start' has been pressed
		stopBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resetSouthPanel("Stop is preseed, Return to initial conditions.");
				setInitialOnStop();
				sim.stopSim();
				sim.setRunning(false); // Force this off early, because we're about to reset the buttons
				resetButtons();
			}

		});

		launchButton = new JButton("Launch");
		launchButton.addActionListener(new ActionListener() {
			
			/**
			 * Here it will check the launch car lane and launch car type and then
			 * add car to the list of vehicles and launch the car in simulation.
			 * It will check if there is already a car then it will notify user about this.
			 */

			@Override
			public void actionPerformed(ActionEvent e) {

				if (carSelection1.isSelected()) {
					launchSelectedCar = "Slow";
				} else if (carSelection2.isSelected()) {
					launchSelectedCar = "Medium";
				} else if (carSelection3.isSelected()) {
					launchSelectedCar = "Fast";
				}

				if (laneSelection1.isSelected()) {
					launchSelectedLane = 1;
				} else if (laneSelection2.isSelected()) {
					launchSelectedLane = 2;
				} else if (laneSelection3.isSelected()) {
					launchSelectedLane = 3;
				} else if (laneSelection4.isSelected()) {
					launchSelectedLane = 4;
				} else if (laneSelection5.isSelected()) {
					launchSelectedLane = 5;
				} else if (laneSelection6.isSelected()) {
					launchSelectedLane = 6;
				}

				if (launchSelectedLane <= 3) {
					if (launchSelectedCar == "Slow") {
						int speed = 3;
						boolean launchCheck = false;

						for (Vehicle vehicle : sim.vehicleList) {
							if (vehicle.getCurrLane() == launchSelectedLane) {
								if (vehicle.getX() > 130) {
									launchCheck = true;
								} else {
									launchCheck = false;
									System.out.println("Already a vehicle.");
									resetSouthPanel("No Launch, Already Vehicle in Lane "+launchSelectedLane);
								}
							}
						}

						if (launchCheck == true) {
							resetSouthPanel("Car Launch in "+launchSelectedLane);
							Vehicle v = new Vehicle(0, 130, speed, 120, 40, launchSelectedLane);
							sim.addCar(v);
						}
					} else if (launchSelectedCar == "Medium") {
						int speed = 7;
						boolean launchCheck = false;

						for (Vehicle vehicle : sim.vehicleList) {
							if (vehicle.getCurrLane() == launchSelectedLane) {
								if (vehicle.getX() > 130) {
									launchCheck = true;
								} else {
									launchCheck = false;
									resetSouthPanel("No Launch, Already Vehicle in Lane "+launchSelectedLane);
								}
							}
						}

						if (launchCheck == true) {
							resetSouthPanel("Car Launch in "+launchSelectedLane);
							Vehicle v = new Vehicle(0, 130, speed, 120, 40, launchSelectedLane);
							sim.addCar(v);
						}
					} else if (launchSelectedCar == "Fast") {
						int speed = 10;
						boolean launchCheck = false;

						for (Vehicle vehicle : sim.vehicleList) {
							if (vehicle.getCurrLane() == launchSelectedLane) {
								if (vehicle.getX() > 130) {
									launchCheck = true;
								} else {
									launchCheck = false;
									resetSouthPanel("No Launch, Already Vehicle in Lane "+launchSelectedLane);
								}
							}
						}

						if (launchCheck == true) {
							resetSouthPanel("Car Launch in "+launchSelectedLane);
							Vehicle v = new Vehicle(0, 130, speed, 120, 40, launchSelectedLane);
							sim.addCar(v);
						}
					}
				}
				else if (launchSelectedLane > 3) {
					if (launchSelectedCar == "Slow") {
						int speed = 3;
						boolean launchCheck = false;

						for (Vehicle vehicle : sim.vehicleList) {
							if (vehicle.getCurrLane() == launchSelectedLane) {
								if (vehicle.getX() < 1380) {
									launchCheck = true;
								} else {
									launchCheck = false;
									resetSouthPanel("No Launch, Already Vehicle in Lane "+launchSelectedLane);
								}
							}
						}

						if (launchCheck == true) {
							resetSouthPanel("Car Launch in "+launchSelectedLane);
							Vehicle v = new Vehicle(0, 1380, speed, 120, 40, launchSelectedLane);
							sim.addCar(v);
						}
					} else if (launchSelectedCar == "Medium") {
						int speed = 7;
						boolean launchCheck = false;

						for (Vehicle vehicle : sim.vehicleList) {
							if (vehicle.getCurrLane() == launchSelectedLane) {
								if (vehicle.getX() < 1380) {
									launchCheck = true;
								} else {
									launchCheck = false;
									resetSouthPanel("No Launch, Already Vehicle in Lane "+launchSelectedLane);
								}
							}
						}

						if (launchCheck == true) {
							resetSouthPanel("Car Launch in "+launchSelectedLane);
							Vehicle v = new Vehicle(0, 1380, speed, 120, 40, launchSelectedLane);
							sim.addCar(v);
						}
					} else if (launchSelectedCar == "Fast") {
						int speed = 10;
						boolean launchCheck = false;

						for (Vehicle vehicle : sim.vehicleList) {
							if (vehicle.getCurrLane() == launchSelectedLane) {
								if (vehicle.getX() < 1380) {
									launchCheck = true;
								} else {
									launchCheck = false;
									resetSouthPanel("No Launch, Already Vehicle in Lane "+launchSelectedLane);
								}
							}
						}

						if (launchCheck == true) {
							resetSouthPanel("Car Launch in "+launchSelectedLane);
							Vehicle v = new Vehicle(0, 1380, speed, 120, 40, launchSelectedLane);
							sim.addCar(v);
						}
					}
				}
			}
			
		});

		JPanel lanePanel = new JPanel();
		lanePanel.setLayout(new GridLayout(2, 1));
		lanePanel.add(upperLaneSelectionBox);
		lanePanel.add(lowerLaneSelectionBox);

		lanePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Lane"));
		
		JLabel ruleLbl = new JLabel("Rule:");
		ruleComboBox = new JComboBox<String>();
		ruleComboBox.addItem("No Rule"); 
		ruleComboBox.addItem("Slow Down Rule"); 
		ruleComboBox.addItem("Speed Up Rule");
		
		ruleComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRuleIndex = ruleComboBox.getSelectedIndex();
				switch (selectedRuleIndex) {
				case 1:
					sim.slowSpeedRule();
					resetSouthPanel(1);
					break;
				case 2:
					sim.speedUpRule();
					resetSouthPanel(2);
					break;
				case 0:
					sim.noRuleApply();
					resetSouthPanel(3);
					break;
				}
			}
		});
		
		southTextField = new JTextField(40);

		southTextField.setEditable(false);
		southTextField.setText(southTextFieldText);
		southTextField.setText("No rule applied");

		// Add everything to the north panel
		northPanel.add(radioPanel);
		northPanel.add(lanePanel);
		northPanel.add(launchButton);

		northPanel.add(startBtn);
		northPanel.add(pauseBtn);
		northPanel.add(stopBtn);
		

		northPanel.add(ruleLbl);
		northPanel.add(ruleComboBox);
		northPanel.add(southTextField);

		return northPanel;
	}

	/**
	 * This method is to reset buttons while in simulation is running.
	 */
	private void resetButtons() {
		if (sim == null)
			return;

		ruleComboBox.setEnabled(sim.isRunning());

		startBtn.setEnabled(!sim.isRunning());
		pauseBtn.setEnabled(sim.isPausable());
		stopBtn.setEnabled(sim.isRunning());
	}

	/**
	 * This method will use to re write the south panel contents as per the rule selection.
	 * @param n
	 */
	private void resetSouthPanel(int n) {
		if (n == 1) {
			southTextField.setText("Slow Down Rule Applied");
		} else if (n == 2) {
			southTextField.setText("Speed up Rule Applied");
		} else {
			southTextField.setText("No Rule Applied");
		}
	}
	
	/**
	 * This panel will reset the text of text field which is in north panel.
	 * @param s is String which will be set for text field.
	 */
	public void resetSouthPanel(String s) {
		southTextField.setText(s);
	}
	
	/**
	 * This function is to set to initial selections when stop pressed.
	 */
	public void setInitialOnStop() {
		launchSelectedLane = 1;
		launchSelectedCar = "Slow";
		southTextFieldText = "No rule Applied";
		ruleComboBox.setSelectedIndex(0);
		carSelection1.setSelected(true);
		laneSelection1.setSelected(true);
		pauseBtn.setText("Pause");
	}

	public static void main(String[] args) {
		MyAppUI myApp = new MyAppUI();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		frame.repaint();
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
