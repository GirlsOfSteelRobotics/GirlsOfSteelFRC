/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates.commands;
import edu.wpi.first.wpilibj.networking.NetworkTable;

/**
 *
 * @author tester
 */
public class PointAtTarget extends CommandBase {

    protected void initialize() {
        System.out.println("HI!!!!!!!!");
    }

    protected void execute() {
        chassis.tankDrive(.25,.25);
        NetworkTable table= NetworkTable.getTable("camera");
           double xDifference = table.getDouble("xDifference");
           System.out.println(xDifference);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
         chassis.tankDrive(0,0);
    }

}
