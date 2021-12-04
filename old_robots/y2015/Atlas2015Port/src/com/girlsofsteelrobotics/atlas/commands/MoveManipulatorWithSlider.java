/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlsofsteelrobotics.atlas.commands;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author appasamysm
 */
public class MoveManipulatorWithSlider extends CommandBase {

    //Joystick thinks top is zero
    //Joystick thinks bottom is full
    Joystick driver;

    private final double fullRangeOnSlider = 100;
    private final double maxManipulatorAngle = 110;
    private final double minManipulatorAngle = -3;

    public MoveManipulatorWithSlider()
    {
        requires(manipulator);
        driver = oi.getChassisJoystick();
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        double angle = ((driver.getZ()/fullRangeOnSlider)*maxManipulatorAngle)+minManipulatorAngle;
        manipulator.setSetPoint(angle);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }

}
