package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.OI;
import frc.robot.Robot;

public class AutoShoot extends CommandBase {
	Command autonomousCommand;
	Shooter shooter;
	OI OI;


	public AutoShoot() {
		requires(Robot.Shooter);
		shooter = Robot.shooter;
		OI = Robot.OI;
		// Use requires() here to declare subsystem dependencies
        //super.addRequirements(Shooter); When a subsystem is written, add the requires line back in.
	}

    private void requires(Object shooter2) {
	}

	public void initialize() {
    }

	// Called repeatedly when this Command is scheduled to run
	public void execute() { 
		if(OI.XboxController.)
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
        return false;
	}

	// Called once after isFinished returns true
	public void end(boolean interrupted) {
  }
}