/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// POSITIVE ENCODER TICKS IS BALLSCREW DOWN, ROBOT UP

package com.gos.deep_space.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.deep_space.RobotMap;
import com.gos.deep_space.commands.ClimberHold;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */

// commented out screwback for testing purposes
public class Climber extends Subsystem {

    public static final double CLIMBER_EXTEND = 2000;

    public static final double CLIMBER_TOLERANCE = 200;

    public static final double FIRST_GOAL_POS = 0.0; // Robot is powered on in fully retracted state
    public static final double SECOND_GOAL_POS = 34000.0; // -30000 on Belka, -32000 on Laika
    public static final double THIRD_GOAL_POS = 84000.0; // -82000 on Belka, -84000 on Laika

    public static final double ALL_TO_ZERO = 0.0;

    public static final int MAX_CRUISE_VELOCITY = 3900; // Anna and Gracie 4/25
    public static final int MAX_ACCELERATION = 10000; // Anna and Gracie 4/25

    public enum ClimberType {
        All, Front, Back
    }

    // DigitalInput limitSwitch = new DigitalInput(1);
    // MotorController armMotor = new Victor(1);
    // Counter counter = new Counter(limitSwitch);
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    private final WPI_TalonSRX m_climberFront;
    private final WPI_TalonSRX m_followerClimberFront;

    private final WPI_TalonSRX m_climberBack;
    private final WPI_TalonSRX m_followerClimberBack;

    public double m_goalFrontPosition;
    public double m_goalBackPosition;

    private int m_dir;

    public Climber() {

        m_climberFront = new WPI_TalonSRX(RobotMap.CLIMBER_FRONT_TALON);
        m_followerClimberFront = new WPI_TalonSRX(RobotMap.CLIMBER_FRONT_FOLLOWER_TALON);

        m_climberBack = new WPI_TalonSRX(RobotMap.CLIMBER_BACK_TALON);
        m_followerClimberBack = new WPI_TalonSRX(RobotMap.CLIMBER_BACK_FOLLOWER_TALON);

        m_climberFront.setInverted(true);
        m_followerClimberFront.setInverted(true);
        m_climberBack.setInverted(true);
        m_followerClimberBack.setInverted(true);

        m_climberFront.setSensorPhase(true);
        m_climberBack.setSensorPhase(true);

        // PID
        m_climberFront.config_kF(0, 0.20, 10); // tuned by Ziya and Joe on 4/3
        m_climberFront.config_kP(0, 1.0, 10); // tuned by Ziya and Joe on 4/3
        m_climberFront.config_kI(0, 0, 10);
        m_climberFront.config_kD(0, 0, 10);

        m_climberBack.config_kF(0, 0.20, 10); // tuned by Ziya and Joe on 4/3
        m_climberBack.config_kP(0, 1.0, 10); // tuned by Ziya and Joe on 4/3
        m_climberBack.config_kI(0, 0, 10);
        m_climberBack.config_kD(0, 0, 10);

        // Motion Magic
        m_climberFront.configMotionAcceleration(MAX_ACCELERATION);
        m_climberFront.configMotionCruiseVelocity(MAX_CRUISE_VELOCITY);

        m_climberBack.configMotionAcceleration(MAX_ACCELERATION);
        m_climberBack.configMotionCruiseVelocity(MAX_CRUISE_VELOCITY);

        m_climberFront.setNeutralMode(NeutralMode.Brake);
        m_followerClimberFront.setNeutralMode(NeutralMode.Brake);

        m_climberBack.setNeutralMode(NeutralMode.Brake);
        m_followerClimberBack.setNeutralMode(NeutralMode.Brake);

        m_followerClimberFront.follow(m_climberFront, FollowerType.PercentOutput);
        m_followerClimberBack.follow(m_climberBack, FollowerType.PercentOutput);

        addChild("climberFront", m_climberFront);
        addChild("climberBack", m_climberBack);
        addChild("climberFollowerFront", m_followerClimberFront);
        addChild("climberFollowerBack", m_followerClimberBack);

    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new ClimberHold(this));
    }

    // the value in set expiration is in SECONDS not milliseconds
    public void setGoalClimberPosition(double pos, ClimberType type) {
        if (type == ClimberType.All) {
            m_goalFrontPosition = pos;
            m_goalBackPosition = pos;
        } else if (type == ClimberType.Front) {
            m_goalFrontPosition = pos;
        } else {
            m_goalBackPosition = pos;
        }

    }

    public void climberStop() {
        m_goalFrontPosition = getFrontPosition();
        m_goalBackPosition = getBackPosition();
        holdClimberPosition(ClimberType.All);
    }

    public double getFrontPosition() {
        return m_climberFront.getSelectedSensorPosition(0);
    }

    public double getBackPosition() {
        return m_climberBack.getSelectedSensorPosition(0);
    }

    public boolean checkCurrentPosition(double goalPos, ClimberType type) {

        boolean isFinished = (goalPos + CLIMBER_TOLERANCE >= getFrontPosition()
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

        return isFinished; // NOPMD
    }

    public void holdClimberPosition(ClimberType type) {
        if (type == ClimberType.All) {
            m_climberFront.set(ControlMode.MotionMagic, m_goalFrontPosition);
            SmartDashboard.putNumber("Climber Front Velocity", m_climberFront.getSelectedSensorVelocity());
            m_climberBack.set(ControlMode.MotionMagic, m_goalBackPosition);
            SmartDashboard.putNumber("Climber Back Velocity", m_climberBack.getSelectedSensorVelocity());
        } else if (type == ClimberType.Front) {
            m_climberFront.set(ControlMode.MotionMagic, m_goalFrontPosition);
            SmartDashboard.putNumber("Climber Front Velocity", m_climberFront.getSelectedSensorVelocity());
        } else {
            m_climberBack.set(ControlMode.MotionMagic, m_goalBackPosition);
            SmartDashboard.putNumber("Climber Back Velocity", m_climberBack.getSelectedSensorVelocity());
        }
    }

    public void extendClimber(boolean directionExtend, ClimberType type) {
        if (directionExtend) {
            m_dir = 1;
        } else {
            m_dir = -1;
        }

        if (type == ClimberType.All) {

            m_goalFrontPosition = getFrontPosition();
            m_goalFrontPosition += m_dir * CLIMBER_EXTEND;
            m_goalBackPosition = getBackPosition();
            m_goalBackPosition += m_dir * CLIMBER_EXTEND;

        } else if (type == ClimberType.Front) {
            m_goalFrontPosition = getFrontPosition();
            m_goalFrontPosition += m_dir * CLIMBER_EXTEND;
        } else {
            m_goalBackPosition = getBackPosition();
            m_goalBackPosition += m_dir * CLIMBER_EXTEND;

        }

    }


}
