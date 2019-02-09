/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

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
  public static final double CLIMBER_INCREMENT = 50.0;

  public static final double FIRST_GOAL_POS = 0.0; //TOOD; adjust this value
  public static final double SECOND_GOAL_POS = 150.0; //TODO; adjust this value
  public static final double THIRD_GOAL_POS = 200.0; //TODO; adjust this value 


  private double goalClimberPosition;

  public Climber() {
    climberFront = new WPI_TalonSRX(RobotMap.CLIMBER_FRONT_TALON);
    climberBack = new WPI_TalonSRX(RobotMap.CLIMBER_BACK_TALON);

    climberFront.setSensorPhase(false);
    climberBack.setSensorPhase(false);

    climberFront.config_kF(0, 0, 10);
    climberFront.config_kP(0, 1.5, 10);
    climberFront.config_kI(0, 0, 10);
    climberFront.config_kD(0, 15, 10);

    climberBack.config_kF(0, 0, 10);
    climberBack.config_kP(0, 1.5, 10);
    climberBack.config_kI(0, 0, 10);
    climberBack.config_kD(0, 15, 10);

    climberFront.configForwardLimitSwitchSource(LimitSwitchSource.RemoteTalonSRX, LimitSwitchNormal.NormallyOpen,
        RobotMap.DRIVE_LEFT_MASTER_TALON);
    climberBack.configForwardLimitSwitchSource(LimitSwitchSource.RemoteTalonSRX, LimitSwitchNormal.NormallyOpen,
        RobotMap.DRIVE_RIGHT_MASTER_TALON);
  }

  // the value in set expiration is in SECONDS not milliseconds
  public void setClimberPosition(double pos) {
    climberFront.set(ControlMode.Position, pos);
    climberBack.set(ControlMode.Position, pos);
  }

  public void setClimberSpeed(double speed) {
    climberFront.set(speed);
    climberBack.set(speed);
  }

  public void climberStop() {
    climberFront.stopMotor();
    climberBack.stopMotor();
  }

  public double getGoalClimberPosition() {
    return goalClimberPosition;
  }

  public void setGoalCLimberPosition(double goal) {
    goalClimberPosition = goal;
    System.out.println("Goal Climber Position set to: " + goal);
  }

  public double getFrontPosition() {
    return climberFront.getSelectedSensorPosition(0);
  }

  public double getBackPosition() {
    return climberBack.getSelectedSensorPosition(0);
  }

  public void holdClimberPosition() {
    climberFront.set(ControlMode.Position, goalClimberPosition);
    climberBack.set(ControlMode.Position, goalClimberPosition);
  }

  public void holdClimberFrontPosition() {
    climberFront.set(ControlMode.Position, goalClimberPosition);
  }

  public void holdClimberBackPosition() {
    climberBack.set(ControlMode.Position, goalClimberPosition);
  }

  public void setClimberUp() {
    goalClimberPosition = CLIMBER_UP;
  }

  public void setClimberDown() {
    goalClimberPosition = CLIMBER_DOWN;
  }

  public void incrementClimber() {
    goalClimberPosition += CLIMBER_INCREMENT;
  }

  public void decrementClimber() {
    goalClimberPosition -= CLIMBER_INCREMENT;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
