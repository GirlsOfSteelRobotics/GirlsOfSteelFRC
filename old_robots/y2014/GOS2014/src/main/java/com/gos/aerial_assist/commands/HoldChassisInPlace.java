/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import com.gos.aerial_assist.Configuration;
import com.gos.aerial_assist.subsystems.Chassis;

/**
 * @author sophia, sonia
 */
public class HoldChassisInPlace extends GosCommandBase {

    private final Chassis m_chassis;
    private double m_setPoint;

    public HoldChassisInPlace(Chassis chassis) {
        m_chassis = chassis;
        addRequirements(m_chassis);

    }

    @Override
    public void initialize() {
        m_chassis.initEncoders();
        m_chassis.initPositionPIDS();
        m_chassis.resetPositionPIDError();
        m_setPoint = m_chassis.getLeftEncoderDistance(); //The current position, which must be maintained
        m_chassis.setLeftPositionPIDValues(5, 0, 0);
        m_chassis.setRightPositionPIDValues(5, 0, 0);
    }

    @Override
    public void execute() {
        m_chassis.setPosition(m_setPoint);
        /* USING ENCODERS
        if(chassis.getLeftEncoder() > 0)
        {
            chassis.setLeftJag(.2); //TODO: Check these values
        }
        else if (chassis.getLeftEncoder() < 0)
        {
            chassis.setLeftJag(-.2);
        }
        else
        {
            chassis.setLeftJag(0.0);
        }

        if(chassis.getRightEncoder() > 0)
        {
            chassis.setRightJag(.2);
        }
        else if(chassis.getRightEncoder() < 0)
        {
            chassis.setRightJag(-.2);
        }
        else
        {
            chassis.setRightJag(0.0);
        }
        * */
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.setLeftPositionPIDValues(Configuration.LEFT_POSITION_P, 0, 0);
        m_chassis.setRightPositionPIDValues(Configuration.RIGHT_POSITION_P, 0, 0);
        m_chassis.disablePositionPID();
    }



}
