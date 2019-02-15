/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// negative encoder ticks is down
package frc.robot.subsystems;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
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

  public static final double CLIMBER_UP = 1500.0;
  public static final double CLIMBER_DOWN = 0.0;
  public static final double CLIMBER_INCREMENT = -150;

  public static final double CLIMBER_TOLERANCE = 100;

  public static final double FRONT_POSITION = 0; 
  public static final double BACK_POSITION = 0;

  public static final double FIRST_GOAL_POS = 0.0; //TOOD; adjust this value
  public static final double SECOND_GOAL_POS = 150.0; //TODO; adjust this value
  public static final double THIRD_GOAL_POS = 200.0; //TODO; adjust this value 


  public double goalFrontPosition;
  public double goalBackPosition;

  public Climber() {
    climberFront = new WPI_TalonSRX(RobotMap.CLIMBER_FRONT_TALON);
    climberBack = new WPI_TalonSRX(RobotMap.CLIMBER_BACK_TALON);

    climberFront.setSensorPhase(true);
    climberBack.setSensorPhase(true);

    climberFront.config_kF(0, 0, 10);
    climberFront.config_kP(0, 1.5, 10);
    climberFront.config_kI(0, 0, 10);
    climberFront.config_kD(0, 15, 10);

    climberBack.config_kF(0, 0, 10);
    climberBack.config_kP(0, 1.5, 10);
    climberBack.config_kI(0, 0, 10);
    climberBack.config_kD(0, 15, 10);

    climberFront.setNeutralMode(NeutralMode.Brake);
    climberBack.setNeutralMode(NeutralMode.Brake);

    // climberFront.configForwardLimitSwitchSource(LimitSwitchSource.RemoteTalonSRX, LimitSwitchNormal.NormallyOpen,
    //     RobotMap.DRIVE_LEFT_MASTER_TALON);
    // climberBack.configForwardLimitSwitchSource(LimitSwitchSource.RemoteTalonSRX, LimitSwitchNormal.NormallyOpen,
    //     RobotMap.DRIVE_RIGHT_MASTER_TALON);

    climberFront.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen,
      RobotMap.DRIVE_LEFT_MASTER_TALON);
    climberBack.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen,
       RobotMap.DRIVE_RIGHT_MASTER_TALON);
  }

  // the value in set expiration is in SECONDS not milliseconds
  public void setGoalClimberPosition(double pos) {
    climberFront.set(ControlMode.Position, pos);
    climberBack.set(ControlMode.Position, pos);
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
    boolean isFinished = (goalFrontPos <= getFrontPosition() + CLIMBER_TOLERANCE && goalFrontPos >= getFrontPosition()- CLIMBER_TOLERANCE);
    System.out.println("climber front positon check isFinished " + isFinished);
    return isFinished;
  }

  public boolean checkCurrentBackPosition(double goalBackPos){
    boolean isFinished = (goalBackPos <= getBackPosition() + CLIMBER_TOLERANCE && goalBackPos >= getBackPosition() - CLIMBER_TOLERANCE);
    System.out.println("climber back position check isFinished " + isFinished);
    return isFinished;
  }

  public boolean checkCurrentPosition(double goalPos){
    boolean isFinished = (goalPos <= getFrontPosition() + 500 
      && goalPos  >= getFrontPosition() - CLIMBER_TOLERANCE)
      && (goalPos  <= getBackPosition() + CLIMBER_TOLERANCE 
      && goalPos >= getBackPosition() - CLIMBER_TOLERANCE);
    System.out.println("climber isFinished: " + isFinished);
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
