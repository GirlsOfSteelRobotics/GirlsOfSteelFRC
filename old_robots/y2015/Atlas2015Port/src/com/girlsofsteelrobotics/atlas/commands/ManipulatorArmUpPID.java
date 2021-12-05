/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlsofsteelrobotics.atlas.commands;

import com.girlsofsteelrobotics.atlas.Configuration;

/**
 *
 * @author Sylvie
 */
public class ManipulatorArmUpPID extends CommandBase {

    private double angle;

    public ManipulatorArmUpPID() {
        requires(manipulator);
    }

    @Override
    protected void initialize() {
        angle = manipulator.getAbsoluteDistance();
    }

    @Override
    protected void execute() {
        System.out.println("Up Encoder Value: " + manipulator.getAbsoluteDistance());
//        System.out.println("Up Angle Setpoint: " + angle * Configuration.desiredAnglePivotArmSign);
//        System.out.println("Error: " + manipulator.getError());
        manipulator.setSetPoint(angle * Configuration.desiredAnglePivotArmSign);
        if (angle < 110) {
            angle += 3;
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        manipulator.holdAngle();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
