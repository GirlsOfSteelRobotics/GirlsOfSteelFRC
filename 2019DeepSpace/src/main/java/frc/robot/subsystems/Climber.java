/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// NEGATIVE ENCODER TICKS IS BALLSCREW DOWN, ROBOT UP
package frc.robot.subsystems;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteLimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * Add your docs here.
 */

// commented out screwback for testing purposes
public class Climber extends Subsystem {
  // DigitalInput limitSwitch = new DigitalInput(1);
  // SpeedController armMotor = new Victor(1);
  // Counter counter = new Counter(limitSwitch);
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private WPI_TalonSRX climberFront;
  private WPI_TalonSRX climberBack;

  public static final double CLIMBER_INCREMENT = 1000;

  public static final double CLIMBER_TOLERANCE = 100;

  public static final double FRONT_POSITION = 0; 
  public static final double BACK_POSITION = 0;

  public static final double FIRST_GOAL_POS = 0.0; // Robot is powered on in fully retracted state
  public static final double SECOND_GOAL_POS = -33000.0; //TODO; first estimate from quick measurement
  public static final double THIRD_GOAL_POS = -78000.0; //TODO; first estimate 
  //THIRD_GOAL_POS should be around -83000
  public static final double ALL_TO_ZERO = 0.0;

  public double goalFrontPosition;
  public double goalBackPosition;

  public Climber() {
    climberFront = new WPI_TalonSRX(RobotMap.CLIMBER_FRONT_TALON);
    climberBack = new WPI_TalonSRX(RobotMap.CLIMBER_BACK_TALON);

    climberFront.setSensorPhase(true);
    climberBack.setSensorPhase(true);

    climberFront.config_kF(0, 0, 10);
    climberFront.config_kP(0, 1.0, 10);
    climberFront.config_kI(0, 0, 10);
    climberFront.config_kD(0, 0, 10);

    climberBack.config_kF(0, 0, 10);
    climberBack.config_kP(0, 0.85, 10);
    climberBack.config_kI(0, 0, 10);
    climberBack.config_kD(0, 0, 10);

    climberFront.setNeutralMode(NeutralMode.Brake);
    climberBack.setNeutralMode(NeutralMode.Brake);

    // Limit Switches On
    climberFront.configReverseLimitSwitchSource(RemoteLimitSwitchSource.RemoteTalonSRX, LimitSwitchNormal.NormallyClosed,
        RobotMap.DRIVE_LEFT_MASTER_TALON);
    climberBack.configReverseLimitSwitchSource(RemoteLimitSwitchSource.RemoteTalonSRX, LimitSwitchNormal.NormallyClosed,
        RobotMap.DRIVE_RIGHT_MASTER_TALON);

    // Limit Switches Off
    // climberFront.configReverseLimitSwitchSource(RemoteLimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyClosed,
    //   RobotMap.DRIVE_LEFT_MASTER_TALON);
    // climberBack.configReverseLimitSwitchSource(RemoteLimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyClosed,
    //    RobotMap.DRIVE_RIGHT_MASTER_TALON);
  }

  // the value in set expiration is in SECONDS not milliseconds
  public void setGoalClimberPosition(double pos) {
    goalFrontPosition = pos;
    goalBackPosition = pos;
  }

  public void climberStop() {
    climberFront.stopMotor();
    climberBack.stopMotor();
  }

  public double getFrontPosition() {
    return climberFront.getSelectedSensorPosition(0);
  }

  public double getBackPosition() {
    return climberBack.getSelectedSensorPosition(0);
  }

  public boolean checkCurrentFrontPosition(double goalFrontPos){
    boolean isFinished = (goalFrontPos + CLIMBER_TOLERANCE >= getFrontPosition()  && goalFrontPos - CLIMBER_TOLERANCE <= getFrontPosition());
    //System.out.println("climber front positon check isFinished " + isFinished);
    return isFinished;
  }

  public boolean checkCurrentBackPosition(double goalBackPos){
    boolean isFinished = (goalBackPos + CLIMBER_TOLERANCE >= getBackPosition() && goalBackPos - CLIMBER_TOLERANCE <= getBackPosition());
    //System.out.println("climber back position check isFinished " + isFinished);
    return isFinished;
  }

  public boolean checkCurrentPosition(double goalPos){
    boolean isFinished = (goalPos + CLIMBER_TOLERANCE >= getFrontPosition()  
      && goalPos - CLIMBER_TOLERANCE  <= getFrontPosition() )
      && (goalPos + CLIMBER_TOLERANCE >= getBackPosition()  
      && goalPos - CLIMBER_TOLERANCE <= getBackPosition());
    // System.out.println("climber isFinished: " + isFinished);
    // System.out.println("front upper: " + ((goalPos + CLIMBER_TOLERANCE) >= getFrontPosition()));
    // System.out.println("front lower: " + ((goalPos - CLIMBER_TOLERANCE)  <= getFrontPosition()));
    // System.out.println("down upper: " + ((goalPos + CLIMBER_TOLERANCE) >= getBackPosition()));
    // System.out.println("down lower: " + ((goalPos - CLIMBER_TOLERANCE) <= getBackPosition()));

    return isFinished;
  }

  public void holdClimberPosition() {
    holdClimberFrontPosition();
    holdClimberBackPosition();
  }

  public void holdClimberFrontPosition() {
    climberFront.set(ControlMode.Position, goalFrontPosition);
  }

  public void holdClimberBackPosition() {
    climberBack.set(ControlMode.Position, goalBackPosition);
  }

  public void holdClimberAllPosition(){
    climberBack.set(ControlMode.Position, goalBackPosition);
    climberFront.set(ControlMode.Position, goalFrontPosition);
  }
  
  public void incrementFrontClimber() {
    goalFrontPosition = getFrontPosition(); 
    goalFrontPosition += CLIMBER_INCREMENT;
  }

  public void decrementFrontClimber() {
    goalFrontPosition = getFrontPosition(); 
    goalFrontPosition -= CLIMBER_INCREMENT;
  }
  public void incrementBackClimber() {
    goalBackPosition = getBackPosition(); 
    goalBackPosition += CLIMBER_INCREMENT;
  }

  public void decrementBackClimber() {
    goalBackPosition = getBackPosition(); 
    goalBackPosition -= CLIMBER_INCREMENT;
  }
  public void incrementAllClimber() {
    incrementFrontClimber();
    incrementBackClimber();
  }

  public void decrementAllClimber() {
    decrementFrontClimber();
    decrementBackClimber();
  }

  @Override
  public void initDefaultCommand() {
    
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
