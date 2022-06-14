package com.gos.power_up.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.power_up.RobotMap;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 *
 */
public final class Wrist extends SubsystemBase {
    public static final double WRIST_IN_BOUND = -60; //TODO tune
    public static final double WRIST_OUT_BOUND = -1000; //TODO tune
    public static final double WRIST_COLLECT = -930; //TODO tune
    public static final double WRIST_SWITCH = -800; //TODO tune
    public static final double WRIST_INCREMENT = 20; //TODO tune
    public static final double WRIST_SHOOT = -350; //TODO tune

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    private final WPI_TalonSRX m_wrist;
    private double m_goalWristPosition;


    public Wrist() {
        m_wrist = new WPI_TalonSRX(RobotMap.WRIST);
        setupWristFPID();
    }

    public void setupWristFPID() {
        //talon.setPosition (0); TODO figure out new syntax
        m_wrist.config_kF(0, 0, 10);
        m_wrist.config_kP(0, 1.5, 10);
        m_wrist.config_kI(0, 0, 10);
        m_wrist.config_kD(0, 15, 10);
    }

    public void setWristSpeed(double speed) {
        m_wrist.set(speed); //value between -1.0 and 1.0;
    }

    public void setWristPosition(double pos) {
        m_wrist.set(ControlMode.Position, pos);
    }

    public void wristStop() {
        m_wrist.stopMotor();
    }

    public void holdWristPosition() {
        m_wrist.set(ControlMode.Position, m_goalWristPosition);
    }

    public void wristIn() {
        double goalPosition = m_goalWristPosition + WRIST_INCREMENT;
        if (goalPosition >= WRIST_IN_BOUND) {
            m_goalWristPosition = WRIST_IN_BOUND;
        } else {
            m_goalWristPosition = goalPosition;
            //System.out.println("Wrist moved in. New goal : " + goalWristPosition);
        }

    }

    public void wristOut() {
        double goalPosition = m_goalWristPosition - WRIST_INCREMENT;
        if (goalPosition <= WRIST_OUT_BOUND) {
            m_goalWristPosition = WRIST_OUT_BOUND;
        } else {
            m_goalWristPosition = goalPosition;
            //System.out.println("Wrist moved out. New goal : " + goalWristPosition);
        }

    }

    public void setGoalWristPosition(double goal) {
        m_goalWristPosition = goal;
    }

    public double getWristPosition() {
        return m_wrist.getSelectedSensorPosition(0);
    }
}
