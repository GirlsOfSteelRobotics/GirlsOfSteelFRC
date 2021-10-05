package com.gos.power_up;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 * This class manages Game Data obtained from the field management system as
 * well as the related autonomous selection mechanisms on the robot (DIO ports).
 */
public class GameData {

	/**
	 * Possible starting positions for both the robot and the switch and scale
	 * assignments. The BAD entry is used to indicate a problem with querying the
	 * switch and scale information from the game data API.
	 */
	public enum FieldSide {
		left, right, middle, bad
	}

	/**
	 * Choice of scoring priorities when selecting an autonomous routine.
	 */
	public enum FieldElement {
		Switch, Scale
	}

	// DIO ports used to select autonomous behaviors
	public DigitalInput dioPriority;
	public DigitalInput dioLeft;
	public DigitalInput dioMiddle;
	public DigitalInput dioRight;
	public DigitalInput dioScaleOverride;
	public DigitalInput dioNoAuto;

	private String gameData = null;

	/**
	 * Prepare a place to record the Game Data string and initialize the DIO ports
	 * used as autonomous selectors.
	 */
	public GameData() {
		dioPriority = new DigitalInput(RobotMap.DIO_PRIORITY);
		dioLeft = new DigitalInput(RobotMap.DIO_LEFT);
		dioMiddle = new DigitalInput(RobotMap.DIO_MIDDLE);
		dioRight = new DigitalInput(RobotMap.DIO_RIGHT);
		dioScaleOverride = new DigitalInput(RobotMap.DIO_SCALE_OVERRIDE);
		dioNoAuto = new DigitalInput(RobotMap.DIO_NO_AUTO);
	}

	/**
	 * Which field element do we prioritize when selecting an autonomous routine?
	 */
	public FieldElement getElementPriority() {
		if (!dioPriority.get()) {
			return FieldElement.Scale;
		} else {
			return FieldElement.Switch;
		}
	}

	/**
	 * returns what side the robot is on
	 * @return
	 */
	public FieldSide getRobotSide() {
		if (!dioLeft.get()) {
			return FieldSide.left;
		} else if (!dioMiddle.get()) {
			return FieldSide.middle;
		} else if (!dioRight.get()) {
			return FieldSide.right;
		} else {
			return FieldSide.bad;
		}
	}
	
	public boolean getNoAuto() {
		return !dioNoAuto.get();
	}

	public boolean getScaleOverride() {
		return !dioScaleOverride.get();
	}
	
	public void reset() {
		gameData = null;
	}
	/**
	 * Which side of the near scale has our alliance color?
	 * 
	 * @return The FieldSide enum for our near scale position (left or right)
	 */
	public FieldSide getScaleSide() {
		// If we haven't retrieved the game data string, go get it now
		if (gameData == null) {
			gameData = getGameData();
		}
		// Parse the string and return the enumerator

		if (gameData != null && gameData.length() >= 2) {
			if (gameData.charAt(1) == 'L') {
				return FieldSide.left;
			} else if (gameData.charAt(1) == 'R') {
				return FieldSide.right;
			}
		}
		return FieldSide.bad;
	}
	
	/**
	 * Which side of the near scale has our alliance color?
	 * 
	 * @return The FieldSide enum for our near scale position (left or right)
	 */
	public FieldSide getSwitchSide() {
		// If we haven't retrieved the game data string, go get it now
		if (gameData == null) {
			gameData = getGameData();
		}
		// Parse the string and return the enumerator
		if (gameData != null && gameData.length() >= 1) {
			if (gameData.charAt(0) == 'L') {
				return FieldSide.left;
			} else if (gameData.charAt(0) == 'R') {
				return FieldSide.right;
			}
		}
		return FieldSide.bad;
	}


	/**
	 * Ask the field management system for the Game Data string. If the call doesn't
	 * work the first time, retry several times for up to 1.0 second.
	 * 
	 * @return the Game Data string or null if Game Data API fails
	 */
	private String getGameData() {
		int tim = 0;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		while(tim <= 5 && (gameData == null || gameData.equals(""))) 
		{
			gameData = DriverStation.getInstance().getGameSpecificMessage();
			Timer.delay(0.2);
			tim++;
		}
		
		if (gameData.equals("")) {
			gameData = null;
		}
		
		System.out.println("Raw GameData: " + gameData);
		return gameData;
	}
}
