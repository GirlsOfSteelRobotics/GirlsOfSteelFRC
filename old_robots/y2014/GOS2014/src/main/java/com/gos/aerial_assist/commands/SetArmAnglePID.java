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
 * <p>
 * Angle 0 is considered to be the horizontal
 * -18.3 degrees is the actual bottom
 * Top limit of the arm unknown (probably around 100?)
 */
public class SetArmAnglePID extends GosCommandBase {

    private final Manipulator m_manipulator;
    private final double m_desiredAngle;
    private double m_angle;
    private final double m_allowedAngleError;


    public SetArmAnglePID(Manipulator manipulator, double desiredAngle) {
        m_manipulator = manipulator;
        addRequirements(m_manipulator);
        m_desiredAngle = desiredAngle;
        m_allowedAngleError = 4; //2 degrees of error on both sides allowed
    }

    @Override
    public void initialize() {
        m_angle = m_desiredAngle * Configuration.DESIRED_ANGLE_PIVOT_ARM_SIGN;
    }

    @Override
    public void execute() {
        if (m_angle < -18.2) {
            m_angle = -18.2;
        } else if (m_angle > 113) {
            m_angle = 110;
        }
        m_manipulator.setSetPoint(m_angle);
        //System.out.println("SSEENNNTTTT AARRRMMMMMMMMM AANNNGGLLLLEEE ----- "+angle);
        //System.out.println("AARRRMMMMMMMMM AANNNGGLLLLEEE ----- "+manipulator.getDistance());
    }

    @Override
    public boolean isFinished() {
        return Math.abs(m_desiredAngle - m_manipulator.getAbsoluteDistance()) < m_allowedAngleError;
    }

    @Override
    public void end(boolean interrupted) {
        m_manipulator.holdAngle();
    }



}
