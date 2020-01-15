/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

public class ControlPanel extends SubsystemBase {
  /**
   * Creates a new ControlPanel.
   */

   public enum panelColor{
     yellow, blue, red, green, unknown
   }


   private panelColor currentColor;

  private final CANSparkMax controlPanel;
  private final CANEncoder controlPanelEncoder;

  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  private final ColorMatch m_colorMatcher = new ColorMatch();

  private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

  public ControlPanel() {


		controlPanel = new CANSparkMax(Constants.CONTROL_PANEL_SPARK, MotorType.kBrushed);
    controlPanelEncoder = controlPanel.getEncoder();

    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);
    
    System.out.println("ControlPanel"); 

  }

  @Override
  public void periodic() {

    Color detectedColor = m_colorSensor.getColor();

    double IR = m_colorSensor.getIR();

    String colorString;
    
    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

    if (match.color == kBlueTarget) {
      currentColor = panelColor.blue;
    } else if (match.color == kRedTarget) {
      currentColor = panelColor.red;
    } else if (match.color == kGreenTarget) {
      currentColor = panelColor.green;
    } else if (match.color == kYellowTarget) {
      currentColor = panelColor.yellow;
    } else {
      currentColor = panelColor.unknown;
    }

    SmartDashboard.putNumber("Red ", detectedColor.red);
    SmartDashboard.putNumber("Green ", detectedColor.green); 
    SmartDashboard.putNumber("Blue " ,detectedColor.blue); 
    SmartDashboard.putNumber("IR ", IR); 
    SmartDashboard.putNumber("Confidence", match.confidence); 
    SmartDashboard.putString("Detected Color", currentColor.toString());

     
    int proximity = m_colorSensor.getProximity();

    SmartDashboard.putNumber("Proximity", proximity); 


    // This method will be called once per scheduler run
  }

  public double getControlPanelEncoder(){
    return controlPanelEncoder.getPosition();
  }

  public panelColor getCurrentColor(){
    return currentColor;
  }
}
