package com.gos.power_up;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 * This class manages Game Data obtained from the field management system as
 * well as the related autonomous selection mechanisms on the robot (DIO ports).
 */
@SuppressWarnings("PMD.NullAssignment")
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
    private final DigitalInput m_dioPriority;
    private final DigitalInput m_dioLeft;
    private final DigitalInput m_dioMiddle;
    private final DigitalInput m_dioRight;
    private final DigitalInput m_dioScaleOverride;
    private final DigitalInput m_dioNoAuto;

    private String m_gameData;

    /**
     * Prepare a place to record the Game Data string and initialize the DIO ports
     * used as autonomous selectors.
     */
    public GameData() {
        m_dioPriority = new DigitalInput(RobotMap.DIO_PRIORITY);
        m_dioLeft = new DigitalInput(RobotMap.DIO_LEFT);
        m_dioMiddle = new DigitalInput(RobotMap.DIO_MIDDLE);
        m_dioRight = new DigitalInput(RobotMap.DIO_RIGHT);
        m_dioScaleOverride = new DigitalInput(RobotMap.DIO_SCALE_OVERRIDE);
        m_dioNoAuto = new DigitalInput(RobotMap.DIO_NO_AUTO);
    }

    /**
     * Which field element do we prioritize when selecting an autonomous routine?
     */
    public FieldElement getElementPriority() {
        if (!m_dioPriority.get()) {
            return FieldElement.Scale;
        } else {
            return FieldElement.Switch;
        }
    }

    /**
     * returns what side the robot is on
     *
     * @return Field side
     */
    public FieldSide getRobotSide() {
        if (!m_dioLeft.get()) {
            return FieldSide.left;
        } else if (!m_dioMiddle.get()) {
            return FieldSide.middle;
        } else if (!m_dioRight.get()) {
            return FieldSide.right;
        } else {
            return FieldSide.bad;
        }
    }

    public boolean getNoAuto() {
        return !m_dioNoAuto.get();
    }

    public boolean getScaleOverride() {
        return !m_dioScaleOverride.get();
    }

    public void reset() {
        m_gameData = null;
    }

    /**
     * Which side of the near scale has our alliance color?
     *
     * @return The FieldSide enum for our near scale position (left or right)
     */
    public FieldSide getScaleSide() {
        // If we haven't retrieved the game data string, go get it now
        if (m_gameData == null) {
            m_gameData = getGameData();
        }
        // Parse the string and return the enumerator

        if (m_gameData != null && m_gameData.length() >= 2) {
            if (m_gameData.charAt(1) == 'L') {
                return FieldSide.left;
            } else if (m_gameData.charAt(1) == 'R') {
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
        if (m_gameData == null) {
            m_gameData = getGameData();
        }
        // Parse the string and return the enumerator
        if (m_gameData != null && m_gameData.length() >= 1) {
            if (m_gameData.charAt(0) == 'L') {
                return FieldSide.left;
            } else if (m_gameData.charAt(0) == 'R') {
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
        m_gameData = DriverStation.getGameSpecificMessage();

        while (tim <= 5 && (m_gameData == null || "".equals(m_gameData))) {
            m_gameData = DriverStation.getGameSpecificMessage();
            Timer.delay(0.2);
            tim++;
        }

        if ("".equals(m_gameData)) {
            m_gameData = null;
        }

        System.out.println("Raw GameData: " + m_gameData);
        return m_gameData;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder(30);

        if (getSwitchSide() == GameData.FieldSide.left) {
            output.append("Switch is on the left");
        } else if (getSwitchSide() == GameData.FieldSide.right) {
            output.append("Switch is on the right");
        } else if (getSwitchSide() == GameData.FieldSide.bad) {
            output.append("Switch is BAD");
        }

        if (getScaleSide() == GameData.FieldSide.left) {
            output.append("Scale is on the left");
        } else if (getScaleSide() == GameData.FieldSide.right) {
            output.append("Scale is on the right");
        } else if (getScaleSide() == GameData.FieldSide.bad) {
            output.append("Scale is BAD");
        }

        return output.toString();
    }
}
