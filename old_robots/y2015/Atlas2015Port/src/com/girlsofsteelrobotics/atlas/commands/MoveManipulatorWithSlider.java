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
    
    private double fullRangeOnSlider = 100;
    private double maxManipulatorAngle = 110;
    private double minManipulatorAngle = -3;
    
    public MoveManipulatorWithSlider()
    {
        requires(manipulator);
        driver = oi.getChassisJoystick();
    }

    protected void initialize() {
    }

    protected void execute() {
        double angle = ((driver.getZ()/fullRangeOnSlider)*maxManipulatorAngle)+minManipulatorAngle;
        manipulator.setSetPoint(angle);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }
    
}
