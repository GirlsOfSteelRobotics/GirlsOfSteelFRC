/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ScrewAllDown extends Command {

	public ScrewAllDown() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.screwClimber);
  }

  // Called just before this Command runs the first time
	protected void initialize() {
		System.out.println("Screw All Down init");
    Robot.screwClimber.screwFrontDown(); 
    Robot.screwClimber.screwBackDown();
  }
  
	// Called re peatedly when this Command is scheduled to run
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
