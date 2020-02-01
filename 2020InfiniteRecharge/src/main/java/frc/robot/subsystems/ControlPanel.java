package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

public class ControlPanel extends SubsystemBase {
    private static final Color BLUE_TARGET_COLOR = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private static final Color GREEN_TARGET_COLOR = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private static final Color RED_TARGET_COLOR = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private static final Color YELLOW_TARGET_COLOR = ColorMatch.makeColor(0.361, 0.524, 0.113);

    /**
     * Creates a new ControlPanel.
     */

    public enum PanelColor {
         yellow, blue, red, green, unknown
    }

    private final TalonSRX m_controlPanel; // NOPMD
    //private final CANEncoder m_controlPanelEncoder;

    private final ColorSensorV3 m_colorSensor;
    private final ColorMatch m_colorMatcher;

    private int m_colorCounter;

    private PanelColor m_currentPanelColor; 
    private PanelColor m_lastPanelColor; 

    private Color m_currentColor = null;


    public ControlPanel() {

        m_colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
        m_colorMatcher = new ColorMatch();

        m_controlPanel = new TalonSRX(Constants.CONTROL_PANEL_TALON);
        //m_controlPanelEncoder = m_controlPanel.getEncoder();

        m_colorCounter = 0;

        m_colorMatcher.addColorMatch(BLUE_TARGET_COLOR);
        m_colorMatcher.addColorMatch(GREEN_TARGET_COLOR);
        m_colorMatcher.addColorMatch(RED_TARGET_COLOR);
        m_colorMatcher.addColorMatch(YELLOW_TARGET_COLOR);
        
        System.out.println("ControlPanel"); 

    }

    @Override
    public void periodic() {
    
        m_currentColor = m_colorSensor.getColor();
     
        final double IR = m_colorSensor.getIR();
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
 
        if(m_currentPanelColor != m_lastPanelColor){
            m_colorCounter++; 
            m_lastPanelColor = m_currentPanelColor; 
        }

        SmartDashboard.putNumber("Red ", m_currentColor.red);
        SmartDashboard.putNumber("Green ", m_currentColor.green);
        SmartDashboard.putNumber("Blue ", m_currentColor.blue);
        SmartDashboard.putNumber("IR ", IR);
        SmartDashboard.putNumber("Confidence", match.confidence);
        SmartDashboard.putString("Assigned Color", m_currentPanelColor.toString());
        SmartDashboard.putNumber("Proximity", m_colorSensor.getProximity());
    }


    public int getColorCounter(){
        return m_colorCounter;
    }

    // public double getControlPanelEncoder() {
    //     return m_controlPanelEncoder.getPosition();
    // }

    public PanelColor getCurrentColor() {
        return m_currentPanelColor;
    }

    public void start(){
        m_controlPanel.set(ControlMode.PercentOutput, 0.5);
    }

    public void stop() {
        m_controlPanel.set(ControlMode.PercentOutput, 0);
    }
}
