/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.girlsofsteelrobotics.atlas.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *THIS IS IN METERS
 * 
 * Requires driving because it should override arcade drive
 * 
 * @author Sylvie
 */
public class MoveToPosition extends CommandBase{

    private double distance;
    private double offBy = 0.03;
    
    public MoveToPosition(double distance) {
        requires(driving); 
        this.distance = distance;
    }

    MoveToPosition() {
    }
    protected void initialize() {
       chassis.initPositionPIDS();
       chassis.resetPositionPIDError();
       chassis.initEncoders();
       //SmartDashboard.putNumber("Distance", 0);
    }

    protected void execute() {
        //distance = SmartDashboard.getNumber("Distance");
        chassis.setPosition(distance);
        SmartDashboard.putNumber("Left Encoder: ", chassis.getLeftEncoderDistance());
        SmartDashboard.putNumber("Right Encoder: ", chassis.getRightEncoderDistance());
    }

    protected boolean isFinished() {
        //Is finished when our position is within the "off by" range of the setpoint

        return (Math.abs((chassis.getLeftEncoderDistance() - distance)) < offBy);
    }

    protected void end() {
        chassis.disablePositionPID();
        chassis.stopEncoders();
        chassis.stopJags();
    }

    protected void interrupted() {
        end();
    }
    
}
