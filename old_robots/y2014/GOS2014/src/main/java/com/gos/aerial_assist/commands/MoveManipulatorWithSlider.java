/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj.Joystick;
import com.gos.aerial_assist.subsystems.Manipulator;

/**
 * @author appasamysm
 */
public class MoveManipulatorWithSlider extends GosCommand {

    //Joystick thinks top is zero
    //Joystick thinks bottom is full
    private final Joystick m_driver;
    private final Manipulator m_manipulator;

    private static final double FULL_RANGE_ON_SLIDER = 100;
    private static final double MAX_MANIPULATOR_ANGLE = 110;
    private static final double MIN_MANIPULATOR_ANGLE = -3;

    public MoveManipulatorWithSlider(Joystick joystick, Manipulator manipulator) {
        m_manipulator = manipulator;
        m_driver = joystick;
        addRequirements(m_manipulator);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double angle = ((m_driver.getZ() / FULL_RANGE_ON_SLIDER) * MAX_MANIPULATOR_ANGLE) + MIN_MANIPULATOR_ANGLE;
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
