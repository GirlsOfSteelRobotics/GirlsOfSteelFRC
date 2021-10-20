/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc3504.OrangeEOPP.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc3504.OrangeEOPP.Robot;

/**
 * This command is called while a button is held to move the shooter in the
 * direction of the goal.
 * @author appasamysm
 */
public class Tilt extends Command {

    protected void initialize() {
        //Uses the shooter
        requires(Robot.shooter);
    }

    protected void execute() {
        //Starts the motor in the direction of the goal
        Robot.shooter.tilt();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        //Stops the shooter when the button is released
        Robot.shooter.stop();
    }

    protected void interrupted() {
        end();
    }

}
