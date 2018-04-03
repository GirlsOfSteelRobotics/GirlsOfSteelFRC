package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * This class manages Game Data obtained from the field management system as
 * well as the related autonomous selection mechanisms on the robot (DIO ports).
 */
public class GameData {

	/**
	 * Possible starting positions for both the robot and the switch and scale
	 * assignments. The BAD entry is used to indicate a problem with querying
	 * the switch and scale information from the game data API.
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

	private String gameData = null;

	/**
	 * Prepare a place to record the Game Data string and initialize the DIO
	 * ports used as autonomous selectors.
	 */
	public GameData() {
		dioPriority = new DigitalInput(RobotMap.DIO_PRIORITY);
	}

	/**
	 * Which field element do we prioritize when selecting an autonomous routine?
	 */
	public FieldElement getElementPriority() {
		if (!dioPriority.get()) {
			return FieldElement.Switch; //TODO: Fix me
		} else {
			return FieldElement.Switch;
		}
	}

	/**
	 * Which side of the near scale has our alliance color?
	 * 
	 * @return The FieldSide enum for our near scale position (left or right)
	 */
	public FieldSide getScaleSide() {
		// If we haven't retrieved the game data string, go get it now
		if (gameData != null) {
			gameData = getGameData();
		}
		// Parse the string and return the enumerator
		return FieldSide.bad;
	}

	/**
	 * Ask the field management system for the Game Data string. If the call
	 * doesn't work the first time, retry several times for up to XXX seconds.
	 * 
	 * @return the Game Data string
	 */
	private String getGameData() {
		gameData = "LLL";
		System.out.println("Raw GameData: " + gameData);
		return gameData;
	}
}
