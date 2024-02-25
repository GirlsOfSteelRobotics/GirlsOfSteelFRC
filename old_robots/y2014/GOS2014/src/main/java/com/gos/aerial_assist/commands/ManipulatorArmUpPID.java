/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import com.gos.aerial_assist.Configuration;
import com.gos.aerial_assist.subsystems.Manipulator;

/**
 * @author Sylvie
 */
public class ManipulatorArmUpPID extends GosCommandBaseBase {

    private final Manipulator m_manipulator;
    private double m_angle;

    public ManipulatorArmUpPID(Manipulator manipulator) {
        m_manipulator = manipulator;
        addRequirements(m_manipulator);
    }

    @Override
    public void initialize() {
        m_angle = m_manipulator.getAbsoluteDistance();
    }

    @Override
    public void execute() {
        System.out.println("Up Encoder Value: " + m_manipulator.getAbsoluteDistance());
        //        System.out.println("Up Angle Setpoint: " + angle * Configuration.desiredAnglePivotArmSign);
        //        System.out.println("Error: " + manipulator.getError());
        m_manipulator.setSetPoint(m_angle * Configuration.DESIRED_ANGLE_PIVOT_ARM_SIGN);
        if (m_angle < 110) {
            m_angle += 3;
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_manipulator.holdAngle();
    }



}
