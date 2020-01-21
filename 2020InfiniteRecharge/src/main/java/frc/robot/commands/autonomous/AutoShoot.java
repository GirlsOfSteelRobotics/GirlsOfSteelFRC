package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoShoot extends CommandBase {

	public AutoShoot() {
		// Use requires() here to declare subsystem dependencies
		//super.addRequirements(Shooter); When a subsystem is written, add the requires line back in.
	}

    public void initialize(){
    }

	// Called repeatedly when this Command is scheduled to run
	public void execute() { 
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
        return false;
	}

	// Called once after isFinished returns true
	public void end(boolean interrupted) {
  }
}