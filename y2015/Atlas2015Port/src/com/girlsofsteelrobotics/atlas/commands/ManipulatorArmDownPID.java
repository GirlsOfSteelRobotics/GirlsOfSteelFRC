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
public class ManipulatorArmDownPID extends CommandBase {

    private double angle;

    public ManipulatorArmDownPID() {
        requires(manipulator);
    }

    protected void initialize() {
        angle = manipulator.getAbsoluteDistance();
    }

    protected void execute() {

        System.out.println("Down Encoder Value: " + manipulator.getAbsoluteDistance());
        //System.out.println("Down Angle Setpoint: " + angle);
        //System.out.println("Error: " + manipulator.getError());

        manipulator.setSetPoint(angle * Configuration.desiredAnglePivotArmSign);
        if (angle > -18.2) {
            angle -= 3;
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        manipulator.holdAngle();
    }

    protected void interrupted() {
        end();
    }

}
