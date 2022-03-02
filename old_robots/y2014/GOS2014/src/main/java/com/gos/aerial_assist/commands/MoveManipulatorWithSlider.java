/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj.Joystick;
import com.gos.aerial_assist.OI;
import com.gos.aerial_assist.subsystems.Manipulator;

/**
 * @author appasamysm
 */
public class MoveManipulatorWithSlider extends CommandBase {

    //Joystick thinks top is zero
    //Joystick thinks bottom is full
    private final Joystick m_driver;
    private final Manipulator m_manipulator;

    private static final double m_fullRangeOnSlider = 100;
    private static final double m_maxManipulatorAngle = 110;
    private static final double m_minManipulatorAngle = -3;

    public MoveManipulatorWithSlider(OI oi, Manipulator manipulator) {
        m_manipulator = manipulator;
        m_driver = oi.getChassisJoystick();
        addRequirements(m_manipulator);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double angle = ((m_driver.getZ() / m_fullRangeOnSlider) * m_maxManipulatorAngle) + m_minManipulatorAngle;
        m_manipulator.setSetPoint(angle);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }



}
