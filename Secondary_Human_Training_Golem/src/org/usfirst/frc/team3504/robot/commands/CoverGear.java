package org.usfirst.frc.team3504.robot.commands;


import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
<<<<<<< HEAD:Secondary_Human_Training_Golem/src/org/usfirst/frc/team3504/robot/commands/CoverGear.java
public class CoverGear extends Command {

    public CoverGear() {
=======
public class GearCover extends Command {
	
	private boolean extended;

    public GearCover(boolean extended) {
>>>>>>> 2fab681d8de0eeed81370d12b97c8a57ca31bdb2:Secondary_Human_Training_Golem/src/org/usfirst/frc/team3504/robot/commands/GearCover.java
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.gear);
    	this.extended = extended;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
<<<<<<< HEAD:Secondary_Human_Training_Golem/src/org/usfirst/frc/team3504/robot/commands/CoverGear.java
    	Robot.gear.coverPosition(false);
=======
    	Robot.gear.coverPosition(extended);
>>>>>>> 2fab681d8de0eeed81370d12b97c8a57ca31bdb2:Secondary_Human_Training_Golem/src/org/usfirst/frc/team3504/robot/commands/GearCover.java
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
