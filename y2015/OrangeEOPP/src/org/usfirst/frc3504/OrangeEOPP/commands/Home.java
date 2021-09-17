/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc3504.OrangeEOPP.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc3504.OrangeEOPP.Robot;

/**
 * This command is called while a button is held to move the shooter in the
 * direction of home.
 * @author appasamysm
 */
public class Home extends Command {

    protected void initialize() {
        //Uses the shooter
        requires(Robot.shooter);
    }

    protected void execute() {
        //Turn on the motors to move in the home direction
        Robot.shooter.home();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        //Stop the motors when the button is no longer held
        Robot.shooter.stop();
    }

    protected void interrupted() {
        end();
    }
    
}
