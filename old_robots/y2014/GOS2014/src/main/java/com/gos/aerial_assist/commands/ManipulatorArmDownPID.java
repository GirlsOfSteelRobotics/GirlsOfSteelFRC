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
public class ManipulatorArmDownPID extends CommandBase {

    private final Manipulator m_manipulator;
    private double m_angle;

    public ManipulatorArmDownPID(Manipulator manipulator) {
        m_manipulator = manipulator;
        requires(m_manipulator);
    }

    @Override
    protected void initialize() {
        m_angle = m_manipulator.getAbsoluteDistance();
    }

    @Override
    protected void execute() {

        System.out.println("Down Encoder Value: " + m_manipulator.getAbsoluteDistance());
        //System.out.println("Down Angle Setpoint: " + angle);
        //System.out.println("Error: " + manipulator.getError());

        m_manipulator.setSetPoint(m_angle * Configuration.desiredAnglePivotArmSign);
        if (m_angle > -18.2) {
            m_angle -= 3;
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_manipulator.holdAngle();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
