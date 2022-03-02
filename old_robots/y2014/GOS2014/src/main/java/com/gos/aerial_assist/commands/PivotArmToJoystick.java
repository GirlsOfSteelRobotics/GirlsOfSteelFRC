/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj.Joystick;
import com.gos.aerial_assist.OI;
import com.gos.aerial_assist.subsystems.Manipulator;

/**
 * @author user
 */
public class PivotArmToJoystick extends CommandBase {

    private final Joystick m_operator;
    private final Manipulator m_manipulator;
    private double m_angle;

    public PivotArmToJoystick(OI oi, Manipulator manipulator) {
        m_manipulator = manipulator;
        addRequirements(m_manipulator); //HAVE TO REQUIRE MANIPULATOR SO THAT THIS DOESN'T INTERFERE WITH OTHER MANIPULATOR COMMANDS
        m_operator = oi.getOperatorJoystick();
    }

    @Override
    public void initialize() {
        m_angle = m_manipulator.getAbsoluteDistance();
    }

    @Override
    public void execute() {
        System.out.println("Operator Y: " + m_operator.getY());
        if (m_operator.getY() > 0.5) {
            m_manipulator.setSetPoint(m_angle);
            m_angle++; //+= 1.5;
        } else if (m_operator.getY() < -0.5) {
            m_manipulator.setSetPoint(m_angle);
            m_angle--; //-= 1.5;
        }
        m_manipulator.holdAngle();

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
