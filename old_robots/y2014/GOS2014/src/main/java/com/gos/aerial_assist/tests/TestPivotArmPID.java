/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.tests;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.aerial_assist.commands.GosCommandBase;
import com.gos.aerial_assist.subsystems.Manipulator;

/**
 * @author user
 */
public class TestPivotArmPID extends GosCommandBase {
    private final Manipulator m_manipulator;
    private double m_currentAngle;

    public TestPivotArmPID(Manipulator manipulator) {
        m_manipulator = manipulator;
        addRequirements(m_manipulator);
    }

    @Override
    public void initialize() {
        // manipulator.initEncoder();
        // manipulator.resetPIDError();
        // manipulator.startPID();
        // SmartDashboard.putNumber("Pivot Angle", 0.0);
        SmartDashboard.putNumber("Pivot Arm Encoder Value", 0.0);
    }

    @Override
    public void execute() {
        //desiredAngle = SmartDashboard.getNumber("Pivot Angle", 0);
        //manipulator.setSetPoint(desiredAngle);
        m_currentAngle = m_manipulator.getAbsoluteDistance();
        SmartDashboard.putNumber("Pivot Arm Encoder Value", m_currentAngle);
    }

    @Override
    public boolean isFinished() {
        return false; //Math.abs(desiredAngle-currentAngle) < allowedAngleError;
    }

    @Override
    public void end(boolean interrupted) {
        m_manipulator.stopManipulator();
        m_manipulator.disablePID();
    }



}
