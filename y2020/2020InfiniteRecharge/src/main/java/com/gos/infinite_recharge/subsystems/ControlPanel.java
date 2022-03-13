package com.gos.infinite_recharge.subsystems;

import com.gos.infinite_recharge.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

public class ControlPanel extends SubsystemBase {
    private static final Color BLUE_TARGET_COLOR = new Color(0.143, 0.427, 0.429);
    private static final Color GREEN_TARGET_COLOR = new Color(0.197, 0.561, 0.240);
    private static final Color RED_TARGET_COLOR = new Color(0.561, 0.232, 0.114);
    private static final Color YELLOW_TARGET_COLOR = new Color(0.361, 0.524, 0.113);

    private final NetworkTable m_customNetworkTable;
    /**
     * Creates a new ControlPanel.
     */

    public enum PanelColor {
        yellow, blue, red, green, unknown
    }

    private final WPI_TalonSRX m_controlPanel; // NOPMD
    // private final RelativeEncoder m_controlPanelEncoder;

    private final ColorSensorV3 m_colorSensor;
    private final ColorMatch m_colorMatcher;

    private int m_colorCounter;

    private PanelColor m_currentPanelColor;
    private PanelColor m_lastPanelColor;

    private Color m_currentColor;

    public ControlPanel() {

        m_colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
        m_colorMatcher = new ColorMatch();

        m_controlPanel = new WPI_TalonSRX(Constants.CONTROL_PANEL_TALON);
        m_controlPanel.configFactoryDefault();
        // m_controlPanelEncoder = m_controlPanel.getEncoder();

        m_colorCounter = 0;

        m_colorMatcher.addColorMatch(BLUE_TARGET_COLOR);
        m_colorMatcher.addColorMatch(GREEN_TARGET_COLOR);
        m_colorMatcher.addColorMatch(RED_TARGET_COLOR);
        m_colorMatcher.addColorMatch(YELLOW_TARGET_COLOR);

        System.out.println("ControlPanel");

        m_customNetworkTable = NetworkTableInstance.getDefault().getTable("SuperStructure/Winch");

    }

    @Override
    public void periodic() {

        m_currentColor = m_colorSensor.getColor();

        final ColorMatchResult match = m_colorMatcher.matchClosestColor(m_currentColor);

        if (match.color == BLUE_TARGET_COLOR) {
            m_currentPanelColor = PanelColor.blue;
        } else if (match.color == RED_TARGET_COLOR) {
            m_currentPanelColor = PanelColor.red;
        } else if (match.color == GREEN_TARGET_COLOR) {
            m_currentPanelColor = PanelColor.green;
        } else if (match.color == YELLOW_TARGET_COLOR) {
            m_currentPanelColor = PanelColor.yellow;
        } else {
            m_currentPanelColor = PanelColor.unknown;
        }

        if (m_currentPanelColor != m_lastPanelColor) {
            m_colorCounter++;
            m_lastPanelColor = m_currentPanelColor;
        }

        //essentially saying that if the last color for each color is way off from what it should be, to decrease the color counter by 1
        //not sure if this is right tho
        // if (m_currentPanelColor == PanelColor.yellow && m_lastPanelColor == PanelColor.red || m_currentPanelColor == PanelColor.yellow && m_lastPanelColor == PanelColor.green) {
        //     m_colorCounter--;
        // }
        // else if (m_currentPanelColor == PanelColor.red && m_lastPanelColor == PanelColor.green || m_currentPanelColor == PanelColor.red && m_lastPanelColor == PanelColor.blue) {
        //     m_colorCounter--;
        // }

        // else if (m_currentPanelColor == PanelColor.green && m_lastPanelColor == PanelColor.yellow || m_currentPanelColor == PanelColor.green && m_lastPanelColor == PanelColor.blue) {
        //     m_colorCounter--;
        // }

        // else if (m_currentPanelColor == PanelColor.blue && m_lastPanelColor == PanelColor.red || m_currentPanelColor == PanelColor.blue && m_lastPanelColor == PanelColor.yellow) {
        //     m_colorCounter--;
        // }

        // SmartDashboard.putNumber("Red ", m_currentColor.red);
        // SmartDashboard.putNumber("Green ", m_currentColor.green);
        // SmartDashboard.putNumber("Blue ", m_currentColor.blue);
        // SmartDashboard.putNumber("IR ", IR);
        // SmartDashboard.putNumber("Confidence", match.confidence);
        // SmartDashboard.putString("Assigned Color", m_currentPanelColor.toString());
        // SmartDashboard.putNumber("Proximity", m_colorSensor.getProximity());

        m_customNetworkTable.getEntry("Speed").setDouble(m_controlPanel.get());
    }

    public int getColorCounter() {
        return m_colorCounter;
    }

    // public double getControlPanelEncoder() {
    // return m_controlPanelEncoder.getPosition();
    // }

    public PanelColor getCurrentColor() {
        return m_currentPanelColor;
    }

    public void start() {
        m_controlPanel.set(ControlMode.PercentOutput, 0.5);
    }

    public void stop() {
        m_controlPanel.set(ControlMode.PercentOutput, 0);
    }
}
