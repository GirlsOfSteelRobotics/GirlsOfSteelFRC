/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.deep_space.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.deep_space.RobotMap;
import com.gos.deep_space.commands.PivotHold;
import edu.wpi.first.wpilibj.command.Subsystem;

public final class Pivot extends Subsystem {

    public static final double PIVOT_DOWN_INCREMENT = 125;
    public static final double PIVOT_UP_INCREMENT = 200;
    public static final double PIVOT_GROUND = -3875; // -4121 was the pre pool noodle value
    public static final double PIVOT_ROCKET = -1371; //-1775
    public static final double PIVOT_CARGO = -559; // -838
    public static final double PIVOT_TOLERANCE = 100;

    public enum PivotDirection {
        Up, Down
    }

    private final WPI_TalonSRX m_pivot;
    private double m_goalPivotPosition;

    public Pivot() {
        m_pivot = new WPI_TalonSRX(RobotMap.PIVOT_TALON);
        m_pivot.setSensorPhase(true);
        setupPivotFPID();
        addChild(m_pivot);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new PivotHold());
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void setupPivotFPID() {
        m_pivot.config_kF(0, 0, 10);
        m_pivot.config_kP(0, 1.5, 10);
        m_pivot.config_kI(0, 0, 10);
        m_pivot.config_kD(0, 15, 10);
    }

    public void setPivotPosition(double pos) {
        m_pivot.set(ControlMode.Position, pos);
    }

    public void holdPivotPosition() {
        m_pivot.set(ControlMode.Position, m_goalPivotPosition);
        //System.out.println("Pivot goal position" + goalPivotPosition +  "actual position " + pivot.getSelectedSensorPosition(0));
    }

    public void pivotToGround() {
        m_pivot.set(ControlMode.Position, PIVOT_GROUND);
    }

    public void pivotToRocket() {
        m_pivot.set(ControlMode.Position, PIVOT_ROCKET);
    }

    public void pivotToCargo() {
        m_pivot.set(ControlMode.Position, PIVOT_CARGO);
    }

    public void setGoalPivotPosition(double goal) {
        m_goalPivotPosition = goal;
    }

    public double getPivotPosition() {
        return m_pivot.getSelectedSensorPosition(0);
    }

    public void incrementPivot() {
        m_goalPivotPosition = getPivotPosition();
        m_goalPivotPosition += PIVOT_UP_INCREMENT;
    }

    public void decrementPivot() {
        m_goalPivotPosition = getPivotPosition();
        m_goalPivotPosition -= PIVOT_DOWN_INCREMENT;
    }

    public boolean checkCurrentPivotPosition(double goalPos) {
        boolean isFinished = (goalPos <= getPivotPosition() + PIVOT_TOLERANCE)
            && (goalPos >= getPivotPosition() - PIVOT_TOLERANCE);
        //System.out.println("isFinished: " + isFinished);
        return isFinished;
    }

    public void pivotStop() {
        m_pivot.stopMotor();
    }
}
