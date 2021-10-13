/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendablePIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author CosmicHawk
 */
public class PointAtTarget extends CommandBase implements PIDOutput, PIDSource {

    SendablePIDController pid;

    public PointAtTarget() {
        pid = new SendablePIDController(.5, 0, 0, this, this);
        SmartDashboard.putData("hfri", pid);
    }

    protected void initialize() {
        pid.enable();
    }

    protected void execute() {
//        NetworkTable table = NetworkTable.getTable("camera");
//        if (table.getBoolean("foundTarget",false)) {
//            double xDifference = table.getDouble("xDifference", 0);
//
//            System.out.println(xDifference);
//
//            chassis.tankDrive(-xDifference / 2, xDifference / 2);
//
//        }
//        else {
//            chassis.tankDrive(0,0);    
//        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
        chassis.tankDrive(0, 0);
        pid.disable();
    }

    public void pidWrite(double output) {
        NetworkTable table = NetworkTable.getTable("camera");
        if (table.getBoolean("foundTarget", false)) {
            chassis.tankDrive(output, -output);
        } else {
            chassis.tankDrive(0, 0);
        }
    }

    public double pidGet() {
        NetworkTable table = NetworkTable.getTable("camera");
        double xDifference = table.getDouble("xDifference", 0);
        return xDifference;
    }
}
