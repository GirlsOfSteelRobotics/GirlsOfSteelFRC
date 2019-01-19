/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class FrontUp extends Command {

	public FrontUp() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.climber);
  }

  // Called just before this Command runs the first time
	protected void initialize() {
		System.out.println("Front Up init");
    Robot.climber.frontUp(); 
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
