/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// POSITIVE ENCODER TICKS IS BALLSCREW DOWN, ROBOT UP
package frc.robot.subsystems;

import frc.robot.RobotMap;
import frc.robot.commands.ClimberHold;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FollowerType;
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
  private WPI_TalonSRX followerClimberFront;

  private WPI_TalonSRX climberBack;
  private WPI_TalonSRX followerClimberBack;

  public static final double CLIMBER_EXTEND = 2000;

  public static final double CLIMBER_TOLERANCE = 200;

  public static final double FIRST_GOAL_POS = 0.0; // Robot is powered on in fully retracted state
  public static final double SECOND_GOAL_POS = 34000.0; // -30000 on Belka, -32000 on Laika
  public static final double THIRD_GOAL_POS = 84000.0; // -82000 on Belka, -84000 on Laika

  public static final double ALL_TO_ZERO = 0.0;

  public static enum ClimberType {
    All, Front, Back
  };

  public static final int MAX_CRUISE_VELOCITY = 3900; // Anna and Gracie 4/25
  public static final int MAX_ACCELERATION = 10000; // Anna and Gracie 4/25
  // 1500

  public double goalFrontPosition;
  public double goalBackPosition;

  private int dir;

  public Climber() {

    climberFront = new WPI_TalonSRX(RobotMap.CLIMBER_FRONT_TALON);
    followerClimberFront = new WPI_TalonSRX(RobotMap.CLIMBER_FRONT_FOLLOWER_TALON);

    climberBack = new WPI_TalonSRX(RobotMap.CLIMBER_BACK_TALON);
    followerClimberBack = new WPI_TalonSRX(RobotMap.CLIMBER_BACK_FOLLOWER_TALON);

    climberFront.setInverted(true); 
    followerClimberFront.setInverted(true); 
    climberBack.setInverted(true);
    followerClimberBack.setInverted(true);

    climberFront.setSensorPhase(true);
    climberBack.setSensorPhase(true);

    // PID
    climberFront.config_kF(0, 0.20, 10); // tuned by Ziya and Joe on 4/3
    climberFront.config_kP(0, 1.0, 10); // tuned by Ziya and Joe on 4/3
    climberFront.config_kI(0, 0, 10);
    climberFront.config_kD(0, 0, 10);

    climberBack.config_kF(0, 0.20, 10); // tuned by Ziya and Joe on 4/3
    climberBack.config_kP(0, 1.0, 10); // tuned by Ziya and Joe on 4/3
    climberBack.config_kI(0, 0, 10);
    climberBack.config_kD(0, 0, 10);

    // Motion Magic
    climberFront.configMotionAcceleration(MAX_ACCELERATION);
    climberFront.configMotionCruiseVelocity(MAX_CRUISE_VELOCITY);

    climberBack.configMotionAcceleration(MAX_ACCELERATION);
    climberBack.configMotionCruiseVelocity(MAX_CRUISE_VELOCITY);

    climberFront.setNeutralMode(NeutralMode.Brake);
    followerClimberFront.setNeutralMode(NeutralMode.Brake);

    climberBack.setNeutralMode(NeutralMode.Brake);
    followerClimberBack.setNeutralMode(NeutralMode.Brake);

    followerClimberFront.follow(climberFront, FollowerType.PercentOutput);
    followerClimberBack.follow(climberBack, FollowerType.PercentOutput);

    LiveWindow.addActuator("Climber", "climberFront", climberFront);
    LiveWindow.addActuator("Climber", "climberBack", climberBack);
    LiveWindow.addActuator("Climber", "climberFollowerFront", followerClimberFront);
    LiveWindow.addActuator("Climber", "climberFollowerBack", followerClimberBack);

  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new ClimberHold());
  }

  // the value in set expiration is in SECONDS not milliseconds
  public void setGoalClimberPosition(double pos, ClimberType type) {
    if (type == ClimberType.All) {
      goalFrontPosition = pos;
      goalBackPosition = pos;
    } else if (type == ClimberType.Front) {
      goalFrontPosition = pos;
    } else {
      goalBackPosition = pos;
    }

  }

  public void climberStop() {
    goalFrontPosition = getFrontPosition();
    goalBackPosition = getBackPosition();
    holdClimberPosition(ClimberType.All);
  }

  public double getFrontPosition() {
    return climberFront.getSelectedSensorPosition(0);
  }

  public double getBackPosition() {
    return climberBack.getSelectedSensorPosition(0);
  }

  public boolean checkCurrentPosition(double goalPos, ClimberType type) {

    boolean isFinished;
    if (type == ClimberType.All) {
      isFinished = (goalPos + CLIMBER_TOLERANCE >= getFrontPosition()
          && goalPos - CLIMBER_TOLERANCE <= getFrontPosition())
          && (goalPos + CLIMBER_TOLERANCE >= getBackPosition() && goalPos - CLIMBER_TOLERANCE <= getBackPosition());
    } else if (type == ClimberType.Front) {
      isFinished = (goalPos + CLIMBER_TOLERANCE >= getFrontPosition()
          && goalPos - CLIMBER_TOLERANCE <= getFrontPosition());
    } else {
      isFinished = (goalPos + CLIMBER_TOLERANCE >= getBackPosition()
          && goalPos - CLIMBER_TOLERANCE <= getBackPosition());
    }

    isFinished = (goalPos + CLIMBER_TOLERANCE >= getFrontPosition()
        && goalPos - CLIMBER_TOLERANCE <= getFrontPosition())
        && (goalPos + CLIMBER_TOLERANCE >= getBackPosition() && goalPos - CLIMBER_TOLERANCE <= getBackPosition());

    // System.out.println("climber isFinished: " + isFinished);
    // System.out.println("front upper: " + ((goalPos + CLIMBER_TOLERANCE) >=
    // getFrontPosition()));
    // System.out.println("front lower: " + ((goalPos - CLIMBER_TOLERANCE) <=
    // getFrontPosition()));
    // System.out.println("down upper: " + ((goalPos + CLIMBER_TOLERANCE) >=
    // getBackPosition()));
    // System.out.println("down lower: " + ((goalPos - CLIMBER_TOLERANCE) <=
    // getBackPosition()));

    return isFinished;
  }

  public void holdClimberPosition(ClimberType type) {
    if (type == ClimberType.All) {
      climberFront.set(ControlMode.MotionMagic, goalFrontPosition);
      SmartDashboard.putNumber("Climber Front Velocity", climberFront.getSelectedSensorVelocity());
      climberBack.set(ControlMode.MotionMagic, goalBackPosition);
      SmartDashboard.putNumber("Climber Back Velocity", climberBack.getSelectedSensorVelocity());
    } else if (type == ClimberType.Front) {
      climberFront.set(ControlMode.MotionMagic, goalFrontPosition);
      SmartDashboard.putNumber("Climber Front Velocity", climberFront.getSelectedSensorVelocity());
    } else {
      climberBack.set(ControlMode.MotionMagic, goalBackPosition);
      SmartDashboard.putNumber("Climber Back Velocity", climberBack.getSelectedSensorVelocity());
    }
  }

  public void extendClimber(boolean directionExtend, ClimberType type) {
    if (directionExtend) {
      dir = 1;
    } else {
      dir = -1;
    }

    if (type == ClimberType.All) {

      goalFrontPosition = getFrontPosition();
      goalFrontPosition += (dir * CLIMBER_EXTEND);
      goalBackPosition = getBackPosition();
      goalBackPosition += (dir * CLIMBER_EXTEND);

    } else if (type == ClimberType.Front) {
      goalFrontPosition = getFrontPosition();
      goalFrontPosition += (dir * CLIMBER_EXTEND);
    } else {
      goalBackPosition = getBackPosition();
      goalBackPosition += (dir * CLIMBER_EXTEND);

    }

  }

  
}
